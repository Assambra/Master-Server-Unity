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

        //private List<Player> _nearbyPlayers = new List<Player>();
        private List<string> _nearbyPlayers = new List<string>();

        private SphereCollider _triggerCollider;
        private Rigidbody _rigidbody;

        private Vector3 _lastPosition;
        private Quaternion _lastRotation;

        protected virtual void Awake()
        {
            _triggerCollider = GetComponent<SphereCollider>();
            _triggerCollider.isTrigger = true;
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
        }

        private void OnPlayerEntered(Player player)
        {
            //Debug.Log($"{Name} has detected {otherEntity.Name} entering the area.");
            ServerManager.Instance.ServerLog.ServerLogMessageInfo($"{Name} has detected {player.Name} entering the area.");

            if (gameObject.GetComponent<Player>() != null)
            {
                PlayerModel playerModel = gameObject.GetComponent<Player>().PlayerModel;
                
                NetworkManager.Instance.SendSpawnToPlayer(playerModel.Username, player.PlayerModel.Name, player.PlayerModel.Position, player.PlayerModel.Rotation);
            }
        }

        private void OnPlayerExited(Player player)
        {
            //Debug.Log($"{Name} has detected {otherEntity.Name} leaving the area.");
            ServerManager.Instance.ServerLog.ServerLogMessageInfo($"{Name} has detected {player.Name} leaving the area.");
            
            if (gameObject.GetComponent<Player>() != null)
            {
                PlayerModel playerModel = gameObject.GetComponent<Player>().PlayerModel;
                if(!player.PlayerModel.MasterServerRequestDespawn)
                {
                    NetworkManager.Instance.SendDespawnToPlayer(playerModel.Username, player.PlayerModel.Name);
                }
            }
        }

        private void OnTriggerEnter(Collider other)
        {
            Player player = other.GetComponent<Player>();
            string username = player.PlayerModel.Username;

            if (player != null && !_nearbyPlayers.Contains(username))
            {
                //_nearbyPlayers.Add(player);
                _nearbyPlayers.Add(username);
                PlayerEntered?.Invoke(player);
            }
        }

        private void OnTriggerExit(Collider other)
        {
            Player player = other.GetComponent<Player>();
            string username = player.PlayerModel.Username;
            if (player != null && _nearbyPlayers.Contains(username))
            {
                //_nearbyPlayers.Remove(player);
                _nearbyPlayers.Remove(username);
                PlayerExited?.Invoke(player);
            }
        }
    }
}