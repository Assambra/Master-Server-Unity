using com.tvd12.ezyfoxserver.client;
using com.tvd12.ezyfoxserver.client.constant;
using com.tvd12.ezyfoxserver.client.entity;
using com.tvd12.ezyfoxserver.client.factory;
using com.tvd12.ezyfoxserver.client.request;
using com.tvd12.ezyfoxserver.client.support;
using com.tvd12.ezyfoxserver.client.unity;
using System.Collections.Generic;
using UnityEngine;
using Object = System.Object;

namespace Assambra.Server
{
    public class NetworkManager : EzyAbstractController
    {
        public static NetworkManager Instance { get; private set; }

        private EzySocketConfig socketConfig;
        
        private void Awake()
        {
            if (Instance != null && Instance != this)
                Destroy(this);
            else
                Instance = this;

            socketConfig = GetSocketConfig();
        }

        private new void OnEnable()
        {
            base.OnEnable();
            AddHandler<EzyObject>(Commands.SERVER_STOP, ServerStopRequest);
            AddHandler<EzyObject>(Commands.PLAYER_SPAWN, PlayerSpawnRequest);
            AddHandler<EzyObject>(Commands.PLAYER_DESPAWN, PlayerDespawnRequest);
            AddHandler<EzyObject>(Commands.PLAYER_INPUT, ReceivePlayerInput);
        }

        private void Update()
        {
            EzyClients.getInstance()
                .getClient(socketConfig.ZoneName)
                .processEvents();
        }

        protected override EzySocketConfig GetSocketConfig()
        {
            return EzySocketConfig.GetBuilder()
                .ZoneName("master-server")
                .AppName("master-server")
                .TcpUrl("127.0.0.1:3005")
                .UdpPort(2611)
                .UdpUsage(true)
                .EnableSSL(false)
                .Build();
        }

        public void Login(string username, string password)
        {
            Debug.Log("Login username = " + username + ", password = " + password);
            Debug.Log("Socket clientName = " + socketProxy.getClient().getName());

            socketProxy.onLoginSuccess<Object>(HandleLoginSuccess);
            socketProxy.onUdpHandshake<Object>(HandleUdpHandshake);
            socketProxy.onAppAccessed<Object>(HandleAppAccessed);

            // Login to socket server
            socketProxy.setLoginUsername(username);
            socketProxy.setLoginPassword(password);

            socketProxy.setUrl(socketConfig.TcpUrl);
            socketProxy.setUdpPort(socketConfig.UdpPort);
            socketProxy.setDefaultAppName(socketConfig.AppName);
            socketProxy.setTransportType(EzyTransportType.TCP);

            socketProxy.connect();
        }

        public new void Disconnect()
        {
            base.Disconnect();
        }

        #region MASTER SERVER REQUESTS

        private void ServerReadyRequest()
        {
            EzyObject data = EzyEntityFactory.newObjectBuilder()
                .append("password", ServerManager.Instance.Password)
                .build();

            appProxy.send(Commands.SERVER_READY, data);
        }

        private void ServerStopRequest(EzyAppProxy proxy, EzyObject data)
        {
            Debug.Log("Receive SERVER_STOP request");

            Disconnect();
            Application.Quit();
        }

        private void PlayerSpawnRequest(EzyAppProxy proxy, EzyObject data)
        {
            Debug.Log("Receive PLAYER_SPAWN request");

            string name = data.get<string>("name");
            string username = data.get<string>("username");
            EzyArray position = data.get<EzyArray>("position");
            EzyArray rotation = data.get<EzyArray>("rotation");
            Vector3 pos = new Vector3(position.get<float>(0), position.get<float>(1), position.get<float>(2));
            Vector3 rot = new Vector3(rotation.get<float>(0), rotation.get<float>(1), rotation.get<float>(2));

            GameObject playerGameObject = ServerManager.Instance.CreatePlayer(pos, rot);

            PlayerModel playerModel = new PlayerModel(playerGameObject, name, username, pos, rot);

            ServerManager.Instance.ServerPlayerList.Add(playerModel);

            Player player = playerGameObject.GetComponent<Player>();
            player.Name = name;
            player.PlayerModel = playerModel;

            // Send PlayerSpawn to client
            bool isLocalPlayer = true;
            SendServerToClient(username, "playerSpawn", new List<KeyValuePair<string, object>>
            {
                new KeyValuePair<string, object>("name", name),
                new KeyValuePair<string, object>("isLocalPlayer", isLocalPlayer),
                new KeyValuePair<string, object>("room", ServerManager.Instance.Room),
                new KeyValuePair<string, object>("position", position),
                new KeyValuePair<string, object>("rotation", rotation),
            });
        }

