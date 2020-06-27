package input;

import choice.Choice;
import inventory.Inventory;
import mission.MissionManager;

import java.util.Scanner;

/* Obtains user input
    SINGLETON
 */

public class UserInput {
    private static UserInput instance = new UserInput();

    public static UserInput getInstance() {
        return instance;
    }

    private UserInput() {}

    /* Determine user input, either a valid choice, (B), or invalid and promp again */

    public Choice getUserSelection(Choice choice) throws Exception {
        String input = new Scanner(System.in).next();
        int num = 100;

        // Detect "Go Back" option selected with key - "b" or "B"
        if (input.equalsIgnoreCase(Input.B.toString())) {
            choice.getParentChoice().runChoiceSelection();

        } else if (input.equalsIgnoreCase(Input.M.toString())) {
            MissionManager.getInstance().printMissions();
            // transition from Mission Log to Choices again

        } else if (input.equalsIgnoreCase(Input.I.toString())) {
            Inventory.getInstance().printInventory();
        }


        else {
            try {
                num = Integer.parseInt(input);
            } catch (Exception e) {
                System.out.println(String.format("Invalid. Choose one from: "));
                choice.runChoiceSelection();
            }
        }

        Choice c = null;
        try {
            c = choice.getChoices().get(num);
            System.out.println(String.format("%d - selected.", num));
            c.printActiveText();
        } catch (Exception e) {
            System.out.println(String.format("Invalid. Choose one from: "));
            choice.runChoiceSelection();
        }
        return c;
    }
}