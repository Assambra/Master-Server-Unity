using UnityEngine;
using TMPro;

namespace Assambra.Client
{
    public class UILogin : MonoBehaviour
    {
        [SerializeField] private TMP_InputField _inputFieldUsername;
        [SerializeField] private TMP_InputField _inputFieldPassword;

        private string _password;
        private string _username;

        public void OnButtonLogin()
        {
            Debug.Log("OnButtonLogin");

            _username = _inputFieldUsername.text;
            _password = _inputFieldPassword.text;

            NetworkManager.Instance.Login(_username, _password);
        }

        public void OnButtonQuit()
        {
            Debug.Log("OnButtonQuit");
            NetworkManager.Instance.Disconnect();

            Helper.Quit();
        }
    }
}
