public class Rank {
    //standings indicate heirachy. The higher the standing the more authority a user has. Try to keep standings to single digits
    String name;
    int standing;
    float averageTime;
    float totalTime;
    int totalLogs;
    int members = 0;
    int averageLogs;
    ArrayList<User> userList;
    private float[] logs;

    public rank(string name, int standing, int members) {
        this.name = name;
        this.standing = standing;
        this.members = members;
    }

    public addMember(User user) {
        userList.add(user);
        members++;
    }

    public removeMember(User user) {
        userList.remove(user);
        members--;
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
        if(totalLogs == 0 || members == 0) {
            throw ArithmeticException("Divide by zero Error");
            return;
        }
        averageTime = totalTime/totalLogs;
        averageLogs = totalLogs/members;
    }

    public float calculateStandardDeviation() {
        if(totalLogs == 0) {
            throw ArithmeticException("There are no logs");
            return null;
        }
        float sum = 0;
        for(int i = 0; i < totalLogs; i++) {
            sum += logs[i] - averageTime;
        }
        sum = Math.sqrt((sum * sum)/totalLogs);
        return sum;
    }
}