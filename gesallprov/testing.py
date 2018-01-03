from PortScanner import PortScanner
"""
test_struct = {'85.24.253.25' : {'open_ports': ['200', '500', '300']}}
xmlexp = XMLExporter()
#tmp = xmlexp.create_xml(testStruct)
#xmlexp.write_xml_to_file(tmp, "asd2.xml")
#xmlexp.encrypted_write_xml_to_file(tmp, "testKey", 'encryptedFile', "testIV")
se = SqlExporter()
xmlexp.load_from_xml('asd.xml')
se.connect('mysql.dsv.su.se', 'lape5427', 'railaeyeeh6E', 'lape5427')
#se.load_from_mysql()
#se.export_to_mysql(test_struct)
"""

ps = PortScanner()
addressList = ('176.10.152.20', '176.20.152.20')
ps.scan_address(*addressList, port_low_end=1, port_high_end=10, udp_mode=False)
print(ps.result)