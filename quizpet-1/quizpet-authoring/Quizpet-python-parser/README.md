# Python parser

This server tool parses Python 3 code and
returns the found concepts by traversing the AST
of the code. So you need to install python 3.4 on the server. You need to configure the python path in the file myweb.js:

pythonPath:  '/usr/local/bin/python3.4';

It's a node.js web project. You need to install node.js on the server.


It depends on three third party components.


npm install python-shell


npm install --save body-parser


npm install express


Open the terminal, go to the project directory and run this project using the following command:

node myweb.js


The default port number is 9090 .
