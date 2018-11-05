const express = require("express");
const bodyParser = require("body-parser");
const socketIO = require("socket.io");
const http = require("http");

const app = express();
var server = http.createServer(app);
var io = socketIO(server);
app.use(bodyParser.json());

io.on("connection", socket => {
  console.log("User connected");
  socket.on("disconnect", () => {
    console.log("User disconnected");
  });

  app.get("/python", (req, res) => {
    console.log("ojas");
    res.send("ok");
    socket.emit("python", "true");
  });
});

server.listen(80, () => {
  console.log(`Server running on port 80`);
});
