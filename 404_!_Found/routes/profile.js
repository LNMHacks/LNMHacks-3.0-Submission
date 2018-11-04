const express = require('express');
const router = express.Router();
const request = require('request');
const middleware = require('../middleware');
const User = require('./models/user');


router.get("/profile", (req, res) => {
    res.render("profile");
});



router.post("/profile/add/:movieId", middleware.isLoggedIn, (req, res) => {
    User.findById(req.params.id, (err, foundUser) => {
        if (err) {
            console.log(err);
        } else {
            foundUser.watchlist.push(movieId);
            res.redirect('/movies');
        }
    });
});

module.exports = router;