# User and Dogs
### link: https://redesigned-backend.herokuapp.com/

## User

GET user

https://redesigned-backend.herokuapp.com/user/find?username=XXX

POST register user

https://redesigned-backend.herokuapp.com/user/register

{ 
	"username": "XXXX",
	"email": "XXX@hotmail.com",
	"password": "1234",
	"date_of_birth": "22-03-2020",
	"gender_type": "FEMALE"
}

POST register user by mail only, applicable for facebok users
https://redesigned-backend.herokuapp.com/registerWithMail
{ 
	"username": "leave a blank string",
	"email": "XXX@hotmail.com",
	"password": "1234",
	"date_of_birth": "22-03-2020",
	"gender_type": "FEMALE"
}

PUT update user description

https://redesigned-backend.herokuapp.com/user/update?username=XXXX&description=XXXXX 

PUT update user password 

https://redesigned-backend.herokuapp.com/user/update?username=XXXX&password=XXXXX 

PUT update user date of birth

https://redesigned-backend.herokuapp.com/user/update?username=XXXX&date_of_birth=XXXXX

PUT update user email

https://redesigned-backend.herokuapp.com/user/update?username=XXXX&email=XXXXX

## Dogs

GET the dog by ID: 
https://redesigned-backend.herokuapp.com/dogs/find?id=xxx

GET list of dogs a user owns
https://redesigned-backend.herokuapp.com/user/getMyDogs?username= XXX

DELETE dog from database
https://redesigned-backend.herokuapp.com/dogs/delete?dogId=xxx

POST register dog using post
https://redesigned-backend.herokuapp.com/user/dog/register?owner=XXXX
body: 

{ 
	"name": "XXXX",
	"age": "1",
	"breed": "XXXX",
	"gender": "XXXX",
	"description": "XXXX"
}

PUT change the dogs name
https://redesigned-backend.herokuapp.com/dogs/update?id=XXXX&name=XXXX */

PUT change the dogs breed
https://redesigned-backend.herokuapp.com/dogs/update?id=XXXX&breed=XXXX */

PUT change the dogs age
https://redesigned-backend.herokuapp.com/dogs/update?id=XXXX&age=XXXX */

PUT change the dogs gender
https://redesigned-backend.herokuapp.com/dogs/update?id=XXXX&gender=XXXX */

PUT change the dogs description
https://redesigned-backend.herokuapp.com/dogs/update?id=XXXX&description=XXXX */

## .../wastebin

GET 
https://redesigned-backend.herokuapp.com/find?All
	Returnerar alla papperkorgar i JSON.
	{
		"latitude":"XXX",
		"longitude":"YYY"
	}
	
GET 
https://redesigned-backend.herokuapp.com/find?Latitude=XXX&Longitude=YYY&MaxDistance=ZZZ
	Returnerar alla papperskorgar inom ZZZ meter från XXX.YYY i JSON.
	{
		"latitude":"XXX",
		"longitude":"YYY"
	}
	
GET 
https://redesigned-backend.herokuapp.com/find
	Body:JSON
	{
		"latitude":"XXX",
		"longitude":"YYY",
		"maxdistance":"ZZZ"
	}
	Returnerar alla papperskorgar inom ZZZ meter från XXX.YYY i JSON.
	{
		"latitude":"XXX",
		"longitude":"YYY"
	}
	
	
