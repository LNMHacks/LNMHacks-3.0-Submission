const ejs = require('ejs');
const express = require('express');
const path = require('path');
const app = express();
const url = require('url');
var bodyParser = require('body-parser');
// var app = express();
app.use(bodyParser.urlencoded({
    extended: false
}));
const port = process.env.PORT || 8085;
app.set('view engine', 'ejs');

app.get('/', (req, res) => {
    res.render('createpoll');
    res.end();
});

app.get('/aboutus', (req, res) => {
    res.render('aboutus');
});
app.get('/instruction', (req, res) => {
    res.render('instruction');
});
app.get('/newpoll', (req, res) => {
    res.render('newpoll');
});
app.get('/addcandidate', (req, res) => {
    console.log(req.body.no_of_candidates);
    res.render('addcandidate', {
        data: req.body
    });
});
app.get('/stats', (req, res) => {
    res.render('stats');
});
app.get('/votefor', (req, res) => {
    res.render('votefor');
});
app.get('/try', (req, res) => {
    res.render('try');
});


app.listen(port, () => {
    console.log(`server is running at port ${port}...`);
});