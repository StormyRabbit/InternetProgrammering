#!/bin/bash
clear
curl --silent -H "Content-Type: application/json" --data '{"file_name": "key"}' http://localhost:5000/createKeyFile > /dev/null
curl --silent -H "Content-Type: application/json" --data '{"file_name": "key"}' http://localhost:5000/loadKeyFile > /dev/null
curl -H "Content-Type: application/json" --data '{"file_name": "xmlExportTest"}' http://localhost:5000/loadXML 
curl --silent -H "Content-Type: application/json" --data '{"file_name": "encryptedFile"}' http://localhost:5000/saveEncryptedXML > /dev/null
curl --silent -H "Content-Type: application/json" --data '{"file_name": "encryptedFile"}' http://localhost:5000/loadEncryptedXML > /dev/null
