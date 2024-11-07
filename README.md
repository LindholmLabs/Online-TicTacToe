# Online TicTacToe
A multiplayer TicTacToe game for the course distributed computing.

## Introduction
This is a project report for a group project in distributed computing. The assignment is to create an online multiplayer tic-tac-toe game in java. The web server used is a glassfish server, which communicates with the java swing client using SOAP. Games, users and scores are stored in a mysql server hosted using XAMPP.

## Project setup

### Glassfish
To run the provided web service, responsible for communicating with the clients and database, a glassfish version 7.0.6 server was created. This version of glassfish does not run on the newest version of the JDK (22), therefore JDK 20 was installed and chosen for this project.
![glassfish server creation](./media/glassfish_server_creation1.png)
![glassfish server creation](./media/glassfish_server_creation2.png)

### Web service setup
After running the tictactoe web service, this interface was presented, with all the necessary functions to get our project running. This also gave us access to the WSDL file, which we can use to automatically generate a client interface for communicating with the web service.
![web service web interface](./media/glassfish_web_interface.png)


### Client to server interface
To generate the SOAP interface, a new web service client was created in netbeans using a link to the WSDL file. Thereafter, necessary dependencies were added to allow the project to run; `org.glassfish.metro : webservices-rt : 3.0.3` and `com.sun.xml.ws : jaxws-maven-plugin : 4.0.1`.
![web service client creation](./media/client_interface_creation.png)
![client dependencies](./media/client_dependency_addition.png)<br>
After then building the client project, the files `TicTacToeWS.java` and `TicTacToeWebService.java` were automatically generated, which now allow us to call procedures on the TicTacToeWS.