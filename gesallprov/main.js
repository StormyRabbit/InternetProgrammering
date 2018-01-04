const {dialog, app, BrowserWindow, Menu} = require('electron');
const http = require('http');
let mainWindow = null;
const requestOptions = {
  method: 'POST',
  protocol: 'http:',
  hostname: 'localhost',
  port: 5000,
  path: '/saveXML',
  headers: {
    'Content-Type': 'application/json',
  },
};

/* CALLBACKS */
createKeyFile = function(fileName) {
  requestOptions['path'] = '/createKeyFile';
  sendFileName(fileName);
};

loadKeyFile = function(fileName) {
  requestOptions['path'] = '/loadKeyFile';
  sendFileName(fileName[0]);
  mainWindow.reload();
};

loadXML = function(filePath) {
  requestOptions['path'] = '/loadXML';
  sendFileName(filePath[0]);
  mainWindow.reload();
};

loadEncryptedXML = function(filePath) {
  requestOptions['path'] = '/loadEncryptedXML';
  sendFileName(filePath[0]);
  mainWindow.reload();
};

saveEncryptedXML = function(filePath) {
  requestOptions['path'] = '/saveEncryptedXML';
  sendFileName(filePath);
};

saveXML = function(filePath) {
  sendFileName(filePath);
};

function sendFileName(fileName) {
  req = http.request(requestOptions);
  req.write(JSON.stringify({file_name: fileName}));
  req.end();
}

app.on('ready', function() {
  let subpy = require('child_process').spawn('python', ['./app.py']);
  let rq = require('request-promise');
  let mainAddr = 'http://localhost:5000/';

  let openWindow = function() {
    mainWindow = new BrowserWindow({width: 1200, height: 600});
    mainWindow.loadURL(mainAddr);
    mainWindow.on('closed', function() {
      mainWindow = null;
      subpy.kill('SIGINT');
    });
  };

  let startUp = function() {
    rq(mainAddr)
      .then(function(htmlString) {
        openWindow();
      })
      .catch(function(err) {
        startUp();
      });
  };
  startUp();
});
app.on('window-all-closed', function() {
  app.quit();
});

const template = [
  {
    label: 'Import',
    submenu: [
      {
        label: 'XML',
        click() {
          dialog.showOpenDialog(
            {
              properties: ['openFile'],
            },
            loadXML,
          );
        },
      },
      {
        label: 'Encrypted XML',
        click() {
          dialog.showOpenDialog({properties: ['openFile']}, loadEncryptedXML);
        },
      },
      {label: 'MySQL'},
    ],
  },
  {
    label: 'Export',
    submenu: [
      {
        label: 'XML',
        click() {
          dialog.showSaveDialog(
            {
              properties: ['saveFile'],
            },
            saveXML,
          );
        },
      },
      {
        label: 'Encrypted XML',
        click() {
          dialog.showSaveDialog({properties: ['saveFile']}, saveEncryptedXML);
        },
      },
      {label: 'MySQL'},
    ],
  },
  {
    label: 'Encryption',
    submenu: [
      {
        label: 'Create key file',
        click() {
          dialog.showSaveDialog({properties: ['saveFile']}, createKeyFile);
        },
      },
      {
        label: 'load key file',
        click() {
          dialog.showOpenDialog({properties: ['openFile']}, loadKeyFile);
        },
      },
    ],
  },
];
const menu = Menu.buildFromTemplate(template);
Menu.setApplicationMenu(menu);
