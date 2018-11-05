var azure = require('azure-storage');
var blobService = azure.createBlobService('garbagee','cLwSD22JHD/aj6dpERvAf/3MqaeRvgR2Eh1jIyJN4/i0awP9Q8SGeSITLQftN5PcnvRx+H4ad5Z/penz2xCzcQ==');

blobService.createBlockBlobFromLocalFile('blobee', process.argv[2], process.argv[3], function(error, result, response) {
  if (!error) {
    console.log('Hogya upload');
  }
});