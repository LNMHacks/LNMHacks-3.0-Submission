const express = require('express');
const router = express.Router();
const request = require('request');
const middleware = require('../middleware');


router.get("/movies", middleware.isLoggedIn, (req, res) => {
    const url = "https://api.themoviedb.org/3/discover/movie?api_key=c99295d4966e312cb3db1f56ddf2991b&sort_by=popularity.desc";
    request(url, function (error, response, body) {
        if (!error && response.statusCode == 200) {
            let data = JSON.parse(body);
            res.render("movies/index", { data: data });
        }
    });
});


router.get("/movies/index", middleware.isLoggedIn, (req, res) => {
    const searchTerm = req.query.name;
    const url = "https://api.themoviedb.org/3/search/movie?api_key=c99295d4966e312cb3db1f56ddf2991b&query=" + searchTerm;
    request(url, function (error, response, body) {
        if (!error && response.statusCode == 200) {
            let data = JSON.parse(body);
            res.render("movies/list", { data: data });
        }
    });
});


router.get("/movies/:id", middleware.isLoggedIn, (req, res) => {
    const movieId = req.params.id;
    const url = "https://api.themoviedb.org/3/movie/" + movieId + "?api_key=c99295d4966e312cb3db1f56ddf2991b";
    request(url, function (error, response, body) {
        if (!error && response.statusCode == 200) {
            let data = JSON.parse(body);
            res.render("movies/show", { data: data });
        }
    });
});


module.exports = router;