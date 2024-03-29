using com.tvd12.ezyfoxserver.client;
using com.tvd12.ezyfoxserver.client.unity;

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
    }
}

