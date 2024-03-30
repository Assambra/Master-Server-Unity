using Assambra.GameFramework.GameManager;

namespace Assambra.Client
{
    public class GameManager : BaseGameManager
    {
        public static GameManager Instance;

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

