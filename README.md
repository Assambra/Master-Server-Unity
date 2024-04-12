[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]

<!-- HEADER -->
<div align="center">
<h1>Master-Server-Unity</h1>
    <a href="https://github.com/Assambra">
        <img src="Github/Images/Assambra-Logo-512x512.png" alt="Logo" width="80" height="80">
    </a>
</div>

<!-- LINKS -->
<div align="center">
    <p align="center">
        <a href="https://github.com/Assambra/Master-Server-Unity/wiki"><strong>Explore the docs »</strong></a>
    </p>
    <p align="center">
        <a href="https://github.com/Assambra/Master-Server-Unity/releases">Get Latest</a>
        ·
        <a href="https://github.com/Assambra/Master-Server-Unity/issues">Request Feature</a>
        ·
        <a href="https://github.com/Assambra/Master-Server-Unity/issues">Report Bug</a>
    </p>
</div>

<!-- DEMO IMAGE -->
![Unity-Master-Server][product-image]

<a name="readme-top"></a>

<!-- TABLE OF CONTENTS -->
## Table of Contents
<ul>
    <li><a href="#key-features">Key Features</a></li>
    <li><a href="#project-structure">Project structure</a></li>
    <li><a href="#master-server">master-server</a></li>
        <ul>
            <li><a href="#setup-database">Setup database</a></li>
        </ul>
    <li><a href="#unity-server">Unity-Server</a></li>
        <ul>
            <li><a href="#unity-server">Create the Unity Server</a></li>
            <li><a href="#setup-server-path">Setup server.path variable</a> </li>
        </ul>
    <li><a href="#unity-client">Unity-Client</a></li>
    <li><a href="#contact">Contact</a></li>
</ul>



<!-- KEY FEATURES -->
## Key Features
<ul>
<li>Extending EzyFox Server's room management to launch a dedicated Unity server</li>
<li>One Unity-Server for all rooms/servers</li>
</ul>

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- PROJECT STRUCTURE-->
## Project structure
A brief overview of the structure of the project. We use three projects: master-server (EzyFox Server),
Unity-Server and Unity-Client. It is important to know that the Unity-Servers are also clients for the master-server
and we always use the Ezyfox Client SDK for communication between Unity-Client < -- > master-server and  master-server< -- > Unity-Server.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- master-server -->
## master-server
We use an EzyFox server that acts as a master server. Its tasks are:
User management, processing database requests, starting and managing a Unity server for each room.
The client makes a request to the master server, which forwards the request to the unit server. Once these requests have been processed by the Unity server, it is sent to the master server and from there sent back to the client(s).

<!-- SETUP DATABASE -->
### Setup Database

1. Install mongoDB
2. Open your mongosh
3. Create your Database

``use master-server``

4. Create a new user and password and give it access to the created database

`db.createUser({user: "root", pwd: "123456",roles: [{role: "readWrite", db:"master-server" }] })`

5. Create a new collection:

`db.createCollection("user", { collation: { locale: 'en_US', strength: 2 } } )`
`db.user.createIndex( { username: 1 } )`

6. Use this file for the next step

Location: `master-server/master-server-common/src/main/resources/master-server-common-config.properties`

7. Insert the following values for your database and change it to your needs.

`database.mongo.uri=mongodb://root:123456@127.0.0.1:27017/master-server`

`database.mongo.database=master-server`

`database.mongo.collection.naming.case=UNDERSCORE`

`database.mongo.collection.naming.ignored_suffix=Entity`

In this example file we use:

user: root

password 123456

database: master-server

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- Unity Server -->
## Unity Server
We use a single project for the Unity Servers. The server contains all server scenes that correspond to a room. When it starts, the server receives 3 parameters: username, password and room. The server then uses our Module-GameManager to start the corresponding server scene. This has the advantage that, on the one hand, we only use one project and do not have to create another server project for each server and can therefore share the code that is the same for all servers. The Module GameManager uses a persistent scene where all manager classes/GameObjects are at home. The room scene with the 3D objects such as terrain are additionally loaded.
<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Create the Unity Server
Open the Unity Server project in Unity. Open Build Settings, File -> Build Settings. Be sure to include all server scenes in Scenes In Build, if not add them. Select as Platform Dedicated Server and chose under Target Platform your Platform Windows or Linux and Build the project.
### Setup server path
Open the master-server project in your IDE edit the file: `master-server/master-server-app-api/resources/application.properties` and add the path to your server executables from the step before where you saved it. E.g. D:/Game Builds/Unity-Server/Unity-Server.exe

<!-- Unity Client -->
## Unity Client

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- Contact -->
## Contact
Join us on <a href="https://discord.gg/vjPWk5FSYj">Discord</a>

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- DOCUMENT VARIABLE-->
[contributors-shield]: https://img.shields.io/github/contributors/Assambra/Master-Server-Unity.svg?style=for-the-badge
[contributors-url]: https://github.com/Assambra/Master-Server-Unity/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/Assambra/Master-Server-Unity.svg?style=for-the-badge
[forks-url]: https://github.com/Assambra/Master-Server-Unity/network/members
[stars-shield]: https://img.shields.io/github/stars/Assambra/Master-Server-Unity.svg?style=for-the-badge
[stars-url]: https://github.com/Assambra/Master-Server-Unity/stargazers
[issues-shield]: https://img.shields.io/github/issues/Assambra/Master-Server-Unity.svg?style=for-the-badge
[issues-url]: https://github.com/Assambra/Master-Server-Unity/issues
[license-shield]: https://img.shields.io/github/license/Assambra/Master-Server-Unity.svg?style=for-the-badge
[license-url]: https://github.com/Assambra/Master-Server-Unity/blob/main/LICENSE
[product-image]: Github/Images/Master-Server-Unity.jpg
[Unity-url]: https://www.unity.com
[Unity.com]: https://img.shields.io/badge/Unity-000000.svg?style=for-the-badge&logo=unity&logoColor=white