        private void PlayerDespawnRequest(EzyAppProxy proxy, EzyObject data)
        {
            string username = data.get<string>("username");

            ServerManager.Instance.ServerLog.ServerLogMessageInfo($"Receive command.PLAYER_DESPAWN for {username}");

            PlayerModel playerModel = null;

            foreach (PlayerModel p in ServerManager.Instance.ServerPlayerList)
            {
                if (p.Username == username)
                {
                    p.MasterServerRequestDespawn = true;

                    Player player = p.PlayerGameObject.GetComponent<Player>();

                    SendServerToClients(player.NearbyPlayer, "playerDespawn", new List<KeyValuePair<string, object>>
                    {
                        new KeyValuePair<string, object>("name", p.Name),
                    });

                    playerModel = p;
                    Destroy(p.PlayerGameObject);
                }
            }

            ServerManager.Instance.ServerPlayerList.Remove(playerModel);
        }

        #endregion

        #region MASTER SERVER RESPONSE

        private void HandleLoginSuccess(EzySocketProxy proxy, Object data)
        {
            Debug.Log("Log in successfully");
        }

        private void HandleUdpHandshake(EzySocketProxy proxy, Object data)
        {
            Debug.Log("HandleUdpHandshake");
            socketProxy.send(new EzyAppAccessRequest(socketConfig.AppName));
        }

        private void HandleAppAccessed(EzyAppProxy proxy, Object data)
        {
            Debug.Log("App access successfully");

            Debug.Log("ServerReadyRequest");
            ServerReadyRequest();
        }

        #endregion

        #region SEND TO CLIENT

        public void SendSpawnToPlayer(string username, string name, Vector3 position, Vector3 rotation)
        {
            bool isLocalPlayer = false;
            string room = "";

            EzyArray positionArray = EzyEntityFactory.newArrayBuilder()
                .append(position.x)
                .append(position.y)
                .append(position.z)
                .build();

            EzyArray rotationArray = EzyEntityFactory.newArrayBuilder()
                .append(rotation.x)
                .append(rotation.y)
                .append(rotation.z)
                .build();

            SendServerToClient(username, "playerSpawn", new List<KeyValuePair<string, object>>
            {
                new KeyValuePair<string, object>("name", name),
                new KeyValuePair<string, object>("isLocalPlayer", isLocalPlayer),
                new KeyValuePair<string, object>("room", room),
                new KeyValuePair<string, object>("position", positionArray),
                new KeyValuePair<string, object>("rotation", rotationArray)
            });
        }

        public void SendDespawnToPlayer(string username, string name)
        {
            SendServerToClient(username, "playerDespawn", new List<KeyValuePair<string, object>>
            {
                new KeyValuePair<string, object>("name", name)
            });
        }

        #endregion

        #region RECEIVE FROM CLIENT

        private void ReceivePlayerInput(EzyAppProxy proxy, EzyObject data)
        {
            string username = data.get<string>("username");
            EzyArray inputArray = data.get<EzyArray>("input");

            Vector2 input = new Vector2(inputArray.get<float>(0), inputArray.get<float>(1));

            foreach(PlayerModel player in ServerManager.Instance.ServerPlayerList)
            {
                if(string.Equals(player.Username, username))
                {
                    PlayerController playerController = player.PlayerGameObject.GetComponent<PlayerController>();
                    playerController.Move = new Vector3(input.x, 0, input.y);
                }
            }
        }

        #endregion

        #region ROOM SERVER TO CLIENT MESSAGES

        private void SendServerToClient(string recipient, string command, List<KeyValuePair<string, object>> additionalParams)
        {
            Debug.Log("SendServerToClient");

            var dataBuilder = EzyEntityFactory.newObjectBuilder()
                .append("recipient", recipient)
                .append("command", command);

            foreach (var pair in additionalParams)
            {
                dataBuilder.append(pair.Key, pair.Value);
            }

            EzyObject data = dataBuilder.build();

            appProxy.send(Commands.SERVER_TO_CLIENT, data);
        }

        private void SendServerToClients(List<string> recipients, string command, List<KeyValuePair<string, object>> additionalParams)
        {
            var recipientsArray = EzyEntityFactory.newArrayBuilder();
            foreach (string recipient in recipients)
            {
                recipientsArray.append(recipient);
            }

            var dataBuilder = EzyEntityFactory.newObjectBuilder()
                .append("recipients", recipientsArray.build())
                .append("command", command);

            foreach (var pair in additionalParams)
            {
                dataBuilder.append(pair.Key, pair.Value);
            }

            EzyObject data = dataBuilder.build();

            appProxy.send(Commands.SERVER_TO_CLIENTS, data);
        }

        #endregion
    }
}

