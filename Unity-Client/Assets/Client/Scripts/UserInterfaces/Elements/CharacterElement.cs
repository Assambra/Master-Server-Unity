using System;
using TMPro;
using UnityEngine;
using UnityEngine.UI;

namespace Assambra.Client
{
    public class CharacterElement : MonoBehaviour
    {
        [SerializeField] private Button _button;
        [SerializeField] private TMP_Text _textName;
        [SerializeField] private TMP_Text _textLocation;

        private CharacterModel _characterModel;

        public void Initialize(CharacterModel characterModel, Action<CharacterModel> onButtonClickAction)
        {
            _characterModel = characterModel;
            _button.onClick.AddListener(() => onButtonClickAction(_characterModel));

            _textName.text = characterModel.Name;
            _textLocation.text = "";
        }
    }
}

