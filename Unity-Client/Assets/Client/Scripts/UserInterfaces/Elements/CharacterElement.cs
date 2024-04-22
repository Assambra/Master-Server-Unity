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

        private CharacterInfoModel _characterInfoModel;

        public void Initialize(CharacterInfoModel characterInfoModel, Action<CharacterInfoModel> onButtonClickAction)
        {
            _characterInfoModel = characterInfoModel;
            _button.onClick.AddListener(() => onButtonClickAction(_characterInfoModel));

            _textName.text = characterInfoModel.Name;
            _textLocation.text = characterInfoModel.Room;
        }
    }
}

