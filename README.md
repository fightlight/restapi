API DOC
=======

Image uploading
---------------
url: /images
method: POST
ContentType: multipart/form-data
Accept: application/json

### example
Response:
```
{
  "imageUri": "/uploadedFiles/1440727622691_569147964.jpg"
}
```


Adding a user
-------------
url: /users
method: POST
ContentType: application/json
Accept: application/json

### example
Request:
```
{
  "username": "Nick",
  "email": "nick@mail.com",
  "imageUri": "/uploadedFiles/1440727622691_569147964.jpg"
}
```
Response: 4
Note: "username" is a required field


Getting user info
-----------------
url: /users/info/{userId}
method: GET
Accept: application/json

### example
Request: /users/info/4
Response:
```
{
  "id": 4,
  "username": "Nick",
  "email": "nick@mail.com",
  "imageUri": "/uploadedFiles/1440727622691_569147964.jpg"
  "userStatus": "ONLINE",
  "statusUpdateTime": 1440687693000
}
```


Changing user status
--------------------
url: /users/status
method: PUT
ContentType: application/json
Accept: application/json

### example
Request:
```
{
  "id": "4",
  "userStatus": "ONLINE"
}
```
Response:
```
{
  "userId": 4,
  "oldUserStatus": "ONLINE",
  "newUserStatus": "OFFLINE"
}
```
Note: userStatus is in UPPER CASE only (ONLINE, OFFLINE), all fields in request are required



Getting statistics
------------------
url: /stat/userStatus/{userStatus}/timestamp/{timestamp}
     /stat/userStatus/{userStatus}
     /stat/timestamp/{timestamp}
     /stat
method: GET
Accept: application/json

### example
Request:
    /stat/userStatus/ONLINE/timestamp/1440728447404
Response:
```
{
  "requestTimestamp": 1440728882689,
  "users": [
    {
      "username": "Jack",
      "imageUri": "/uploadedFiles/1440728523834_1726524085.jpg",
      "userStatus": "ONLINE"
    },
    {
      "username": "Mike",
      "imageUri": "/uploadedFiles/1440728543277_807466190.jpg",
      "userStatus": "ONLINE"
    },
    {
      "username": "Nick",
      "imageUri": "/uploadedFiles/1440727622691_569147964.jpg",
      "userStatus": "ONLINE"
    }
  ]
}
```
Note: userStatus is in UPPER CASE only (ONLINE, OFFLINE)


Error format
------------
### 1st example:
```
{
  "timestamp": 1440732375905,
  "status": 404,
  "error": "Not Found",
  "message": "No message available",
  "path": "/state"
}
```

### 2nd example:
```
{
  "timestamp": 1440732411109,
  "status": 500,
  "error": "Internal Server Error",
  "message": "User not found",
  "path": "/users/info/0"
}
```


Deploy instructions
===================
0. Edit MySQL username and password in "application.properties", "# Datasource" section.
   Edit DB name (serverapidb by default) if needed
   Create DB scheme `<DB name>` in MySQL.
   Build project with Maven
0. Install tomcat, edit connectors configuration in `<tomcatDir>/conf/server.xml`
```
<Connector port="8080" protocol="HTTP/1.1"
               connectionTimeout="20000"
               maxThread=50
               redirectPort="8443" />
<Connector port="8081" protocol="HTTP/1.1"
               connectionTimeout="20000"
               maxThread=150
               redirectPort="8444" />
```
0. Install nginx, add to "http" section of nginx.conf the following
```
server {
    listen localhost:80;
    location / {
       proxy_pass http://127.0.0.1:8080/;
    }
    location /users/info/ {
       proxy_pass http://127.0.0.1:8081/users/info/;
    }
    location /stat/ {
       proxy_pass http://127.0.0.1:8081/stat/;
    }
}
```
0. Copy project's ".war" file to `<tomcatDir>/webapps`
0. Wait for deploy and try url
`http://localhost/<war filename without ext>`



Spent time
==========
Design 5h
Coding 25h
Docs 2h
Learning ...