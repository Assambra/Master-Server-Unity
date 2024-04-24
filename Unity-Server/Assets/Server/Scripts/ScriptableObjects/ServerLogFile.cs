using UnityEngine;

namespace Assambra.Server
{
    [CreateAssetMenu(fileName = "ServerLogFile", menuName = "Assambra/ServerLogFile", order = 1)]
    public class ServerLogFile : ScriptableObject
    {
        public string File;
    }
}

