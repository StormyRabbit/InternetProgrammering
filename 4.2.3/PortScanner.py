import os.path
import socket
import pprint
import sys
import json
from contextlib import closing


class PortScanner:

    def __init__(self):
        self.ports_to_scan = []
        self.addresses = []
        self.result = {}
        self.menu_options = {'0': self.main_menu,
                             '1': self.start_scan,
                             '2': self.load_addresses_from_file,
                             '3': self.manually_add_address,
                             '4': self.print_result,
                             '5': self.save_result_to_file,
                             '6': self.exit}

    @staticmethod
    def exit():
        sys.exit(0)

    def save_result_to_file(self):
        file = input("Enter filename:")
        with open(file, 'w') as outfile:
            json.dump(self.result, outfile)
        input("result saved to file, press enter to return to main menu...")
        self.main_menu()

    def check_port(self, address, port):
        is_open = False
        with closing(socket.socket(socket.AF_INET, socket.SOCK_STREAM)) as sock:
            sock.settimeout(2)
            if sock.connect_ex((address, port)) == 0:
                print("port {} on adress {} is OPEN".format(port, address))
                is_open = True
            else:
                print("port {} on adress {} is CLOSED".format(port, address))
            self.result[address].update({port: is_open})

    def start_scan(self):
        if not self.addresses:
            print("No addresses in queue, add manually or from file from the main menu...")
            return
        print("Currently {} addresses in queue".format(len(self.addresses)))
        low_end_port = int(input("Enter low end of port range:"))
        high_end_port = int(input("Enter high end of port range (max 65535):"))
        for address in self.addresses:
            self.result[address] = {}
            print("starting scan for {}".format(address))
            for port in range(low_end_port, high_end_port):
                print("checking port {}".format(port))
                self.check_port(address, port)
        input("scan complete, press enter to return to main menu...")
        self.main_menu()

    def handle_menu_input(self, choice):
        try:
            self.menu_options[choice]()
        except KeyError:
            print("Invalid input")

    def main_menu(self):
        os.system('clear')
        print('0 - Main menu')
        print('1 - Start scan')
        print('2 - Load addresses from file')
        print('3 - Add addresses manually')
        print('4 - print result')
        print('5 - save result')
        print('6 - Exit')
        choice = input("Enter input:")
        self.handle_menu_input(choice)
        return

    def load_addresses_from_file(self):
        print("Note this function requires that the infile is structured with one ip address per line.")
        file = input('enter file path:')
        if os.path.isfile(file):
            with open(file, 'r') as in_file:
                for line in in_file:
                    self.addresses.append(line)
        self.main_menu()

    def print_result(self):
        pp = pprint.PrettyPrinter(indent=4)
        pp.pprint(self.result)
        input("print complete, press enter to return to main menu...")
        self.main_menu()

    def manually_add_address(self):
        new_address = input("Enter address: ")
        try:
            socket.inet_aton(new_address)
            self.addresses.append(new_address)
        except:
            print("Invalid ip address")
        self.main_menu()


if __name__ == "__main__":
    ps = PortScanner()
    ps.main_menu()
