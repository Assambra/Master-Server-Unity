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

        public delegate void EntityInteraction(Entity entity);
        public event EntityInteraction EntityEntered;
        public event EntityInteraction EntityExited;

        private int _id;
        private string _name;
        private List<Entity> _nearbyEntities = new List<Entity>();

        private SphereCollider _triggerCollider;
        private Rigidbody _rigidbody;

        protected virtual void Awake()
        {
            _triggerCollider = GetComponent<SphereCollider>();
            _triggerCollider.isTrigger = true;
            _triggerCollider.radius = ServerConstants.AREA_OF_INTEREST;

            _rigidbody = GetComponent<Rigidbody>();
            _rigidbody.isKinematic = true;

            EntityEntered += OnEntityEntered;
            EntityExited += OnEntityExited;
        }

        protected virtual void OnDestroy()
        {
            EntityEntered -= OnEntityEntered;
            EntityExited -= OnEntityExited;
        }

        private void OnEntityEntered(Entity otherEntity)
        {
            //Debug.Log($"{Name} has detected {otherEntity.Name} entering the area.");
            ServerManager.Instance.ServerLog.ServerLogMessageInfo($"{Name} has detected {otherEntity.Name} entering the area.");
        }

        private void OnEntityExited(Entity otherEntity)
        {
            //Debug.Log($"{Name} has detected {otherEntity.Name} leaving the area.");
            ServerManager.Instance.ServerLog.ServerLogMessageInfo($"{Name} has detected {otherEntity.Name} leaving the area.");
        }

        private void OnTriggerEnter(Collider other)
        {
            Entity otherEntity = other.GetComponent<Entity>();
            if (otherEntity != null && !_nearbyEntities.Contains(otherEntity))
            {
                _nearbyEntities.Add(otherEntity);
                EntityEntered?.Invoke(otherEntity);
            }
        }

        private void OnTriggerExit(Collider other)
        {
            Entity otherEntity = other.GetComponent<Entity>();
            if (otherEntity != null && _nearbyEntities.Contains(otherEntity))
            {
                _nearbyEntities.Remove(otherEntity);
                EntityExited?.Invoke(otherEntity);
            }
        }
    }
}