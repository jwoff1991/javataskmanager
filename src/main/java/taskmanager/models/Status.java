package taskmanager.models;

public enum Status {
    TODO ("To do"),
    IN_PROGRESS("In progress"),
    COMPLETE("Complete");

    public String getDisplayText() {
        return displayText;
    }

    Status(String displayText) {
        this.displayText = displayText;
    }

    private String displayText;

}
