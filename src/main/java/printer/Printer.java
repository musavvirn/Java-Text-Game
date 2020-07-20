package printer;

import choice.Choice;
import input.Input;
import mission.Status;

/* Handles all printing functions
    SINGLETON
 */

public class Printer {

    public final static Printer instance = new Printer();
    private String COLOR_MAGENTA = "\u001b[34m";
    private String COLOR_BLUE = "\u001b[35m";
    private String COLOR_BACK = "\u001b[37m";
    private String COLOR_END = "\u001b[0m";

    private Printer() { }/* prints next set of choices */

    public static Printer getInstance() {
        return instance;
    }

    public void printChoice(Choice choice) throws Exception {

        if (choice.getListOfChoices().size() != 0) {
            int i = 0;
            int j = 0;
            for (Choice c : choice.getListOfChoices()) {
                if (c.getCompleteMission() == null || c.getCompleteMission().getStatus() != Status.UNDISCOVERED) {
                    if (j == 0) {
                        System.out.println(String.format(COLOR_MAGENTA + "(%d) %s" + COLOR_END, i, c.getName()));
                        j++;
                    } else {
                        System.out.println(String.format(COLOR_BLUE + "(%d) %s" + COLOR_END, i, c.getName()));
                        j--;
                    }
                    i++;

                }

            }

            this.printBackOption();
        }
        /* if this choice is not the only child of parent, run parent
            to make other child choices visible
         */
        else if (choice.getParentChoice().getListOfChoices().size() > 1) {
            choice.getParentChoice().runChoiceSelection();
        }

        /* if this choice is the only child, then run grandparent */
        else {
            choice.getParentChoice().getParentChoice().runChoiceSelection();
        }
    }

    /* prints active text when a choice is selected */
    public void printActiveText(Choice choice) throws InterruptedException {
        if (choice.getActiveText() != null) {
            System.out.println(choice.getActiveText());
            Thread.sleep(500);
            System.out.println(".");
            Thread.sleep(250);
            System.out.println(".");
        } else {
            Thread.sleep(250);
            System.out.println(".");
        }
    }

    /* print end text for choices that have a text for finishing a taks for example */
    public void printEndText(Choice choice) {
        if (choice.getEndText() != null) {
            System.out.println(choice.getEndText());
        }
    }

    /* prints (B) Go back option at the end of all choices */
    public void printBackOption() {
        System.out.println(String.format(COLOR_BACK + "(%s) (Go back)" + COLOR_END, Input.B.toString()));
    }
}