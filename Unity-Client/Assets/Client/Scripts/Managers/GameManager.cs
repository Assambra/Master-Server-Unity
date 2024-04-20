using Assambra.GameFramework.GameManager;
using System;
using System.Collections.Generic;
using UnityEngine;

namespace Assambra.Client
{
    public class GameManager : BaseGameManager
    {
        public static GameManager Instance;
        public List<PlayerModel> CharacterInfos = new List<PlayerModel>();
        public List<PlayerModel> PlayerList = new List<PlayerModel>();

        [SerializeField] private GameObject _playerPrefab;
        private void Awake()
        {
            if (Instance != null && Instance != this)
                Destroy(this);
            else
                Instance = this;
        }

        protected override void OnSceneChanged(Scene lastScene, Scene newScene)
        {

        }

        public GameObject CreatePlayer(Vector3 position, Vector3 rotation)
        {
            return Instantiate(_playerPrefab, position, Quaternion.Euler(rotation));
        }

        public Scenes getScenesByName(string scenename)
        {
            Scenes scene = (Scenes)Enum.Parse(typeof(Scenes), scenename);

            return scene;
        }
    }
}

