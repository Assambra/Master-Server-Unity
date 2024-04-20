using Assambra.GameFramework.GameManager;
using System.Collections.Generic;
using UnityEngine;

namespace Assambra.Client
{
    public class UISelectCharacter : MonoBehaviour
    {
        [SerializeField] private GameObject _characterElementPrefab;
        [SerializeField] private Transform _characterElementHome;

        private List<GameObject> _characterElements = new List<GameObject>();

        private long _id;

        private void OnEnable()
        {
            CreateCharacterElements();
        }

        public void OnButtonPlay()
        {
            if (_id > 0)
                NetworkManager.Instance.PlayRequest(_id);
            else
                Debug.LogError("You need to select a character!");
        }


        public void OnButtonBack()
        {
            GameManager.Instance.ChangeScene(Scenes.CreateCharacter);
        }

        private void CreateCharacterElements()
        {
            foreach (PlayerModel pm in GameManager.Instance.CharacterInfos)
            {
                GameObject go = GameObject.Instantiate(_characterElementPrefab, _characterElementHome);
                CharacterElement ce = go.GetComponent<CharacterElement>();
                ce.Initialize(pm, HandleCharacterSelection);

                _characterElements.Add(go);
            }
        }

        private void HandleCharacterSelection(PlayerModel character)
        {
            _id = character.Id;
        }
    }
}