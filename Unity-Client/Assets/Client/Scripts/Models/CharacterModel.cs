namespace Assambra.Client
{
    public class CharacterModel
    {
        public long Id { get => _id; }
        public long UserId { get => _userId; }
        public string Name { get => _name; }

        private long _id;
        private long _userId;
        private string _name;

        public CharacterModel(long id, long userId, string name)
        {
            this._id = id;
            this._userId = userId;
            this._name = name;
        }
    }
}

