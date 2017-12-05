import requests
import sys
API_KEY = {'apikey' : 'APIKEY'}

def upload_file(file):
    files = {'file' : (file, open(file, 'rb'))}
    return;

def start_process(*args):
    for file in args:
        upload_file(file)     
    return;



if __name__ == "__main__":
    start_process(sys.argv[1:])