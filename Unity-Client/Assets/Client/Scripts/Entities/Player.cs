using UnityEngine;

namespace Assambra.Client
{
    public class Player : Entity
    {
        [SerializeField] private PlayerHeadInfo _playerHeadInfo;

        public string Room { get => _room; }
        public bool IsLocalPlayer { get => _isLocalPlayer; }

        private string _room;
        private bool _isLocalPlayer;
        
        public void Initialize(uint id, string name, GameObject entityGameObject, string room, bool isLocalPlayer)
        {
            base.Initialize(id, name, entityGameObject);
            this._room = room;
            this._isLocalPlayer = isLocalPlayer;
        }

        public void SetPlayerHeadinfoName(string playerName)
        {
            _playerHeadInfo.SetPlayerName(playerName);
        }
    }
}

