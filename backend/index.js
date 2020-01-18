const express = require('express')
const mysql = require('mysql')
const app = express();

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


app.get("/auth/register", function (req, res) {
    // q = url.parse(req.url, true).query;
    let sql = "INSERT INTO user (email , first , last , password , phone ) VALUES (" ;
    sql = sql + "'" + req.body.email + "','" + req.body.first + "','" + req.body.last + "','" + req.body.password + "'," + req.body.phone +  ")";
    let query = db.query(sql, function (err, result) {
      if (err) {
        console.log(err);
        console.log( req.body.email ) ;
        res.send(err);
        throw err;
      }
      else{
        res.send(result);
        console.log("user inserted sucessfully")
      };
    });
  });



app.get("/createPet", function (req, res) {
  // q = url.parse(req.url, true).query;
  let sql = "INSERT INTO keepet (name, age , breed , type , gender , seller ) VALUES (" ;
  sql = sql + req.body.name + "," + req.body.age + "," + req.body.breed + "," + req.body.type + "," + req.body.gender + "," + req.body.seller + ")";
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

app.get("/getSMS", function (req, res) {
  // q = url.parse(req.url, true).query;
  let sql = "SELECT * FROM sms WHERE sent = 0 " ;
  sql = sql + "ORDER BY id DESC LIMIT 1" ;
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

app.get("/sentSMS", function (req, res) {
  // q = url.parse(req.url, true).query;
  let sql = "UPDATE sms SET sent = 1 WHERE id =" ;
  sql = sql + req.query.id ;
  let query = db.query(sql, function (err, result) {
    if (err) {
      console.log(err);
      throw err;
    }
    else{
      res.send(result);
      console.log("1 record changed")
    };
  });
});

app.get('/', (req, res) => {
  res.send('Hello World!')
});

app.listen( 3000 , () => {
  console.log('Example app listening on port 3000 !')
});