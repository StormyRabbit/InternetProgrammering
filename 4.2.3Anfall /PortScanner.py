import socket
from contextlib import closing
class PortScanner:
    portNumber = 0
    openPortsList = []
    openPortsDict = {}
    highestPortNumber = 65535
    targetIP = ""

    def startscan(self, ip):
        self.targetIP = ip
        for number in range(0, self.highestPortNumber):
            with closing(socket.socket(socket.AF_INET, socket.SOCK_STREAM)) as client:
                result = client.connect_ex( (self.targetIP, number) )
                if result == 0:
                    print("port number: %d" )
                    self.openPortsList.append(number)

    def detectProtocol(self):
        # TODO detect protocol
        print('ping')

