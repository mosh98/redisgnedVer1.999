.../wastebin

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