using UnityEngine;

namespace Assambra.Client
{
    public class PlayerModel
    {
        public long Id { get => _id; }
        public string Name { get => _name; }
        public bool IsLocalPlayer { get => _isLocalPlayer; }
        public string Room { get => _room; set => _room = value; }
        public GameObject PlayerGameObject { get => _playerGameObject; }
        public Vector3 Position { get => _position; set => _position = value; }
        public Vector3 Rotation { get => _rotation; set => _rotation = value; }

        private long _id;
        private string _name;
        private bool _isLocalPlayer;
        private string _room;
        private GameObject _playerGameObject;
        private Vector3 _position;
        private Vector3 _rotation;

        public PlayerModel(long id, string name, bool isLocalPlayer, string room, GameObject playerGameObject, Vector3 position, Vector3 rotation)
        {
            this._id = id;
            this._name = name;
            this._isLocalPlayer = isLocalPlayer;
            this._room = room;
            this._playerGameObject = playerGameObject;
            this._position = position;
            this._rotation = rotation;
        }
    }
}

