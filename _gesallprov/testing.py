from PortScanner import PortScanner
from IO import *
test_struct = {'85.24.253.25': {'open_ports': ['200', '500', '300', '500', '300', '500', '300']},
               '85.24.100.25': {'open_ports': ['1', '2', '3', '4', '5', '6', '7']}}
"""
xmlexp = XMLExporter()
ps = PortScanner()
em = EncryptionMgr()
em.create_rnd_key_file("newKey")
em.load_key_file('newKey')
xmlTestStruct = xmlexp.create_xml(test_struct)
em.save_with_key_file('testFil', etree.tostring(xmlTestStruct))
em.load_encrypted_file('testFil')
em.decrypt(em.encrypted_data, em.loadedKey)
ps.scan_address('176.10.152.20', port_low_end=1, port_high_end=6000, udp_mode=False)
print(ps.result)
testTree = xmlexp.create_xml(ps.result)
xmlexp.write_xml_to_file(testTree, 'test')
#xmlexp.write_xml_to_file(tmp, "asd2.xml")
xmlexp.load_from_xml('asd.xml')
#se.export_to_mysql(test_struct)
"""
ps = PortScanner()
se = SqlExporter()
se.connect('mysql.dsv.su.se', 'lape5427', 'railaeyeeh6E', 'lape5427')
ps.result = se.load_from_mysql()
print(ps.result)
