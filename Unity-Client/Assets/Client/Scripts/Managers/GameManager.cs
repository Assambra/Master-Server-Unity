using Assambra.GameFramework.GameManager;
using System.Collections.Generic;
using UnityEngine;

namespace Assambra.Client
{
    public class GameManager : BaseGameManager
    {
        [SerializeField] private GameObject _playerPrefab;
        
        public static GameManager Instance;
        public List<CharacterInfoModel> CharacterInfos = new List<CharacterInfoModel>();
        public Dictionary<uint, Entity> ClientEntities = new Dictionary<uint, Entity>();

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
    }
}

