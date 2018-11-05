const express = require('express');
const router = express.Router();
const passport = require('passport');
const User = require('../models/user');


router.get("/login", (req, res) => {
    res.render("login");
});


router.post('/login', passport.authenticate('local', {
    successRedirect: '/movies',
    failureRedirect: '/login'
}), function (req, res) {

});


router.get("/signup", (req, res) => {
    res.render("signup");
});


router.post('/signup', function (req, res) {
    User.register(new User({ username: req.body.username }), req.body.password, function (err, user) {
        if (err) {
            req.flash("error", err.message);
            return res.redirect('/signup');
        }
        passport.authenticate('local')(req, res, function () {
            req.flash("success", "Welcome " + user.username);
            res.redirect('/movies');
        });
    });
});


router.get('/logout', function (req, res) {
    req.logout();
    req.flash("success", "Logged you out!");
    res.redirect('/');
});

module.exports = router;