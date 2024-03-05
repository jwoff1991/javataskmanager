package taskmanager.ui;

import org.springframework.stereotype.Component;
import taskmanager.models.Status;
import taskmanager.models.Task;

import java.io.Console;
import java.util.List;
import java.util.Scanner;
@Component
public class View {
    Scanner console = new Scanner(System.in);

    public int getMenuOption() {
        displayHeader("Welcome to the main menu");
        displayText("1. Add a task");
        displayText("2. View all tasks");
        displayText("3. Update a task");
        displayText("4. Delete a task");
        displayText("5. Exit the program");
        return readInt("What would you like to do? [1-5]", 1, 5);
    }

    public Task makeTask() {
        Task result = new Task();
        result.setCreatedOn(readString("Enter the date: "));
        result.setTitle(readString("Enter the title: "));
        result.setDescription(readString("Enter the description: "));
        result.setDueDate(readString("Enter due date: "));
        result.setStatus(readStatus("Enter the status: "));
        return result;

    }

    public void displayTasks(List<Task> tasks) {
        for(Task task : tasks) {
            displayText(String.format("id: %s, Date: %s, Title: %s, Description: %s, Due date: %s, Status: %s%n",
                    task.getId(),
                    task.getCreatedOn(),
                    task.getTitle(),
                    task.getDescription(),
                    task.getDueDate(),
                    task.getStatus()));
        }
    }

    public int updateById() {
        displayText("Which is would you like to update?");
        int id = readInt("Enter the id: ", 1, 9999999);
        return id;
    }


    public void displayHeader(String header) {
        System.out.println();
        System.out.println(header);
        System.out.println("*".repeat(header.length()));
    }

    public void displayText(String line) {
        System.out.println();
        System.out.println(line);
    }

    public void displayErrors(List<String> errors) {
        displayHeader("Errors: " );
        for(String error : errors) {
            displayText(error);
        }
        displayText("");
    }

    public String readString(String prompt) {
        displayText(prompt);
        String string = console.nextLine();

        if(string == null || string.isBlank()) {
            displayText("Must enter a value!");
            string = readString(prompt);
        }
        return string;
    }

    public int readInt(String prompt, int min, int max) {
        while(true) {
            String value = readString(prompt);
            try {
                int intValue = Integer.parseInt(value);
                if(intValue < min || intValue > max) {
                    System.out.printf("That is not a value choice, please choose a value between %s - %s%n", min, max);
                }else {
                    return intValue;
                }
            } catch (NumberFormatException ex) {
                System.out.printf("%s is not a valid number." , value);
            }
        }
    }

    public Status readStatus(String prompt) {
        displayHeader("Task Status: ");
        for(Status status : Status.values()) {
            displayText(status.getDisplayText());
        }
        while(true) {
            String selection = readString(prompt);
            selection = selection.toUpperCase();
            try{
                return Status.valueOf(selection);
            }catch(IllegalArgumentException ex){
                System.out.printf("%s is not a valid status", selection);
            }
        }
    }

}
