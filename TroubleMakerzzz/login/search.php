<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css">
    <link rel="stylesheet" href="mystyle.css"/>
</head>
<body class="#212121 grey lighten-4">
    <div id="search" class="#f5f5f5 grey lighten-4 z-depth-5">
        <form id="myform" action="data_retrieve.php" method="post">
            <div class="input-field">
                <input type="search" id="book">
                <label for="search">Search Books</label>
            </div>
            <button class="btn red">Search Books</button>
        </form>
    </div>
    <div id="result">
    </div>
</body>
</html>