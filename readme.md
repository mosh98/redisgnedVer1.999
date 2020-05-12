# User and Dogs
### link: https://redesigned-backend.herokuapp.com/

## User

GET user

user/find?username=XXX

POST register user

user/register

{ 
	"username": "XXXX",
	"email": "XXX@hotmail.com",
	"name": "HAHA",
	"password": "1234",
	"dateOfBirth": "22-03-2020",
	"genderType": "FEMALE"
}

POST register user by mail only, applicable for facebok users
..../registerWithMail
{ 
	"username": "leave a blank string",
	"email": "XXX@hotmail.com",
	"name": "HAHA",
	"password": "1234",
	"dateOfBirth": "22-03-2020",
	"genderType": "FEMALE"
}

## Dogs

GET  the dog by

/dogs/find?id= xx

DELETE
/dogs/delete?id= xx


POST register dog using post

/user/dog/register?name=XXXX&breed=XXXX&age=XX&gender=XXXX&description=XXXX&owner=XXXXX


GET list of dogs a user ownes
	/user/getMyDogs?username= XXX



## .../wastebin

GET .../find?All
	Returnerar alla papperkorgar i JSON.
	{
		"latitude":"XXX",
		"longitude":"YYY"
	}
	
GET .../find?Latitude=XXX&Longitude=YYY&MaxDistance=ZZZ
	Returnerar alla papperskorgar inom ZZZ meter från XXX.YYY i JSON.
	{
		"latitude":"XXX",
		"longitude":"YYY"
	}
	
GET .../find
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
	
	
