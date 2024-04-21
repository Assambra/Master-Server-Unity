using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace Assambra.Server
{
    public sealed class Commands
    {
        public const String SERVER_READY = "serverReady";
        public const String SERVER_STOP = "serverStop";
        public const String PLAYER_SPAWN = "playerSpawn";
        public const String CLIENT_TO_SERVER = "clientToServer";
        public const String SERVER_TO_CLIENT = "serverToClient";
        public const String SERVER_TO_CLIENTS = "serverToClients";

        //Test
        public const String HELLO_WORLD = "helloWorld";

        private Commands() { }
    }
}

