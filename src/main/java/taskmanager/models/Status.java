package taskmanager.models;

public enum Status {
    TODO ("To do"),
    IN_PROGRESS("In progress"),
    COMPLETE("Complete");

    Status(String displayText) {
        this.displayText = displayText;
    }

    private String displayText;

}
