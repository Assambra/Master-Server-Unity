using UnityEngine;

namespace Assambra.Client
{
    [RequireComponent(typeof(CharacterController))]
    public class PlayerController : MonoBehaviour
    {
        public PlayerModel PlayerModel;

        private CharacterController _characterController;
        private Vector3 playerVelocity;
        private bool groundedPlayer;
        private float playerSpeed = 2.0f;
        private float jumpHeight = 1.0f;
        private float gravityValue = -9.81f;

        private void Start()
        {
            _characterController = gameObject.GetComponent<CharacterController>();
            _characterController.center = new Vector3(0, 1, 0);
        }

        void Update()
        {
            if (!PlayerModel.IsLocalPlayer)
                return;

            groundedPlayer = _characterController.isGrounded;
            if (groundedPlayer && playerVelocity.y < 0)
            {
                playerVelocity.y = 0f;
            }

            Vector3 move = new Vector3(Input.GetAxis("Horizontal"), 0, Input.GetAxis("Vertical"));
            _characterController.Move(move * Time.deltaTime * playerSpeed);

            if (move != Vector3.zero)
            {
                gameObject.transform.forward = move;
            }

            // Changes the height position of the player..
            if (Input.GetButtonDown("Jump") && groundedPlayer)
            {
                playerVelocity.y += Mathf.Sqrt(jumpHeight * -3.0f * gravityValue);
            }

            playerVelocity.y += gravityValue * Time.deltaTime;
            _characterController.Move(playerVelocity * Time.deltaTime);
        }
    }
}

