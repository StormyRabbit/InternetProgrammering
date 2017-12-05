"""
simple script for virus checking using virus total api
"""
import sys
from functools import partial
import hashlib
import argparse

import requests

VIRUS_TOTAL_SCAN_URL = "https://www.virustotal.com/vtapi/v2/file/scan"
VIRUS_TOTAL_RESCAN_URL = "https://www.virustotal.com/vtapi/v2/file/rescan"
API_KEY = 'be0d664466ecc3383e3417fa88601f5dc8bb45783b745e4d91ff5da003e69ca4'

def calculate_sha256(file):
    """[Creates and returns a hashlib sha256 object from param]

    [description]

    Arguments:
        file {[type]} -- [description]

    Returns:
        [type] -- [description]
    """

    with open(file, mode='rb') as in_file:
        file_hash = hashlib.sha256()
        for buf in iter(partial(in_file.read, 128), b''):
            file_hash.update(buf)
    return file_hash.hexdigest()

def check_file_scan_status(file):
    """[summary]

    [description]

    Arguments:
        file {[type]} -- [description]
    """
    # can do up to 25 files
    file_hexdigest = calculate_sha256(file)
    params = {'resource' : file_hexdigest}
    headers = {"Accept-Encoding" : "gzip, deflate",
               "User-Agent" : "gzip, restful_client_virus_total.py"}
    response = requests.post(VIRUS_TOTAL_RESCAN_URL, params=params)

def handle_response(respons):
    """[summary]

    [description]

    Arguments:
        respons {[type]} -- [description]
    """
    print(respons.json())
    if respons.json()['response_code'] is 1:
        return
    elif respons.json()['response_code'] is 1:
        return
    else:
        return

def upload_file(file):
    """[summary]

    [description]

    Arguments:
        file {[type]} -- [description]
    """
    file = {'file' : (file, open(file, 'rb'))}
    params = {'apikey': API_KEY}
    resp = requests.post(VIRUS_TOTAL_SCAN_URL, files=file, params=params)
    handle_response(resp)

def retrieve_results():
    """[summary]

    [description]
    """

    return

def start_process(*args):
    """[summary]

    [description]

    Arguments:
        *args {[type]} -- [description]
    """
    print("args:", args)
    for arg in args:
        print("FILE: ", arg)
        check_file_scan_status(arg)
        print("\n")
        sys.stdout.write("\r uploading %s ..." % arg)
        sys.stdout.flush()
        upload_file(arg)
    retrieve_results()

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="Script for checking files for viruses using virus total public API.")
    parser.add_argument('files', ,metavar="File", type=string)
    print("Starting process of uploading %d files to virus total API..." % len(sys.argv[1:]))
    start_process(sys.argv[1:])
