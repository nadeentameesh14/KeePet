// const router = require('express').Router()
// const auth = require('./auth/auth')

// router.use('/auth', auth)

// module.exports = router


const express = require('express')
const mysql = require('mysql')
const app = express();

var bodyParser = require('body-parser');

app.use(bodyParser.json()); // support json encoded bodies
app.use(bodyParser.urlencoded({ extended: false })); // support encoded bodies


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



app.post("/pet/create",  function (req, res) {
  
    if( !req.body )
      return res.sendStatus(400);

  let sql = "INSERT INTO pet (name, age , breed , type , gender , seller , description , vaccination  ) VALUES (" ;
  sql = sql + "'" + req.body.name + "'," + req.body.age + ",'" + req.body.breed + "','" + req.body.type + "','" + req.body.gender + "','" + "moussa"  + "','" +  req.body.description  + "'," +  req.body.vaccination + ")";
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


app.get("/pet/get", function (req, res) {
  
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

// app.get("/sentSMS", function (req, res) {
//   // q = url.parse(req.url, true).query;
//   let sql = "UPDATE sms SET sent = 1 WHERE id =" ;
//   sql = sql + req.query.id ;
//   let query = db.query(sql, function (err, result) {
//     if (err) {
//       console.log(err);
//       throw err;
//     }
//     else{
//       res.send(result);
//       console.log("1 record changed")
//     };
//   });
// });

app.get('/', (req, res) => {
  res.send('Hello World!')
});

app.listen( 3000 , () => {
  console.log('Example app listening on port 3000 !')
});