using TMPro;
using UnityEngine;

namespace Assambra.Client
{
    public class UICreateCharacter : MonoBehaviour
    {
        [SerializeField] private TMP_InputField _inputFieldName;

        public void OnButtonCreate()
        {
            Debug.Log("Button Create clicked");
            NetworkManager.Instance.CreateCharacterRequest(_inputFieldName.text);
        }

        public void OnButtonBack()
        {
            Debug.Log("Button Back clicked");
        }
    }
}
