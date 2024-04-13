using UnityEngine;

namespace Assambra.Client
{
    public class UISelectChracter : MonoBehaviour
    {
        [SerializeField] private GameObject _prefabCharacterElement;

        public void OnButtonPlay()
        {
            Debug.Log("Button Play clicked");
        }

        public void OnButtonBack()
        {
            Debug.Log("Button Back clicked");
        }
    }
}
