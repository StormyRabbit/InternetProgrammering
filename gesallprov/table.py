from flask_table import Table, Col

class ResultTable(Table):
    table_id = 'resultTable'
    address = Col('Address')
    port = Col('Port')

class TableEntry(object):
    def __init__(self, address, port):
        self.address = address
        self.port = port