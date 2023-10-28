class User {
    //!!A USER CAN ONLY HAVE ONE RANK!!
    public Rank rank;
    public float averageTime;
    public int totalLogs;

    public user(Rank rank) {
        this.rank = rank;
        this.rank.members++;
    }

    public changeRank(Rank rank) {
        if(this.rank != null) {
            this.rank.members--;
        }
        this.rank = rank;
        this.rank.members++;
    }

    

}


class Rank {
    //standings indicate heirachy. The higher the standing the more authority a user has. Try to keep standings to single digits
    String name;
    int standing;
    float averageTime;
    float totalTime;
    int totalLogs;
    int members = 0;
    int averageLogs;
    private float[] logs;

    public rank(string name, int standing, int members) {
        this.name = name;
        this.standing = standing;
        this.members = members;
    }

    public string getName() {
        return name;
    }

    public int getStanding() {
        return standing;
    }

    public float getAverageTime() {
        return averageTime;
    }

    public float getAverageLogs() {
        return averageLogs;
    }

    private addTime(float time) {
        totalTime += time;
        calculateAverage();
    }

    public addLog(Log log) {
        logs[totalLogs] = log.time;
        totalLogs++;
        addTime(log.time);
    }

    public calculateAverage() {
        averageTime = totalTime/totalLogs;
        averageLogs = totalLogs/members;
    }

    public float calculateStandardDeviation() {
        float sum = 0;
        for(int i = 0; i < totalLogs; i++) {
            sum += logs[i] - averageTime;
        }
        sum = Math.sqrt((sum * sum)/totalLogs);
        return sum;
    }
}



public class Rankings {
    private static int multiplier = 1;

    private Rankings() {
        throw IllegalAccessException("Rankings Class is Static");
    }

    static int compareRank(User a, User b) {
        if(a.rank.standing > b.rank.standing) {
            return 1;
        }
        if(a.rank.standing < b.rank.standing) {
            return -1;
        }
        if(a.rank.standing == b.rank.standing) {
            return 0;
        }
    }




    static String checkRank(User user) {
        return user.rank.name;
    }

    //within how many standard deviations do you want the comparison to check?
    static void changeSDMultipler(int mult) {
        multiplier = mult;
    }


    //compares the average time of a users logs against the average time of their rank,
    //returns within what multiple of the standard deviation is it off
    //ex. if the standard deviation is 10 minutes and the users average time is
    //15 minutes, then it will return as 1.5 times the standard deviation
    static float compareAvgTime(User user) {
        Rank userRank = user.rank;
        float standardDeviation = userRank.calculateStandardDeviation() * multiplier;
        if(standardDeviation == 0) {
            throw ArithmeticException("Standard Deviation can not be 0");
            return null;
        }
        float deltaTime = user.averageTime - userRank.averageTime;
        return deltaTime/standardDeviation;
        
    }


    //same as compareRank function except compares an individual log entry instead of a users entire log history
    static float compareLogTime(User user, Log log) {
        Rank userRank = user.rank;
        float standardDeviation = userRank.calculateStandardDeviation() * multiplier;
        if(standardDeviation == 0) {
            throw ArithmeticException("Standard Deviation can not be 0");
            return null;
        }
        float deltaTime = log.time - userRank.averageTime;
        return deltaTime/standardDeviation;
    }


}



