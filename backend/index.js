// const router = require('express').Router()
// const auth = require('./auth/auth')

// router.use('/auth', auth)

// module.exports = router


const express = require('express')
const mysql = require('mysql')
const app = express();
const multer = require('multer');
const bcrypt = require('bcrypt');
const DIR = 'uploads' ;
const jwt = require('jsonwebtoken');
const CheckAuth = require('./middleware/check-auth') ;



const storage = multer.diskStorage({
  destination: function (req, file, callback) {
    callback(null, DIR );
  },
  filename: function (req, file, cb) {
    cb(null,  new Date().toISOString().replace(/:/g, '-') + file.originalname );
  }
});

const upload = multer({storage : storage})

var bodyParser = require('body-parser');

app.use(bodyParser.urlencoded({ extended: false })); // support encoded bodies
app.use(bodyParser.json()); // support json encoded bodies


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


app.post("/auth/register",  function (req, res) {

    let sql = "SELECT * FROM user where email = " ;
    sql = sql + "'" + req.body.email + "'" ;
    let query = db.query ( sql , function (error, resultt){

      if (error) {
        console.log( "error occured" );
        res.send(error);
      }else{
        
        if( resultt.length < 1 ) {
          var hashedPassword = bcrypt.hashSync(req.body.password, 10);

          let sql = "INSERT INTO user (email , password  ) VALUES (" ;
          sql = sql + "'" + req.body.email + "','" + hashedPassword + "'" + ")";    
          let query = db.query(sql, function (err, result) {
            if (err) {
              console.log(err);
              res.send(err);
            }
            else{
              res.send(result);
              console.log("user inserted sucessfully")
            };
          });
        }else{
          console.log("This email has already been registerd")
          res.send("This email has already been registerd ")
        }
      }

    })    
});




app.post("/auth/login",  function (req, res) {
  let sql = "SELECT * FROM user where email = " ;
  sql = sql + "'" + req.body.email + "'" ;
  console.log(req.body);
  let query = db.query ( sql , function (err, result){
    
    if (err) {
      console.log( "error occured" );
      res.send(err);
    }else{

      if( result.length > 0 ){
        console.log( req.body.password , " " , result[0].password  )

        if( bcrypt.compareSync(req.body.password, result[0].password ) ) {

          const token = jwt.sign({
            email: result[0].email ,
            password: result[0].password
          },
          "JWTOKEN",{
            expiresIn: "1hr"
          }
        );
          console.log("Correct Credentials !")
          return res.status(200).send(token);

        } else {
          console.log('Incorrect password !')
          res.send('Incorrect password !')
        }

      }  
      else{
        console.log('Email not found !')
        res.send('Email not found !') ;
      } 
    }

  });
});


// upload.single('image') , 
// DIR + "/" + req.file.originalname


  app.post("/user/update", CheckAuth  , upload.single('image') , function (req, res) {
    
    console.log( req.file ) ;
    console.log(req.image) ;
    
    let sql = "UPDATE user SET email = '" + req.body.email  + "' , bio = '" + req.body.bio + "'" + ", name = '" + req.body.name + "'"  ;
    sql = sql + " WHERE email = '" + req.body.email + "'";
  
    let query = db.query(sql, function (err, result) {
        if (err) {
          console.log(err);
          res.send(err);
          throw err;
        }
        else{
          res.send(result);
          console.log("update info for user : " + req.body.email )
          console.log(req.body);
        };
    });

});




app.post("/user/like",  CheckAuth  ,  function (req, res) {
  
  if( !req.body )
    return res.sendStatus(400);

    let sql = "INSERT INTO likes (email , petid ) VALUES (" ;
    sql = sql + "'" + req.body.email + "','" + req.body.petid + "')";
    let query = db.query(sql, function (err, result) {
        if (err) {
        console.log(err);
        res.send(err);
        throw err;
        }
        else{
          res.send(result);
          console.log("pet Liked by " + req.body.email)
        };
    });

});


// CheckAuth ,
// upload.single('image') , 
// DIR + "/" + req.file.originalname

app.post("/pet/create", CheckAuth  , function (req, res) {

    if( !req.body )
      return res.sendStatus(400);

    // console.log( req.file ) ;
    console.log( req.body ) ;

    let sql = "INSERT INTO pet (name, age , breed , type , gender , seller , description , vaccination , city , area , adopted , image  ) VALUES (" ;
    sql = sql + "'" + req.body.name + "'," + req.body.age + ",'" + req.body.breed + "','" + req.body.type + "','" + req.body.gender + "','" + req.userData.email  + "','" +  req.body.description  + "'," +  req.body.vaccination + ",'" + req.body.city +"','" + req.body.area + "'," + 0 + ",'" + req.body.image  +"')";
    let query = db.query(sql, function (err, result) {
        if (err) {
        console.log(err);
        res.send(err);
        throw err;
        }
        else{
        res.send(result);
        console.log("pet inserted sucessfully")
        console.log(req.body);
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
        console.log("returned all pets (non adopted)");
      };
    });
});


app.post("/getUser", CheckAuth  ,  function (req, res) { 
    let sql = "SELECT * FROM user WHERE email = '"  + req.userData.email + "'";
    let query = db.query(sql, function (err , result) {
      if (err) {
        console.log(err);
        throw err;
      }
      else{
        res.send(result[0]);
        console.log(result[0])
        // console.log("returned user data for : " + req.userData.email );
      };
    });
});


app.get("/user/getlikes", function (req, res) { 
  if( !req.body )
      return res.sendStatus(400);

  let sql = "SELECT * FROM likes WHERE email = " + req.userData.email ;
  let query = db.query(sql, function (err, result) {
    if (err) {
      console.log(err);
      throw err;
    }
    else{

      let sql = "SELECT * FROM pet WHERE id = " + result.id ;
      let query = db.query(sql, function (error, resultt) {   
        res.send(result);
        console.log("returned the like histor for : " + req.userData.email );  
      });
    }
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
      console.log("returned 1 pet by id");

    };
  });

});
 

app.get('/', (req, res) => {
  res.send('Hello World!')
});

app.listen( 3000 , () => {
  console.log('Example app listening on port 3000 !')
});


