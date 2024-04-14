using System.Collections.Generic;
using UnityEngine;

namespace Assambra.Client
{
    public class UISelectCharacter : MonoBehaviour
    {
        [SerializeField] private GameObject _characterElementPrefab;
        [SerializeField] private Transform _characterElementHome;

        private List<GameObject> _characterElements = new List<GameObject>();

        private void OnEnable()
        {
            CreateCharacterElements();
        }

        public void OnButtonPlay()
        {
            Debug.Log("Button Play clicked");
        }

        public void OnButtonBack()
        {
            Debug.Log("Button Back clicked");
        }

        private void CreateCharacterElements()
        {
            foreach (CharacterModel ci in GameManager.Instance.CharacterInfos)
            {
                GameObject go = GameObject.Instantiate(_characterElementPrefab, _characterElementHome);
                CharacterElement ce = go.GetComponent<CharacterElement>();
                ce.Initialize(ci, HandleCharacterSelection);

                _characterElements.Add(go);
            }
        }

        private void HandleCharacterSelection(CharacterModel character)
        {
            Debug.Log("Selected Character: " + character.Name);
        }
    }
}