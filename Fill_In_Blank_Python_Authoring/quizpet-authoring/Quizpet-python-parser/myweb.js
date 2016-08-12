var express = require('express');
var fs = require('fs');
var python = require('python-shell');

var bodyParser  =   require("body-parser");
var app = express();


//Here we are configuring express to use body-parser as middle-ware.
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());

app.get('/', function (req, res) {
    res.set('Content-Type', 'text/html');
    fs.readFile(__dirname + '/form.html', function(err, data) {
      if (!err) {
        res.send(data);
      } else {
        res.send("Error");
      }
    });
});

  app.post('/python-parser', function(req, res) {
    var code = { 'code': req.body.code, 'mode': req.body.mode || 'simple' };
    console.log( req.body.code);
    var options = {
      mode: 'json',
      pythonPath:  '/usr/local/bin/python3.4',
      pythonOptions: ['-u'], // -u is unbuffered output
      scriptPath: __dirname
    };

    var pyshell = new python('parser.py', options);
    pyshell.send(code);

    var response = '';

    pyshell.on('message', function(message) {
      response = message;
    });

    pyshell.end(function(err) {
      if (err) {
        res.json({ status: 'ERROR', message: 'Parsing failed.' });
      } else {
        res.json(response);
      }
    });
  });

app.listen(9090, function () {
  console.log('Example app listening on port 9090!');
});