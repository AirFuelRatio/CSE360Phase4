public class User {
    //!!A USER CAN ONLY HAVE ONE RANK!!
    public Rank rank;
    public float averageTime;
    public int totalLogs;

    public user(Rank rank) {}

    public changeRank(Rank rank) {
        if(this.rank != null) {
            this.rank.members--;
        }
        this.rank = rank;
        this.rank.members++;
    }

    

}