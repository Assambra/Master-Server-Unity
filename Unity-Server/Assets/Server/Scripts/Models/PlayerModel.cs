using UnityEngine;

namespace Assambra.Server
{
    public class PlayerModel
    {
        public string Username { get => _username; }
        public Vector3 Position { get => _position; set => _position = value; }
        public Vector3 Rotation { get => _rotation; set => _rotation = value; }

        private string _username;
        private Vector3 _position;
        private Vector3 _rotation;

        public PlayerModel(string username, Vector3 position, Vector3 rotation)
        {
            this._username = username;
            this._position = position;
            this._rotation = rotation;
        }
    }
}
