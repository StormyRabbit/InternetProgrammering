#!/bin/python3
from flask import Flask, render_template, request
from PortScanner import PortScanner
from IO import *
from EncryptionMgr import EncryptionMgr
from forms import *
from table import *
app = Flask(__name__)
# APPLICATION OBJECTS
ps = PortScanner()
xmlExp = XMLExporter()
em = EncryptionMgr()
db = SqlExporter()
# TEMPLATE VARIABLES
app.secret_key = "HEMLIS"
navigation = [{'toolName': 'Port Scan'}]


@app.route("/", methods=['GET', 'POST'])
def index():
    if request.method == 'POST':
        _handle_post()
    return render_template('index.html', table=_build_table(),
                           scan_form=ScanForm(), keyLoaded=em.isKeyLoaded)


@app.route("/createKeyFile", methods=['POST'])
def create_key_file():
    em.create_rnd_key_file(request.get_json()['file_name'])
    return "200"


@app.route("/loadKeyFile", methods=['POST'])
def load_key_file():
    em.load_key_file(request.get_json()['file_name'])
    return "200"


@app.route("/loadXML", methods=['POST'])
def _load_result_xml():
    ps.result.clear()
    ps.result = xmlExp.load_from_xml(request.get_json()['file_name'])
    return "200"


@app.route("/saveEncryptedXML", methods=['POST'])
def save_encrypted_xml():
    xml_result = xmlExp.create_xml(ps.result)
    xmlString = xmlExp.stringify(xml_result)
    em.save_with_key_file(request.get_json()['file_name'], xmlString)
    return "200"


@app.route("/loadEncryptedXML", methods=['POST'])
def load_encrypted_xml():
    ps.result.clear()
    em.load_encrypted_file(request.get_json()['file_name'])
    decrypted_xml = em.decrypt(em.encrypted_data, em.loadedKey)
    ps.result = xmlExp.load_from_xml_string(decrypted_xml)
    return "200"


@app.route("/saveXML", methods=['POST'])
def _save_result_xml():
    xml = xmlExp.create_xml(ps.result)
    xmlExp.write_xml_to_file(xml, request.get_json()['file_name'])
    return "200"


@app.route("/importDB", methods=['GET', 'POST'])
def db_import():
    if request.method == 'GET':
        return render_template('sqlIO.html', sql_form=SqlForm(), _import=True)
    else:
        ps.result.clear()
        db.connect(request.form['host'], request.form['user'], request.form['pw'], request.form['db'])
        ps.result = db.load_from_mysql()
        db.disconnect()
        print(ps.result)
        return "200"


@app.route("/exportDB", methods=['GET', 'POST'])
def db_export():
    if request.method == 'GET':
        return render_template('sqlIO.html', sql_form=SqlForm(), _import=False)
    else:
        db.connect(request.form['host'], request.form['user'], request.form['pw'], request.form['db'])
        db.export_to_mysql(ps.result)
        db.disconnect()
        return "200"


def _handle_post():
    if request.form['submit'] == 'TCP Scan':
        start_scan()


def _build_table():
    items = []
    for k, v in ps.result.items():
        for port in v['open_ports']:
            items.append(TableEntry(k, port))
    return ResultTable(items)


def start_scan():
    address = request.form['address']
    port_low = int(request.form['startPort'])
    port_high = int(request.form['endPort'])
    udp_mode = request.form['submit'] != 'TCP Scan'
    ps.scan_address(address, port_low_end=port_low, port_high_end=port_high, udp_mode=udp_mode)


if __name__ == "__main__":
    app.run(host='127.0.0.1', port=5000)
