using UnityEngine;

namespace Assambra.Server
{
    public class Player : Entity
    {
        [SerializeField] private PlayerHeadInfo _playerHeadInfo;

        public string Username { get => _username; }
        public bool MasterServerRequestedDespawn { get => _masterServerRequestedDespawn; set => _masterServerRequestedDespawn = value; }

        private string _username;
        private bool _masterServerRequestedDespawn;

        public void Initialize(uint id, string name, GameObject entityGameObject, bool isStatic, EntityType entityType, string username)
        {
            base.Initialize(id, name, entityGameObject, isStatic, entityType);
            this._username = username;
        }

        protected override void Awake()
        {
            base.Awake();
        }

        protected override void OnDestroy()
        {
            base.OnDestroy();
        }

        public void SetPlayerHeadinfoName(string playerName)
        {
            _playerHeadInfo.SetPlayerName(playerName);
        }
    }
}

