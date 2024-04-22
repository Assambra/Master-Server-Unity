namespace Assambra.Client
{
    public class CharacterInfoModel
    {
        public long Id { get => _id; }
        public string Name { get => _name; }
        public string Room { get => _room; }
        
        private long _id;
        private string _name;
        private string _room;

        public CharacterInfoModel (long id, string name, string room)
        {
            this._id = id;
            this._name = name;
            this._room = room;
        }
    }
}

