public class Rankings {
    private static int multiplier = 1;

    private Rankings() {
        throw IllegalAccessException("Rankings Class is Static");
    }



    static int compareRank(User a, User b) {
        //return 1 if a > b, -1 if b > a, 0 if a = b
        if(a.rank.standing > b.rank.standing) {
            return 1;
        }
        if(a.rank.standing < b.rank.standing) {
            return -1;
        }
        if(a.rank.standing == b.rank.standing) {
            return 0;
        }
        return null;
    }





    static String getRankName(User user) {
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