# LauzHack2016
Project for the LauzHack Hackaton 2016, you can check it out here: [https://lauzhack.scapp.io/](https://lauzhack.scapp.io/)

Winner of the Swisscom Challenge

## What it does

By giving a number of users, you can set a common destination with different starting points and times for each user, and our program will find the paths that optimizes the time spent together in the trains, instead of everybody travelling by themselves.

## How we built it

The backend is built in Java. That is the parser for the JSON data returned by the SBB API, the backtracking algorithm selects a master route, which is the one that can be the most easily joined by the others, and then tries to merge all the routes as best as possible.
The frontend consists of Java Server Pages and JSTL with expression language. It displays the routes on the Google Maps API and on travel tables indicating for each traveler the trains to take, at what time and from where.

## Accomplishments that we're proud of

Having a working and polished app, in less than 24 hours.
The three of us took a long time designing the algorithm together, working as a team in brainstorming it from scratch, that then it was easy and relatively quick to actually implement it.

Made with ❤️ from EPFL

Powered by Beer, RedBull & Coffee 
