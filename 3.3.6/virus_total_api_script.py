"""
Script used for communicating with virus totals api.
Checks if files checksum is known by VT and returns results.
Stores checksums of unknown files locally and checks for results
when script is used at later dates.
"""

import sys
from functools import partial
import hashlib
import requests
import os.path
import json
import pprint

# constants
VIRUS_TOTAL_SCAN_URL = "https://www.virustotal.com/vtapi/v2/file/scan"
VIRUS_TOTAL_RESCAN_URL = "https://www.virustotal.com/vtapi/v2/file/rescan"
VIRUS_TOTAL_REPORT_URL = "https://www.virustotal.com/vtapi/v2/file/report"
API_KEY = 'be0d664466ecc3383e3417fa88601f5dc8bb45783b745e4d91ff5da003e69ca4'

result_data = []


def write_to_file():
    """Prints anything remaning in result_data list to the data file.
    """

    print("writing remaining file data to disk...")
    with open('data', 'w') as out_file:
        json.dump(result_data, out_file)


def prework():
    """Reads in the data file if it exists.
    """

    if os.path.isfile('data'):
        print("Loading existing file data...")
        with open('data', 'r') as in_file:
            for entry in json.load(in_file):
                result_data.append(entry)
        print("Finished loading data for {} files...".format(len(result_data)))


def file_in_data(checksum):
    """[checks if the checksum is already locally known]
    
    Arguments:
        checksum {[sha256 hash]} -- [A sha256 hash]
    
    Returns:
        [bool] -- [if checksum in collection]
    """

    for entry in result_data:
        if checksum in entry.values():
            return True


def calculate_sha256(file):
    """[takes a file and calculates the sha256 hash]
    
    Arguments:
        file {[string]} -- [a string representing a filename or filepath]
    
    Returns:
        [sha256 object] -- [a object from hashlib containing information about the incoming file]
    """

    with open(file, mode='rb') as in_file:
        file_hash = hashlib.sha256()
        for buf in iter(partial(in_file.read, 128), b''):
            file_hash.update(buf)
        return file_hash


def request_file_report(checksum):
    """[takes a checksum and requests a virus total api report related to it]
    
    Arguments:
        checksum {[sha256hash]} -- [a sha256 hash used to request a file report]
    
    Returns:
        [json] -- [json containing the report if it exits]
    """

    params = {'apikey': API_KEY, 'resource': checksum}
    headers = {"Accept-Encoding": "gzip, deflate", "User-Agent": "gzip, Virus total api script IP stationary units ht17"}
    pp = pprint.PrettyPrinter(indent=4)
    response = requests.get(VIRUS_TOTAL_REPORT_URL, params=params, headers=headers)
    pp.pprint(checksum)
    pp.pprint(response)
    if response.status_code == 200:
        return response.json()
    elif response.status_code == 204:
        print("Request rate limit exceeded. You are making more requests than allowed.")
    else:
        print("An error occurred while requesting report for {}".format(checksum))


def upload_file(file):
    """[Uploads a file to the virus total API]
    
    Arguments:
        file {[string]} -- [A string representing a file to upload]
    """

    params = {'apikey': API_KEY}
    files = {'file': (file, open(file, 'rb'))}
    response = requests.post(VIRUS_TOTAL_SCAN_URL, files=files, params=params)
    if response:
        print("File {} uploaded successfully".format(file))
    else:
        print("An error occurred while uploading {}".format(file))


def process_files(file_list):
    """[Takes a list of files and starts the script process]
    
    Arguments:
        file_list {[list of strings]} -- [a list of strings representing files to process]
    """

    print("Starting process of {} files..".format(len(file_list)))
    for file in file_list:
        file_hash = calculate_sha256(file)
        status = request_file_report(file_hash.hexdigest())
        if not file_in_data(file_hash.hexdigest()):
            if status['response_code'] == 1:
                result_data.append( {'sha256' : file_hash.hexdigest(), 'fileName' : file, 'report' : status['scans']})
            else:
                result_data.append( {'sha256' : file_hash.hexdigest(), 'fileName' : file, 'report' : None })
                if status['response_code'] != -2:
                    print('File missing from VT database and file: {} queue, uploading...\n'.format(file))
                    upload_file(file)
                    print('File {} done uploading, rerun script at later file to check report.\n'.format(file))

def present_result():
    """prints out information about the results of the script, such as known reports 
    and files still waiting for a report.
    """

    if result_data:
        print('Printing entries with known reports:\n')
        pp = pprint.PrettyPrinter(indent=4)
        tmp = []
        for entry in result_data:
            if entry['report'] is not None:
                pp.pprint(entry)
                response = input("\nDelete entry y/n?")
                if response.lower() == 'y':
                    tmp.append(entry)

        for entry in tmp:
            result_data.remove(entry)

        print("{} files left in queue, rerun script later to check status...".format(len(result_data)))
    else:
        print("Script finished without any presentable data.")


if __name__ == "__main__":
    """starts the script!
    """

    print("DUE TO THE LIMITATION OF VIRUS TOTAL API THIS SCRIPT WILL RAISE AN EXCEPTION IF THE NUMBER OF REQUESTS"
          " EXCEED 4 PER 60 SECOND PEROID")
    prework() # ladda data
    process_files(sys.argv[1:]) # behandla nya filer, kolla om de är kända,
    present_result()
    write_to_file()
    print("Script done, exiting...")

