using UnityEngine;

namespace Assambra.Client
{
    public class NetworkTransform : MonoBehaviour
    {
        public bool IsActive { get => _isActive; set => _isActive = value; }
       

        private bool _isActive;

        private float _updateInterval;
        private float _elapsedTime = 0f;

        private Vector3 _startPosition;
        public Vector3 _targetPosition;

        public void Initialize(Vector3 startPosition)
        {
            _startPosition = startPosition;
            _targetPosition = startPosition;
        }

        private void Awake()
        {
            _updateInterval = Time.fixedDeltaTime;
        }

        private void Update()
        {
            if (_isActive)
            {
                _elapsedTime += Time.deltaTime;

                transform.position = Vector3.Lerp(_startPosition, _targetPosition, _elapsedTime / _updateInterval);

                if (_elapsedTime >= _updateInterval)
                {
                    _elapsedTime = 0f;
                    _startPosition = transform.position;
                }
            }
        }

        public void UpdateTargetPosition(Vector3 newPosition)
        {
            _targetPosition = newPosition;
            _elapsedTime = 0f;
            _startPosition = transform.position;
        }
    }
}