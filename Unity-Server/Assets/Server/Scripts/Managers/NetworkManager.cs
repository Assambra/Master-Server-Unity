using com.tvd12.ezyfoxserver.client;
using com.tvd12.ezyfoxserver.client.constant;
using com.tvd12.ezyfoxserver.client.request;
using com.tvd12.ezyfoxserver.client.support;
using com.tvd12.ezyfoxserver.client.unity;
using System;

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
            LOGGER.debug("Login username = " + username + ", password = " + password);
            LOGGER.debug("Socket clientName = " + socketProxy.getClient().getName());

            socketProxy.onLoginSuccess<Object>(HandleLoginSuccess);
            socketProxy.onUdpHandshake<Object>(HandleUdpHandshake);
            socketProxy.onAppAccessed<Object>(HandleAppAccessed);

            // Login to socket server
            socketProxy.setLoginUsername(username);
            socketProxy.setLoginPassword(password);

            socketProxy.setUrl(socketConfig.TcpUrl);
            socketProxy.setUdpPort(socketConfig.UdpPort);
            socketProxy.setDefaultAppName(socketConfig.AppName);
            socketProxy.setTransportType(EzyTransportType.UDP);


            socketProxy.connect();
        }

        public new void Disconnect()
        {
            base.Disconnect();
        }

        private void HandleLoginSuccess(EzySocketProxy proxy, Object data)
        {
            LOGGER.debug("Log in successfully");
        }

        private void HandleUdpHandshake(EzySocketProxy proxy, Object data)
        {
            LOGGER.debug("HandleUdpHandshake");
            socketProxy.send(new EzyAppAccessRequest(socketConfig.AppName));
        }


        private void HandleAppAccessed(EzyAppProxy proxy, Object data)
        {
            LOGGER.debug("App access successfully");
        }
    }
}

