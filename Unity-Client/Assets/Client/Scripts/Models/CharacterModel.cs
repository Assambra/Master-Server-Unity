namespace Assambra.Client
{
    public class CharacterModel
    {
        public long Id { get => _id; }
        public string Name { get => _name; }
        public bool IsLocalPlayer { get => _isLocalPlayer; set => _isLocalPlayer = value; }
        public string Room { get => _room; set => _room = value; }
        

        private long _id;
        private string _name;
        private bool _isLocalPlayer;
        private string _room;

        public CharacterModel(long id, string name)
        {
            this._id = id;
            this._name = name;
        }
    }
}

