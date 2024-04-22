using UnityEngine;

namespace Assambra.Client
{
    public class PlayerModel
    {
        public GameObject PlayerGameObject { get => _playerGameObject; }
        public string Name { get => _name; }
        public bool IsLocalPlayer { get => _isLocalPlayer; }
        public string Room { get => _room; set => _room = value; }
        public Vector3 Position { get => _position; set => _position = value; }
        public Vector3 Rotation { get => _rotation; set => _rotation = value; }

        private GameObject _playerGameObject;
        private string _name;
        private bool _isLocalPlayer;
        private string _room;
        private Vector3 _position;
        private Vector3 _rotation;

        public PlayerModel(GameObject playerGameObject, string name, bool isLocalPlayer, string room, Vector3 position, Vector3 rotation)
        {
            this._playerGameObject = playerGameObject;
            this._name = name;
            this._isLocalPlayer = isLocalPlayer;
            this._room = room;
            this._position = position;
            this._rotation = rotation;
        }
    }
}

