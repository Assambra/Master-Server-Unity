using System.Collections.Generic;
using UnityEngine;

namespace Assambra.Server
{
    [RequireComponent(typeof(SphereCollider))]
    [RequireComponent(typeof(Rigidbody))]

    public abstract class Entity : MonoBehaviour
    {
        public int Id { get => _id; set => _id = value; }
        public string Name { get => _name; set => _name = value; }
        public List<string> NearbyPlayer { get => _nearbyPlayers; }

        public delegate void PlayerInteraction(Player player);
        public event PlayerInteraction PlayerEntered;
        public event PlayerInteraction PlayerExited;

        private int _id;
        private string _name;
        private List<string> _nearbyPlayers = new List<string>();

        private SphereCollider _triggerCollider;
        private Rigidbody _rigidbody;

        private Vector3 _lastPosition;
        private Quaternion _lastRotation;

        protected virtual void Awake()
        {
            _triggerCollider = GetComponent<SphereCollider>();
            _triggerCollider.isTrigger = true;
            _triggerCollider.center = new Vector3(0, 1, 0);
            _triggerCollider.radius = ServerConstants.AREA_OF_INTEREST;

            _rigidbody = GetComponent<Rigidbody>();
            _rigidbody.isKinematic = true;

            PlayerEntered += OnPlayerEntered;
            PlayerExited += OnPlayerExited;
        }

        protected virtual void OnDestroy()
        {
            PlayerEntered -= OnPlayerEntered;
            PlayerExited -= OnPlayerExited;
        }

        private void FixedUpdate()
        {
            if(_lastPosition != transform.position  || _lastRotation != transform.rotation)
            {
                // Send position and rotation to all nearbyPlayers
            }

            _lastPosition = transform.position;
            _lastRotation = transform.rotation;
        }

        private void OnPlayerEntered(Player otherPlayer)
        {
            ServerManager.Instance.ServerLog.ServerLogMessageInfo($"{Name} has detected {otherPlayer.Name} entering the area.");

            Player player = gameObject.GetComponent<Player>();

            if (player != null)
            {
                NetworkManager.Instance.SendSpawnToPlayer(player.PlayerModel.Username, otherPlayer.PlayerModel.Name, otherPlayer.PlayerModel.Position, otherPlayer.PlayerModel.Rotation);
            }
        }

        private void OnPlayerExited(Player otherPlayer)
        {
            ServerManager.Instance.ServerLog.ServerLogMessageInfo($"{Name} has detected {otherPlayer.Name} leaving the area.");

            Player player = gameObject.GetComponent<Player>();

            if (player != null)
            {
                if(!otherPlayer.PlayerModel.MasterServerRequestDespawn)
                {
                    NetworkManager.Instance.SendDespawnToPlayer(player.PlayerModel.Username, otherPlayer.PlayerModel.Name);
                }
            }
        }

        private void OnTriggerEnter(Collider other)
        {
            Player otherPlayer = other.GetComponent<Player>();

            if(otherPlayer != null)
            {
                string username = otherPlayer.PlayerModel.Username;

                if (!_nearbyPlayers.Contains(username))
                {
                    _nearbyPlayers.Add(username);
                    PlayerEntered?.Invoke(otherPlayer);
                }
            }
        }

        private void OnTriggerExit(Collider other)
        {
            Player otherPlayer = other.GetComponent<Player>();

            if(otherPlayer != null) 
            {
                string username = otherPlayer.PlayerModel.Username;

                if (_nearbyPlayers.Contains(username))
                {
                    _nearbyPlayers.Remove(username);
                    PlayerExited?.Invoke(otherPlayer);
                }
            }
        }
    }
}