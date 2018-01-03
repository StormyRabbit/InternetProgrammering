import os
class HostDiscover:
    def __init__(self):
        self.result = []
        pass

    def discover_local(self):
        pass

    def discover_hosts(self, *addresses):
        for ip in addresses:
            if os.system("ping -c 3 {}".format(ip)) == 0:
                self.result.append(ip)

        pass
