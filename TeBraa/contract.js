const Web3 = require('web3');
const abi = [{
        "constant": false,
        "inputs": [{
            "name": "_name",
            "type": "string"
        }],
        "name": "addCandidate",
        "outputs": [],
        "payable": false,
        "stateMutability": "nonpayable",
        "type": "function"
    },
    {
        "constant": false,
        "inputs": [{
            "name": "_person",
            "type": "address"
        }],
        "name": "authorise",
        "outputs": [],
        "payable": false,
        "stateMutability": "nonpayable",
        "type": "function"
    },
    {
        "constant": false,
        "inputs": [],
        "name": "end",
        "outputs": [],
        "payable": false,
        "stateMutability": "nonpayable",
        "type": "function"
    },
    {
        "constant": false,
        "inputs": [{
            "name": "_voteIndex",
            "type": "uint256"
        }],
        "name": "vote",
        "outputs": [],
        "payable": false,
        "stateMutability": "nonpayable",
        "type": "function"
    },
    {
        "inputs": [{
            "name": "_name",
            "type": "string"
        }],
        "payable": false,
        "stateMutability": "nonpayable",
        "type": "constructor"
    },
    {
        "constant": true,
        "inputs": [{
            "name": "",
            "type": "uint256"
        }],
        "name": "candidates",
        "outputs": [{
                "name": "name",
                "type": "string"
            },
            {
                "name": "voteCount",
                "type": "uint256"
            }
        ],
        "payable": false,
        "stateMutability": "view",
        "type": "function"
    },
    {
        "constant": true,
        "inputs": [],
        "name": "electionName",
        "outputs": [{
            "name": "",
            "type": "string"
        }],
        "payable": false,
        "stateMutability": "view",
        "type": "function"
    },
    {
        "constant": true,
        "inputs": [],
        "name": "getNumCandidate",
        "outputs": [{
            "name": "",
            "type": "uint256"
        }],
        "payable": false,
        "stateMutability": "view",
        "type": "function"
    },
    {
        "constant": true,
        "inputs": [],
        "name": "owner",
        "outputs": [{
            "name": "",
            "type": "address"
        }],
        "payable": false,
        "stateMutability": "view",
        "type": "function"
    },
    {
        "constant": true,
        "inputs": [],
        "name": "totalVotes",
        "outputs": [{
            "name": "",
            "type": "uint256"
        }],
        "payable": false,
        "stateMutability": "view",
        "type": "function"
    },
    {
        "constant": true,
        "inputs": [{
            "name": "",
            "type": "address"
        }],
        "name": "voters",
        "outputs": [{
                "name": "authorised",
                "type": "bool"
            },
            {
                "name": "voted",
                "type": "bool"
            },
            {
                "name": "vote",
                "type": "uint256"
            }
        ],
        "payable": false,
        "stateMutability": "view",
        "type": "function"
    }
];
const contractAddress = '0xD2a0d3fDA742E73646dC27ed2515EAC9d5A8c6cC';

var web3 = new Web3(new Web3.providers.HttpProvider("http://localhost:7545"));

const contract = new web3.eth.Contract(abi, contractAddress);


module.exports = {
    contract,
    abi,
    contractAddress
};