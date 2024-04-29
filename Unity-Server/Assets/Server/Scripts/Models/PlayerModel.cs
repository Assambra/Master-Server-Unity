using UnityEngine;

namespace Assambra.Server
{
    public class PlayerModel
    {
        public GameObject PlayerGameObject { get => _playerGameObject; }
        public string Name { get => _name; }
        public string Username { get => _username; }
        public Vector3 Position { get => _position; set => _position = value; }
        public Vector3 Rotation { get => _rotation; set => _rotation = value; }
        public bool MasterServerRequestDespawn { get => _masterServerRequestDespawn; set => _masterServerRequestDespawn = value; }

        private GameObject _playerGameObject;
        private string _name;
        private string _username;
        private Vector3 _position;
        private Vector3 _rotation;
        private bool _masterServerRequestDespawn = false;

        public PlayerModel(GameObject playergameobject, string name, string username, Vector3 position, Vector3 rotation)
        {
            this._playerGameObject = playergameobject;
            this._name = name;
            this._username = username;
            this._position = position;
            this._rotation = rotation;
        }
    }
}
