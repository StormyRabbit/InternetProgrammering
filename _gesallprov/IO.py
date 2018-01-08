from lxml import etree
import pymysql
from io import StringIO


class SqlExporter:
    def __init__(self):
        self.connection = None
        self.cursor = None

    def connect(self, host, user, password, database):
        self.connection = pymysql.connect(host, user, password, database)
        self.cursor = self.connection.cursor()
        pass

    def disconnect(self):
        if self.connection is not None:
            self.connection.close()

    def create_table(self):
        if self.connection is None:
            raise TypeError("Connection is None, call connect first")
        table_creation_query = "CREATE TABLE portScan(ADDRESS CHAR(15) NOT NULL, PORT CHAR(5) NOT NULL)"
        self.cursor.execute(table_creation_query)
        self.connection.commit()

    def export_to_mysql(self, results):
        if self.connection is None:
            raise TypeError("Connection is None, call connect first")
        for entry, result in results.items():
            for port in result['open_ports']:
                insert_query = "INSERT INTO portScan (ADDRESS, PORT) VALUES ('%s', '%s');" % (entry, port)
                try:
                    self.cursor.execute(insert_query)
                    self.connection.commit()
                except pymysql.InternalError:
                    self.connection.rollback()
            pass
        pass

    def string_load_from_mysql(self, query):
        result = self._execute_query(query)
        return self._build_dictionary(result)

    def _execute_query(self, query):
        try:
            self.cursor.execute(query)
            return self.cursor.fetchall()
        except pymysql.InternalError:
            pass

    def _build_dictionary(self, results):
        ret_struct = {}
        for row in results:
            ret_struct[row[0]] = {'open_ports': []}
        for row in results:
            ret_struct[row[0]]['open_ports'].append(row[1])
        return ret_struct

    def load_from_mysql(self):
        sql = "SELECT * FROM portScan"
        result = self._execute_query(sql)
        return self._build_dictionary(result)


class XMLExporter:
    @staticmethod
    def write_xml_to_file( tree, file_name):
        etree.ElementTree(tree).write(file_name, pretty_print=True, xml_declaration=True, encoding="utf-8")

    @staticmethod
    def encrypted_write_xml_to_file(em, tree, key, file_name):
        encrypted_tree = em.encrypt(tree, key)
        em.write_encrypted_to_file(encrypted_tree, file_name)

    @staticmethod
    def stringify(xml):
        return etree.tostring(xml)

    @staticmethod
    def create_xml(results):
        root = etree.Element("PortScanResults")
        for ip, result in results.items():
            entity = etree.SubElement(root, 'PortScanEntity')
            ip_address = etree.SubElement(entity, 'ip')
            ip_address.text = ip
            for port in result['open_ports']:
                etree.SubElement(entity, 'port').text = str(port)
        return root

    def load_from_xml_string(self, xml_string):
        ret_struct = {}
        root = etree.fromstring(xml_string)
        for _, b in etree.iterwalk(root, tag='PortScanEntity'):
            for entry in b:
                if entry.tag == 'ip':
                    current_address = entry.text
                    ret_struct[current_address] = {'open_ports': []}
                if entry.tag == 'port':
                    ret_struct[current_address]['open_ports'].append(entry.text)
        return ret_struct

    @staticmethod
    def load_from_xml(file_name):
        ret_struct = {}
        for _, b in etree.iterparse(file_name, tag='PortScanEntity'):
            for entry in b:
                if entry.tag == 'ip':
                    current_address = entry.text
                    ret_struct[current_address] = {'open_ports': []}
                if entry.tag == 'port':
                    ret_struct[current_address]['open_ports'].append(entry.text)
        return ret_struct

