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

            if (args.Length >= 3)
            {
                _username = args[1];
                _password = args[2];
                _room = args[3];
            }
            else
            {
                Debug.LogError("Insufficient command line arguments. Expected format: [executable] username password room");
            }
        }

        private void Start()
        {
            switch (_room)
            {
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


