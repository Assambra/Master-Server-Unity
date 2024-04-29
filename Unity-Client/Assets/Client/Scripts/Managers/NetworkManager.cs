using Assambra.GameFramework.GameManager;
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

namespace Assambra.Client
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

            AddHandler<EzyArray>(Commands.CHARACTER_LIST, CharacterListResponse);
            AddHandler<EzyObject>(Commands.PLAYER_SPAWN, PlayerSpawnRequest);
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

        public new void Disconnect()
        {
            base.Disconnect();
        }

        public void Login(string username, string password)
        {
            Debug.Log("Login username = " + username + ", password = " + password);
            Debug.Log("Socket clientName = " + socketProxy.getClient().getName());

            socketProxy.onLoginSuccess<Object>(LoginSuccessResponse);
            socketProxy.onUdpHandshake<Object>(UdpHandshakeResponse);
            socketProxy.onAppAccessed<Object>(AppAccessedResponse);

            // Login to socket server
            socketProxy.setLoginUsername(username);
            socketProxy.setLoginPassword(password);

            socketProxy.setUrl(socketConfig.TcpUrl);
            socketProxy.setUdpPort(socketConfig.UdpPort);
            socketProxy.setDefaultAppName(socketConfig.AppName);
            socketProxy.setTransportType(EzyTransportType.TCP);

            socketProxy.connect();
        }

        #region SEND

        public void CharacterListRequest()
        {
            appProxy.send(Commands.CHARACTER_LIST);
        }

        public void CreateCharacterRequest(string name)
        {
            EzyObject characterdata = EzyEntityFactory
                .newObjectBuilder()
                .append("name", name)
                .build();

            appProxy.send(Commands.CREATE_CHARACTER, characterdata);
        }

        public void PlayRequest(long id)
        {
            EzyObject data = EzyEntityFactory
                .newObjectBuilder()
                .append("id", id)
                .build();

            appProxy.send(Commands.PLAY, data);
        }

        private void SendClientToServer(string room, string command, List<KeyValuePair<string, object>> additionalParams)
        {
            Debug.Log("SendClientToServer");

            var dataBuilder = EzyEntityFactory.newObjectBuilder()
                .append("room", room)
                .append("command", command);

            foreach (var pair in additionalParams)
            {
                dataBuilder.append(pair.Key, pair.Value);
            }

            EzyObject data = dataBuilder.build();

            appProxy.send(Commands.CLIENT_TO_SERVER, data);
        }

        #endregion

        #region RECEIVE

        private void LoginSuccessResponse(EzySocketProxy proxy, Object data)
        {
            Debug.Log("Log in successfully");
        }

        private void UdpHandshakeResponse(EzySocketProxy proxy, Object data)
        {
            Debug.Log("HandleUdpHandshake");
            socketProxy.send(new EzyAppAccessRequest(socketConfig.AppName));
        }

        private void AppAccessedResponse(EzyAppProxy proxy, Object data)
        {
            Debug.Log("App access successfully");
            CharacterListRequest();
        }

        private void CharacterListResponse(EzyAppProxy proxy, EzyArray data)
        {
            if (data.isEmpty())
                GameManager.Instance.ChangeScene(Scenes.CreateCharacter);
            else
            {
                for (int i = 0; i < data.size(); i++)
                {
                    EzyObject characterInfo = data.get<EzyObject>(i);

                    CharacterInfoModel characterInfoModel = new CharacterInfoModel(
                        characterInfo.get<long>("id"),
                        characterInfo.get<string>("name"),
                        characterInfo.get<string>("room")
                        );

                    GameManager.Instance.CharacterInfos.Add(characterInfoModel);
                }

                GameManager.Instance.ChangeScene(Scenes.SelectCharacter);
            }
        }

        private void PlayerSpawnRequest(EzyAppProxy proxy, EzyObject data)
        {
            Debug.Log("Receive PLAYER_SPAWN request");
            string name = data.get<string>("name");
            bool isLocalPlayer = data.get<bool>("isLocalPlayer");
            string room = data.get<string>("room");
            EzyArray position = data.get<EzyArray>("position");
            EzyArray rotation = data.get<EzyArray>("rotation");
            Vector3 pos = new Vector3(position.get<float>(0), position.get<float>(1), position.get<float>(2));
            Vector3 rot = new Vector3(rotation.get<float>(0), rotation.get<float>(1), rotation.get<float>(2));

            if(!string.IsNullOrEmpty(room))
            {
                Scenes scenes = GameManager.Instance.getScenesByName(room);

                GameManager.Instance.ChangeScene(scenes);
            }
            
            GameObject playerGameObject = GameManager.Instance.CreatePlayer(pos, rot);

            PlayerModel playerModel = new PlayerModel(playerGameObject, name, isLocalPlayer, room, pos, rot);

            GameManager.Instance.PlayerList.Add(playerModel);
        }

        #endregion
    }
}
