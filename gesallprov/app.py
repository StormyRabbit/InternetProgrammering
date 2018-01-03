from flask import Flask, render_template, request, flash
from PortScanner import PortScanner
import socket
from IO import *
from EncryptionMgr import EncryptionMgr
from forms import *
from table import *
app = Flask(__name__)
ps = PortScanner()
app.secret_key = "HEMLIS"
navigation = [{'toolName': 'Port Scan'}]
test_struct = {'85.24.253.25': {'open_ports': ['200', '500', '300', '500', '300', '500', '300']},
               '85.24.100.25': {'open_ports': ['1', '2', '3', '4', '5', '6', '7']}}


@app.route("/", methods=['GET', 'POST'])
def hello():
    if request.method == 'POST':
        _handle_post()

    return render_template('testing.html', table=_build_table(),
                           scan_form=ScanForm(), sql_form=SqlForm(), xml_form=XmlForm())


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
    print(request.form)
    address = request.form['address']
    port_low = int(request.form['startPort'])
    port_high = int(request.form['endPort'])
    udp_mode = request.form['submit'] != 'TCP Scan'
    ps.scan_address(address, port_low_end=port_low, port_high_end=port_high, udp_mode=udp_mode)





