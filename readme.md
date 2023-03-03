# Java Sockets Test Project - Server
This is a project I'm doing in my free time to experiment with Java sockets. The idea is that this project be run as a server while [this one](https://github.com/arianensis/java-sockets-test-client) can have many instances running which connect to the server.

## What are Sockets?
A socket is a two way connection between two programs through a web address and a port, which gives a really fast way to send data between computers in an efficient way without the use of files such as json or HTTP infrastructure such an API REST. I'm just finding out about Sockets but they seem to me like a great tool to make real-time interacting programs such as chats or games, so I will use this project to experiment with these possibilities.

## Why Java?
Basically it's the language we learnt at school and it seemed very easy to set up a socket connection between two programs using it.

## This version
The repository tag **A.2** corresponds to a functioning, yet very simple, interface. It sets up a server and waits for connections. For each connection it creates an execution thread so it can interact independently with each client if needed. This will probably be the core of the app and thus used in other versions.

The current version makes use of Swing (Java libraries for GUIs) to create a small window for each incoming connection. The connection window will display the connection ID, the client address and the last message recieved through the class Message, which contains a string and will send back the same object as an answer. Additionally, if the first eight characters are numbers, they will be read as XXXXYYYY and the window will move to cordinates (XXXX,YYYY) of the screen. When a client disconnects, the corresponding thread on the server will be terminated and its window closed.