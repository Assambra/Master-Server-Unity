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
![Master Server Diagramm][product-image]

<a name="readme-top"></a>

<!-- TABLE OF CONTENTS -->
## Table of Contents
<ul>
    <li><a href="#foreword">Foreword</a></li>
    <li><a href="#key-features">Key Features</a></li>
    <li><a href="#server">Server</a></li>
    <ul>
        <li><a href="#setup-database">Setup database</a></li>
    </ul>
    <li><a href="#contact">Contact</a></li>
</ul>

<!-- FOREWORD-->
## Foreword
Some Forword text

For the game client we are using [Unity](https://unity.com "Unity") as game engine.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- KEY FEATURES -->
## Key Features
<ul>
<li></li>
</ul>

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