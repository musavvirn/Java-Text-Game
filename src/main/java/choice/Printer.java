package choice;

import input.Input;

/* Handles all printing functions
    SINGLETON
 */

public class Printer {

    public final static Printer printer = new Printer();

    private Printer() { }/* prints next set of choices */

    public static Printer getInstance() {
        return printer;
    }

    public void printChoice(Choice choice) throws Exception {

        if (choice.getListOfChoices().size() != 0) {
            int i = 0;
            int j = 0;
            for (Choice c : choice.getListOfChoices()) {
                if (j == 0) {
                    System.out.println(String.format("\u001b[34m(%d) %s\u001b[0m", i, c.getName()));
                    j++;
                } else {
                    System.out.println(String.format("\u001b[35m(%d) %s\u001b[0m", i, c.getName()));
                    j--;
                }
                i++;
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
            choice.getParentChoice().parentChoice.runChoiceSelection();
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
    void printBackOption() {
        System.out.println(String.format("\u001b[37m(%s) (Go back)\u001b[0m", Input.B.toString()));
    }
}