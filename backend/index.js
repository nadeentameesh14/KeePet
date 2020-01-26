// const router = require('express').Router()
// const auth = require('./auth/auth')

// router.use('/auth', auth)

// module.exports = router


const express = require('express')
const mysql = require('mysql')
const app = express();
const multer = require('multer');
const DIR = 'uploads' ;

const storage = multer.diskStorage({
  destination: function (req, file, callback) {
    callback(null, DIR );
  },
  filename: function (req, file, cb) {
    cb(null, "KEEPET" + new Date().toISOString().replace(/:/g, '-') + file.originalname );
  }
});

const upload = multer({storage : storage})

var bodyParser = require('body-parser');

app.use(bodyParser.json()); // support json encoded bodies
// app.use(bodyParser.urlencoded({ extended: false })); // support encoded bodies



//create connection
const db = mysql.createConnection({
  host  : 'localhost',
  user : 'root',
  password : '',
  database: "keepet"
})

//connect to mysql
db.connect((err) => {
  if(err)
    throw err ;
  console.log( 'mysql connected ... ')
});



app.post('/auth/login', function(request, response) {
	var email = request.body.email;
	var password = request.body.password;
	if (email && password) {
		connection.query('SELECT * FROM user WHERE email = ? AND password = ?', [email, password], function(error, results, fields) {
			if (results.length > 0) {
				request.session.loggedin = true;
				request.session.email = email;
			} else {
				response.send('Incorrect email and/or Password!');
			}			
			response.end();
		});
	} else {
		response.send('Please enter email and Password!');
		response.end();
	}
});



app.post("/auth/register", function (req, res) {

    if( !req.body )
      return res.sendStatus(400);

    // q = url.parse(req.url, true).query;
    let sql = "INSERT INTO user (email , first , last , password , phone ) VALUES (" ;
    sql = sql + "'" + req.body.email + "','" + req.body.first + "','" + req.body.last + "','" + req.body.password + "'," + req.body.phone +  ")";
    let query = db.query(sql, function (err, result) {
      if (err) {
        console.log(err);
        res.send(req);
        throw err;
      }
      else{
        res.send(result);
        console.log("user inserted sucessfully")
      };
    });
});



app.post("/pet/create", upload.single('image') , function (req, res) {

    if( !req.body )
      return res.sendStatus(400);


    console.log( req.file ) ;

    let sql = "INSERT INTO pet (name, age , breed , type , gender , seller , description , vaccination , city , area , adopted , image  ) VALUES (" ;
    sql = sql + "'" + req.body.name + "'," + req.body.age + ",'" + req.body.breed + "','" + req.body.type + "','" + req.body.gender + "','" + "moussa"  + "','" +  req.body.description  + "'," +  req.body.vaccination + ",'" + req.body.city +"','" + req.body.area + "'," + 0 + ",'" + DIR + "/" + req.file.originalname +"')";
    let query = db.query(sql, function (err, result) {
        if (err) {
        console.log(err);
        res.send(err);
        throw err;
        }
        else{
        res.send(result);
        console.log("pet inserted sucessfully")
        };
    });
});



app.get("/pet/get/nonadopted", function (req, res) { 
    if( !req.body )
        return res.sendStatus(400);
  
    let sql = "SELECT * FROM pet WHERE adopted = 0" ;
    let query = db.query(sql, function (err, result) {
      if (err) {
        console.log(err);
        throw err;
      }
      else{
        res.send(result);
        console.log("returned 1 row");
      };
    });
});



app.get("/pet/get/adopted", function (req, res) { 
    if( !req.body )
        return res.sendStatus(400);
  
    let sql = "SELECT * FROM pet WHERE adopted = 1" ;
    let query = db.query(sql, function (err, result) {
      if (err) {
        console.log(err);
        throw err;
      }
      else{
        res.send(result);
        console.log("returned 1 row");
      };
    });
});
   

app.get("/pet/get/byId", function (req, res) { 
  if( !req.body )
      return res.sendStatus(400);

  let sql = "SELECT * FROM pet WHERE id = " ;
  sql = sql + req.query.id ;
  let query = db.query(sql, function (err, result) {
    if (err) {
      console.log(err);
      throw err;
    }
    else{
      res.send(result);
      console.log("returned 1 row");
    };
  });
});
 



app.get('/', (req, res) => {
  res.send('Hello World!')
});

app.listen( 3000 , () => {
  console.log('Example app listening on port 3000 !')
});