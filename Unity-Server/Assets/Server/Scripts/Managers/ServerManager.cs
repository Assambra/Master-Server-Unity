using Assambra.GameFramework.GameManager;
using System;
using UnityEngine;

namespace Assambra.Server
{
    public class ServerManager : MonoBehaviour
    {
        public static ServerManager Instance { get; private set; }
        
        private string _username;
        private string _password;
        private string _room;

        private bool doOnce;

        private void Awake()
        {
            if (Instance != null && Instance != this)
                Destroy(this);
            else
                Instance = this;

            string[] args = Environment.GetCommandLineArgs();

            for (int i = 1; i < args.Length; i++)
            {
                if (args[i] == "--username" && i + 1 < args.Length)
                {
                    _username = args[i + 1];
                }
                else if (args[i] == "--password" && i + 1 < args.Length)
                {
                    _password = args[i + 1];
                }
                else if (args[i] == "--room" && i + 1 < args.Length)
                {
                    _room = args[i + 1];
                }
            }
        }

        private void Start()
        {
            switch (_room)
            {
                case "Newcomer":
                    GameManager.Instance.ChangeScene(Scenes.Newcomer);
                    Debug.Log("Change server scene to Newcomer");
                    break;
                case "World":
                    GameManager.Instance.ChangeScene(Scenes.World);
                    Debug.Log("Change server scene to World");
                    break;
                case "BossRoom":
                    GameManager.Instance.ChangeScene(Scenes.BossRoom);
                    Debug.Log("Change server scene to World");
                    break;
                default:
                    Debug.LogError("Receive unhandled room args");
                    break;
            }
        }

        private void Update()
        {
            if(!doOnce && !String.IsNullOrEmpty(_room) && !String.IsNullOrEmpty(_username) && !String.IsNullOrEmpty(_password))
            {
                doOnce = true;
                NetworkManager.Instance.Login(_username, _password);
            }
        }
    }
}


