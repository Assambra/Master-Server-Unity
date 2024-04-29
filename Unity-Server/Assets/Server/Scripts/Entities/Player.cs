namespace Assambra.Server
{
    public class Player : Entity
    {
        public PlayerModel PlayerModel { get => _playerModel; set => _playerModel = value; }

        private PlayerModel _playerModel;


        protected override void Awake()
        {
            base.Awake();
        }

        protected override void OnDestroy()
        {
            base.OnDestroy();
        }
    }
}

