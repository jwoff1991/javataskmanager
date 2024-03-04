package taskmanager.ui;

import taskmanager.models.Status;

import java.io.Console;
import java.util.List;
import java.util.Scanner;

public class View {
    Scanner console = new Scanner(System.in);

    public int getMenuOption() {

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
