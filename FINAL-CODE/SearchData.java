package application;

public class SearchData {
    private String project;
    private String lifeCycle;
    private String effort;
    private String plan;

    // Constructor
    public SearchData(String project, String lifeCycle, String effort, String plan) {
        this.project = project;
        this.lifeCycle = lifeCycle;
        this.effort = effort;
        this.plan = plan;
    }

    // Getters and setters
    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getLifeCycle() {
        return lifeCycle;
    }

    public void setLifeCycle(String lifeCycle) {
        this.lifeCycle = lifeCycle;
    }

    public String getEffort() {
        return effort;
    }

    public void setEffort(String effort) {
        this.effort = effort;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }
}