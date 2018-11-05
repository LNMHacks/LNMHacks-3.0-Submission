const express = require("express");
const app = express();
const bodyParser = require('body-parser');
const methodOverride = require('method-override');
const flash = require('connect-flash');
const mongoose = require('mongoose');
const passport = require('passport');
const LocalStrategy = require('passport-local');
const User = require('./models/user');


// REQUIRING ROUTES
const authRoutes = require('./routes/auth');
const movieRoutes = require('./routes/movies');
const profileRoutes = require('./routes/profile');


// CONNECTING TO DATABASE
mongoose.connect("mongodb://localhost:27017/movie_guide", { useNewUrlParser: true });

app.set("view engine", "ejs");
app.use(express.static(__dirname + "/public"));
app.use(bodyParser.urlencoded({ extended: true }));
app.use(methodOverride("_method"));
app.use(flash());



// PASSPORT CONFIG
app.use(require('express-session')({
    secret: "I love Stranger Things",
    resave: false,
    saveUninitialized: false
}));
app.use(passport.initialize());
app.use(passport.session());
passport.use(new LocalStrategy(User.authenticate()));
passport.serializeUser(User.serializeUser());
passport.deserializeUser(User.deserializeUser());


app.use(function (req, res, next) {
    res.locals.currentUser = req.user;
    res.locals.error = req.flash("error");
    res.locals.success = req.flash("success");
    next();
});


// REQUIRING ROUTES
app.use(authRoutes);
app.use(movieRoutes);
app.use(profileRoutes);


app.get("/", (req, res) => {
    res.render("landing");
});

app.get("*", (req, res) => {
    res.render("notFound");
});

app.listen(3000, () => {
    console.log("Server has started !!");
});