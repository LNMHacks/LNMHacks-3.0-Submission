const expect = require('chai').expect;
const ganache = require('ganache-cli');
const Web3 = require('web3');
const provider = ganache.provider();
const web3 = new Web3(provider);

var accounts, ;
beforeEach(async() => {

});

describe('#LotteryContract', () => {
    it('deploys a contract', () => {
        // console.log(lottery);
        // expect(lottery.options.address).is.a('string');
    });

    // it('allows one account to enter', async() => {
    //     await lottery.methods.enter().send({
    //         from: accounts[0],
    //         value: web3.utils.toWei('0.02', 'ether')
    //     });
    //     const players = await lottery.methods.getPlayers().call({
    //         from: accounts[0]
    //     });
    //     expect(accounts[0]).equals(players[0]);
    //     expect(players.length).equals(1);
    // });

    // it('allows multiple account to enter', async() => {
    //     await lottery.methods.enter().send({
    //         from: accounts[0],
    //         value: web3.utils.toWei('0.02', 'ether')
    //     });
    //     await lottery.methods.enter().send({
    //         from: accounts[1],
    //         value: web3.utils.toWei('0.02', 'ether')
    //     });
    //     await lottery.methods.enter().send({
    //         from: accounts[2],
    //         value: web3.utils.toWei('0.02', 'ether')
    //     });
    //     const players = await lottery.methods.getPlayers().call({
    //         from: accounts[0]
    //     });
    //     expect(accounts[0]).equals(players[0]);
    //     expect(accounts[1]).equals(players[1]);
    //     expect(accounts[2]).equals(players[2]);
    //     // console.log(players.length);
    //     expect(players.length).equals(3);
    // });

    // it('require minimum amount of ether', async() => {
    //     try {
    //         await lottery.methods.enter().send({
    //             from: accounts[0],
    //             value: 0
    //         });
    //         expect(false).is.to.be.false;
    //     } catch (error) {
    //         expect('error').is.exist;
    //     }
    // });

    // it('only manager can call pickWinner', async() => {
    //     try {
    //         await lottery.methods.pickWinner().send({
    //             from: accounts[1]
    //         });
    //         expect(false).is.to.be.false;
    //     } catch (error) {
    //         expect('error').is.exist;
    //     }
    // });

    // it('sends money to winner and reset player array', async() => {
    //     await lottery.methods.enter().send({
    //         from: accounts[0],
    //         value: web3.utils.toWei('2', 'ether')
    //     });

    //     const initialBalance = await web3.eth.getBalance(accounts[0]);
    //     // console.log('initial balance: ', initialBalance);
    //     await lottery.methods.pickWinner().send({
    //         from: accounts[0]
    //     });
    //     const finalBalance = await web3.eth.getBalance(accounts[0]);
    //     // console.log('final balance: ', finalBalance);
    //     const difference = finalBalance - initialBalance;
    //     // console.log('Difference: ', difference / 1000000000000000000);
    //     expect(difference).is.greaterThan(1.8);

    // });
});