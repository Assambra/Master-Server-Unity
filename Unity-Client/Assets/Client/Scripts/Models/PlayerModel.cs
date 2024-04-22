using UnityEngine;

namespace Assambra.Client
{
    public class PlayerModel
    {
        public GameObject PlayerGameObject { get => _playerGameObject; set => _playerGameObject = value; }
        public long Id { get => _id; }
        public string Name { get => _name; }
        public bool IsLocalPlayer { get => _isLocalPlayer; set => _isLocalPlayer = value; }
        public string RoomName { get => _roomName; set => _roomName = value; }
        public Vector3 Position { get => _position; set => _position = value; }
        public Vector3 Rotation { get => _rotation; set => _rotation = value; }

        private GameObject _playerGameObject;
        private long _id;
        private string _name;
        private bool _isLocalPlayer;
        private string _roomName;
        private Vector3 _position;
        private Vector3 _rotation;

        public PlayerModel(long id, string name)
        {
            this._id = id;
            this._name = name;
        }
    }
}

