package choice;

import input.Input;
import input.UserInput;
import mission.Mission;
import mission.Status;

import java.util.ArrayList;
import java.util.List;

import static choice.State.REGULAR;

public class Choice {
    private String name;
    private String activeText = null;
    private String endText = null;
    protected State state = REGULAR;
    protected Choice parentChoice;
    protected Choice linkedChoice;
    protected Mission initiateMission;
    protected Mission completeMission;
    private ArrayList<Choice> listOfChoices = new ArrayList<Choice>();

    public Choice(String name) {
        this.name = name;
    }

    public Choice(String name, String activeText) {
        this(name);
        this.activeText = activeText;
    }

    public String getName() {
        return this.name;
    }

    public void setEndText(String text) {
        if (text.trim().length() == 0 || text == null) {
            this.endText = null;
        } else {
            this.endText = text;
        }

    }

    public void setState(State state) {
        this.state = state;
    }

    public Choice getParentChoice() {return this.parentChoice;}

    public void setInitiateMission(Mission initiateMission) {
        this.initiateMission = initiateMission;
    }

    public void setParentChoice(Choice c) {
        this.parentChoice = c;
    }

    public String getActiveText() {
        return this.activeText;
    }

    public void setCompleteMission(Mission q) {
        this.completeMission = q;
    }


    public void setLinkedChoice(Choice c) {
        this.linkedChoice = c;
    }

    public void addChoice(Choice c) throws Exception {
        if (!this.listOfChoices.contains(c)) {
            c.setParentChoice(this);
            this.listOfChoices.add(c);
        } else throw new Exception("Choice already exists!");
    }

    public void addChoice(List<Choice> list) throws Exception {
        for (Choice c : list) {
            this.addChoice(c);
        }
    }

    public void removeChoice(Choice c) {
        this.listOfChoices.remove(c);
    }

    public ArrayList<Choice> getChoices() {
        return this.listOfChoices;
    }

    /* prints next set of choices */
    public void printChoice() throws Exception {

        if (this.listOfChoices.size() != 0) {
            int i = 0;
            for (Choice c : this.listOfChoices) {
                System.out.println(String.format("(%d) %s", i, c.getName()));
                i++;
            }
            this.printBackOption();

        }
        /* if this choice is not the only child of parent, run parent
            to make other child choices visible
         */
        else if (this.parentChoice.listOfChoices.size() > 1) {
            this.parentChoice.runChoiceSelection();
        }

        /* if this choice is the only child, then run grandparent */
        else {
            this.parentChoice.parentChoice.runChoiceSelection();
        }
    }

    /* prints active text when a choice is selected */
    public void printActiveText() throws InterruptedException {
        if (this.activeText != null) {
            System.out.println(this.activeText);
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
    public void printEndText() {
        if (this.endText != null) {
            System.out.println(this.endText);
        }
    }

    /* prints (B) Go back option at the end of all choices */
    private void printBackOption() {
        System.out.println(String.format("(%s) (Go back)", Input.B.toString()));
    }

    /* activate, complete or fail linked mission */
    public void checkLinkedMission() throws Exception {
        assert (this.initiateMission != null || this.completeMission != null);
        if (this.initiateMission != null) {
            if (this.initiateMission.getStatus() == Status.UNDISCOVERED) {
                this.initiateMission.setStatus(Status.DISCOVERED);
            }
        }

        if (this.completeMission != null) {
            this.completeMission.setStatus(Status.COMPLETED);
        }
    }

    /* Determine user input, either a valid choice, (B), or invalid and promp again */
    public Choice getUserSelection() throws Exception {
        return UserInput.getInstance().getUserSelection(this);
    }

    /* If its a final choice, then runs parent's parent choice
        used by FINAL, FINAL_LINKED States
     */
    public void checkFinalChoice() throws Exception {
        assert (this.listOfChoices.size() == 0);

        if (this.parentChoice.parentChoice != null) {
            this.parentChoice.parentChoice.removeChoice(this.parentChoice);
            this.parentChoice.parentChoice.runChoiceSelection();
        }
    }

    /* activates any linked choice, called first everytime during execution of a choice */
    private void checkLinkedChoice() throws Exception {
        if (this.linkedChoice != null) {
            this.linkedChoice.parentChoice.addChoice(this.linkedChoice);
        }
    }

    /* MAIN FUNCTION
        checks any linked choice to activate or deactivate first,
        then executes choice based on State
     */
    public void runChoiceSelection() throws Exception {
        this.checkLinkedChoice();
        this.state.execute(this);

    }


}
