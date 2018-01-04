import os.path
import socket
from contextlib import closing


class PortScanner:

    def __init__(self):
        self.ports_to_scan = []
        self.result = {}
        self.udpResult = {}

    def check_port(self, address, port, udp_mode):
        if udp_mode:
            socket_protocol = socket.SOCK_DGRAM
        else:
            socket_protocol = socket.SOCK_STREAM
        with closing(socket.socket(socket.AF_INET, socket_protocol)) as sock:
            sock.settimeout(2)
            if sock.connect_ex((address, port)) == 0:
                if not udp_mode:
                    self.result[address]['open_ports'].append(port)
            else:
                if udp_mode:
                    self.udpResult[address]['open_ports'].append(port)

    def scan_multiple_addresses(self, address_collection, port_low_end, port_high_end, udp_mode ):
        for ip in address_collection:
            self.scan_address(ip, port_low_end, port_high_end, udp_mode)

    def scan_address(self, address, port_low_end, port_high_end, udp_mode):
        if udp_mode:
            self.udpResult[address] = {'open_ports': []}
        else:
            self.result[address] = {'open_ports': []}

        for port in range(port_low_end, port_high_end + 1):
            self.check_port(address, port, udp_mode)

    def load_addresses_from_file(self):
        file = input('enter file path:')
        if os.path.isfile(file):
            with open(file, 'r') as in_file:
                for line in in_file:
                    self.addresses.append(line)

