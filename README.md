# Unit Testing

--> The project consists of the covid-unit-api and web folders<br />

## Requirements for covid-unit-api:
--> `cd covid-unit-api` then follow the instructions below<br />
--> Download the MySQL Installer 8.0.24 for windows<br/>
--> Open the folder in IntelliJ<br />
--> Import the maven dependencies using the pom.xml folder<br/>
--> Enter your MYSQL username and password to the application.properties file<br/>
--> DO NOT PUSH YOUR application.properties FILE TO GITHUB<br/>
--> Open MySQL shell and first execute `\sql`<br/>
--> Connect to MYSQL using `\connect {username}@localhost:3306`<br />
--> Execute `CREATE DATABASE users;` in the shell<br />
--> Execute `\use users` in the shell<br />
--> Execute `CREATE TABLE users;` in the shell<br />
--> Execute `CREATE TABLE symptoms;` in the shell<br />
--> Switch back to IntelliJ and run the program which will start on port 8080<br/>

## Requirements for web:
This project was created with [Create React App](https://github.com/facebook/create-react-app).<br />
--> In the project directory, you should run `yarn`<br />
--> Then run `yarn start`<br />
--> Open http://localhost:3000 to view it in the browser.<br />
