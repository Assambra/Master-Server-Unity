using UnityEngine;

namespace Assambra.Server
{
    public class PlayerModel
    {
        public long Id { get => _id; }
        public string Name { get => _name; }
        public string Username { get => _username; }
        public GameObject PlayerGameObject { get => _playerGameObject; }
        public Vector3 Position { get => _position; set => _position = value; }
        public Vector3 Rotation { get => _rotation; set => _rotation = value; }
        public bool MasterServerRequestDespawn { get => _masterServerRequestDespawn; set => _masterServerRequestDespawn = value; }

        private long _id;
        private string _name;
        private string _username;
        private GameObject _playerGameObject;
        private Vector3 _position;
        private Vector3 _rotation;
        private bool _masterServerRequestDespawn = false;

        public PlayerModel(long id, string name, string username, GameObject playergameobject, Vector3 position, Vector3 rotation)
        {
            this._id = id;
            this._name = name;
            this._username = username;
            this._playerGameObject = playergameobject;
            this._position = position;
            this._rotation = rotation;
        }
    }
}
