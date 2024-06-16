using System.Collections.Generic;
using UnityEngine;

namespace Assambra.Server
{
    [RequireComponent(typeof(SphereCollider))]
    [RequireComponent(typeof(Rigidbody))]

    public abstract class Entity : MonoBehaviour
    {
        public Dictionary<uint, Player> NearbyPlayers { get => _nearbyPlayers; } 
        public uint Id { get => _id;}
        public string Name { get => _name;}
        public GameObject EntityGameObject { get => _entityGameObject; }
        public bool IsStatic { get => _isStatic;}
        public EntityType EntityType { get => _entityType;}

        public Vector3 Position { get => gameObject.transform.position;}
        public Quaternion Rotation { get => gameObject.transform.rotation;}

        private uint _id;
        private string _name;
        private GameObject _entityGameObject;
        private bool _isStatic;
        private EntityType _entityType;

        private Vector3 _position;
        private Vector3 _rotation;

        private delegate void PlayerInteraction(Player player);
        private event PlayerInteraction PlayerEntered;
        private event PlayerInteraction PlayerExited;
        
        private Dictionary<uint, Player> _nearbyPlayers = new Dictionary<uint, Player>();
        
        private SphereCollider _triggerCollider;
        private Rigidbody _rigidbody;

        private Vector3 _lastPosition;
        private Quaternion _lastRotation;

        public virtual void Initialize(uint id, string name, GameObject entityGameObject, bool isStatic, EntityType entityType)
        {
            this._id = id;
            this._name = name;
            this._entityGameObject = entityGameObject;
            this._isStatic = isStatic;
            this._entityType = entityType;
        }

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

            _lastPosition = transform.position;
            _lastRotation = transform.rotation;
        }

        protected virtual void OnDestroy()
        {
            PlayerEntered -= OnPlayerEntered;
            PlayerExited -= OnPlayerExited;
        }

        private void FixedUpdate()
        {
            if (_isStatic)
                return;

            if (_lastPosition != transform.position || _lastRotation != transform.rotation)
            {
                Player player = gameObject.GetComponent<Player>();

                foreach (KeyValuePair<uint, Player> entry in _nearbyPlayers)
                {
                    NetworkManager.Instance.SendUpdateEntityPosition(entry.Value.Username, _id, transform.position, transform.rotation.eulerAngles);
                }
            }

            _lastPosition = transform.position;
            _lastRotation = transform.rotation;
        }

        private void OnPlayerEntered(Player otherPlayer)
        {
            Player player = gameObject.GetComponent<Player>();
            ServerManager.Instance.ServerLog.ServerLogMessageInfo($"{Name} has detected {otherPlayer.Name} entering the area.");
            if (player != null)
            {
                NetworkManager.Instance.SendSpawnToPlayer(player.Username, otherPlayer.Id, otherPlayer.Name, otherPlayer.Position, otherPlayer.Rotation.eulerAngles);
            }
        }

        private void OnPlayerExited(Player otherPlayer)
        {
            ServerManager.Instance.ServerLog.ServerLogMessageInfo($"{Name} has detected {otherPlayer.Name} leaving the area.");

            Player player = gameObject.GetComponent<Player>();

            if (player != null)
            {
                if(!otherPlayer.MasterServerRequestedDespawn)
                {
                    NetworkManager.Instance.SendDespawnToPlayer(player.Username, otherPlayer.Id);
                }
            }
        }

        private void OnTriggerEnter(Collider other)
        {
            Player otherPlayer = other.GetComponent<Player>();
            
            if(otherPlayer != null)
            {
                uint id = otherPlayer.Id;

                if (!_nearbyPlayers.ContainsKey(id))
                {
                    _nearbyPlayers.Add(id, otherPlayer);
                    PlayerEntered?.Invoke(otherPlayer);
                }
            }
        }

        private void OnTriggerExit(Collider other)
        {
            Player otherPlayer = other.GetComponent<Player>();

            if(otherPlayer != null) 
            {
                uint id = otherPlayer.Id;

                if (_nearbyPlayers.ContainsKey(id))
                {
                    _nearbyPlayers.Remove(id);
                    PlayerExited?.Invoke(otherPlayer);
                }
            }
        }
    }
}