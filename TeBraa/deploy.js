const Web3 = require('web3');
const HDWalletProvider = require('truffle-hdwallet-provider');
const {
    bytecode,
    interface
} = require('./compile');
const mnemonics = 'embark grape brother misery raise stable hotel security item either oppose then';
const provider = new HDWalletProvider(mnemonics, 'https://rinkeby.infura.io/v3/18504985fe7d4eceb70e35612c5e6e6a');
const web3 = new Web3(provider);
console.log('provider added...');
let accounts, poll;
let contract_address;
const deploy = async() => {
    console.log('Getting account...')
    accounts = await web3.eth.getAccounts();
    console.log('Attempting to deploy from account ', accounts[0]);
    poll = await new web3.eth.Contract(JSON.parse(interface))
        .deploy({
            data: '0x' + bytecode,
            arguments: ['hi there']
        })
        .send({
            gas: '1000000',
            from: accounts[0]
        });
    poll.setProvider(provider);
    // contract_addresss=poll.options.address;
    console.log('Contract deployed at address ', poll.options.address);
    // console.log(poll);

};
deploy();