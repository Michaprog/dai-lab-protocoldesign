Overview: SCP (Simple Calculator Protocol) client-server protocol that give 
the client the ability to do mathematical operation on a server and get the result
as the awnser to the question

For the communication will require an IP and a Port. 
The server will close the connection on request, or if something unexpenced happends

On the first call the server will send the possible operations and the syntax to use

- ADD < number 1 >  < number 2 >
- SUB < number 1 >  < number 2 >
- MUL < number 1 >  < number 2 >
- DIV < number 1 >  < number 2 >

and the server response :

- RESPONSE to operation < request > is < result >

If the request is not possible 

- The request was not possible

If the client want to stop the session :

- STOP


