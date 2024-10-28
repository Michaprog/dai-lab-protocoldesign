# SCP SIMPLE CALULATOR PROTOCAL

## Overview
scp is a client-server protocal. The client connects to a server and askes for a calculation to be do. The Server sends a responses with the number or an error message if there is an error. 


## Transport layer protocal 
scp uses TCP. The client establishes the connection. It has to know the IP address of the server. The server listens on TCP port 54545.
The server closes the connection when the user sends the close commande. 

## Messages

In this projetct we are doing to implement the following functions: 
- Addition : ADD <First_number> <Second_number> - This command will add two numbers 
- Substration : SUB <First_number> <Second_number> - This command will subtract two numbers 
- Multiplication : MULT <First_number> <Second_number> - This command will multiply two numbers 
- Division : DIV <First_number> <Second_number> - This command will devide two numbers 
- Quit : QUIT - This command will send the commande to the server to end the connection

If request is not possible : 
- "The request was not possible"

The server's response : 
- "RESPONSE the operation: <operation> is <result>"


The first message from server then connecting : 

- ADD <First_number> <Second_number>
- SUB <First_number> <Second_number>
- MULT <First_number> <Second_number>
- DIV <First_number> <Second_number>
- QUIT





