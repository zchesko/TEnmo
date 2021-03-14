Module 2 Capstone - TEnmo
Congratulations—you've landed a job with TEnmo, whose product is an online payment service for transferring "TE bucks" between friends. However, they don't have a product yet. You've been tasked with writing a RESTful API server and command-line application.

Use cases
Required Use Cases
You should attempt to complete all of the following required use cases.

[COMPLETE] As a user of the system, I need to be able to register myself with a username and password.
A new registered user starts with an initial balance of 1,000 TE Bucks.
The ability to register has been provided in your starter code.
[COMPLETE] As a user of the system, I need to be able to log in using my registered username and password.
Logging in returns an Authentication Token. I need to include this token with all my subsequent interactions with the system outside of registering and logging in.
The ability to log in has been provided in your starter code.
As an authenticated user of the system, I need to be able to see my Account Balance.
As an authenticated user of the system, I need to be able to send a transfer of a specific amount of TE Bucks to a registered user.
I should be able to choose from a list of users to send TE Bucks to.
A transfer includes the User IDs of the from and to users and the amount of TE Bucks.
The receiver's account balance is increased by the amount of the transfer.
The sender's account balance is decreased by the amount of the transfer.
I can't send more TE Bucks than I have in my account.
A Sending Transfer has an initial status of "approve."
As an authenticated user of the system, I need to be able to see transfers I have sent or received.
As an authenticated user of the system, I need to be able to retrieve the details of any transfer based upon the transfer ID.
