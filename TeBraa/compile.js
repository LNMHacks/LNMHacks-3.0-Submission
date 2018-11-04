const path = require('path');
const fs = require('fs');
const solc = require('solc');

const pollingPath = path.resolve(__dirname, 'contracts', 'Polling.sol');
// console.log(inboxPath);
const source = fs.readFileSync(pollingPath, 'utf-8');
const bytecode = solc.compile(source).contracts[':Polling'].bytecode;
const interface = solc.compile(source).contracts[':Polling'].interface;
module.exports = {
    bytecode,
    interface
};