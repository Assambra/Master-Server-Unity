using UnityEngine;

namespace Assambra.Server
{
    [RequireComponent(typeof(CharacterController))]
    public class PlayerController : MonoBehaviour
    {
        public Vector3 Move { set => _move = value; }

        private Vector3 _move;

        private CharacterController _characterController;
        private Vector3 _playerVelocity;
        private bool _groundedPlayer;
        private float _playerSpeed = 2.0f;
        private float _rotationSpeed = 150f;
        private float _jumpHeight = 1.0f;
        private float _gravityValue = -9.81f;

        private void Start()
        {
            _characterController = gameObject.GetComponent<CharacterController>();
            _characterController.center = new Vector3(0, 1, 0);
        }

        void Update()
        {
            _groundedPlayer = _characterController.isGrounded;
            if (_groundedPlayer && _playerVelocity.y < 0)
            {
                _playerVelocity.y = 0f;
            }

            _characterController.Move(transform.forward * _move.z * Time.deltaTime * _playerSpeed);
            transform.Rotate(new Vector3(0, _move.x * _rotationSpeed * Time.deltaTime, 0));

            if (Input.GetButtonDown("Jump") && _groundedPlayer)
            {
                _playerVelocity.y += Mathf.Sqrt(_jumpHeight * -3.0f * _gravityValue);
            }

            _playerVelocity.y += _gravityValue * Time.deltaTime;
            _characterController.Move(_playerVelocity * Time.deltaTime);
        }
    }
}

