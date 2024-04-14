using Assambra.GameFramework.GameManager;
using com.tvd12.ezyfoxserver.client;
using com.tvd12.ezyfoxserver.client.constant;
using com.tvd12.ezyfoxserver.client.entity;
using com.tvd12.ezyfoxserver.client.factory;
using com.tvd12.ezyfoxserver.client.request;
using com.tvd12.ezyfoxserver.client.support;
using com.tvd12.ezyfoxserver.client.unity;
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
            LOGGER.debug("Login username = " + username + ", password = " + password);
            LOGGER.debug("Socket clientName = " + socketProxy.getClient().getName());

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

        #region REQUEST

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

        public void PlayRequest(long characterId)
        {
            EzyObject data = EzyEntityFactory
                .newObjectBuilder()
                .append("characterId", characterId)
                .build();

            appProxy.send(Commands.PLAY, data);
        }

        #endregion

        #region RESPONSE

        private void LoginSuccessResponse(EzySocketProxy proxy, Object data)
        {
            LOGGER.debug("Log in successfully");
        }

        private void UdpHandshakeResponse(EzySocketProxy proxy, Object data)
        {
            LOGGER.debug("HandleUdpHandshake");
            socketProxy.send(new EzyAppAccessRequest(socketConfig.AppName));
        }

        private void AppAccessedResponse(EzyAppProxy proxy, Object data)
        {
            LOGGER.debug("App access successfully");
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
                    EzyObject character = data.get<EzyObject>(i);

                    CharacterModel characterModel = new CharacterModel(
                        character.get<long>("id"),
                        character.get<long>("userId"),
                        character.get<string>("name")
                        );

                    GameManager.Instance.CharacterInfos.Add(characterModel);
                }

                GameManager.Instance.ChangeScene(Scenes.SelectCharacter);
            }
        }

        #endregion
    }
}
