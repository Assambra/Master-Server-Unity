using UnityEngine;

namespace Assambra.Server
{
    [RequireComponent(typeof(CharacterController))]
    public class PlayerController : MonoBehaviour
    {
        public Vector3 Move;

        private CharacterController _characterController;
        private Vector3 _playerVelocity;
        private bool _groundedPlayer;
        private float _playerSpeed = 2.0f;
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

            _characterController.Move(Move * Time.deltaTime * _playerSpeed);

            if (Move != Vector3.zero)
            {
                gameObject.transform.forward = Move;
            }

            // Changes the height position of the player..
            if (Input.GetButtonDown("Jump") && _groundedPlayer)
            {
                _playerVelocity.y += Mathf.Sqrt(_jumpHeight * -3.0f * _gravityValue);
            }

            _playerVelocity.y += _gravityValue * Time.deltaTime;
            _characterController.Move(_playerVelocity * Time.deltaTime);
        }
    }
}

