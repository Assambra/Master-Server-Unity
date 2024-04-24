using TMPro;
using UnityEngine;

namespace Assambra.Server
{
    public class UIServerLog : MonoBehaviour
    {
        [SerializeField] TMP_Text textFieldServerLog;
        [SerializeField] ServerLogFile serverLogFile;


        private void Awake()
        {
            ServerManager.Instance.ServerLog = this;
        }

        void Update()
        {
            textFieldServerLog.text = serverLogFile.File;
        }

        public void ClearLog()
        {
            serverLogFile.File = "";
        }

        public void ServerLogMessageInfo(string message)
        {
            serverLogFile.File += "<color=white>Info: " + message + "</color><br>";
        }

        public void ServerLogMessageSuccess(string message)
        {
            serverLogFile.File += "<color=green>Success: " + message + "</color><br>";
        }

        public void ServerLogMessageError(string message)
        {
            serverLogFile.File += "<color=red>Error: " + message + "</color><br>";
        }
    }
}
