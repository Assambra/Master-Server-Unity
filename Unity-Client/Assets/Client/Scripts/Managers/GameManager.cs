using Assambra.GameFramework.GameManager;
using System.Collections.Generic;

namespace Assambra.Client
{
    public class GameManager : BaseGameManager
    {
        public static GameManager Instance;
        public List<CharacterModel> CharacterInfos = new List<CharacterModel>();

        private void Awake()
        {
            if (Instance != null && Instance != this)
                Destroy(this);
            else
                Instance = this;
        }

        protected override void OnSceneChanged(Scene lastScene, Scene newScene)
        {

        }
    }
}

