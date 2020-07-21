package choice;

import input.UserInput;
import inventory.Inventory;
import inventory.Item;
import mission.Mission;
import mission.Status;
import printer.Printer;
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
    protected Choice linkedDeactivateChoice;
    private ArrayList<Choice> listOfChoices = new ArrayList<Choice>();

    protected Mission initiateMission;
    protected Mission completeMission;

    protected Item gainItem;
    protected Item useItem;

    public String getEndText() {
        return endText;
    }

    public void setGainItem(Item i) {
        this.gainItem = i;
    }
    public void setLinkedDeactivateChoice(Choice c) {this.linkedDeactivateChoice = c;}

    public Item getGainItem() {return  this.gainItem;}
    public Item getUseItem() {return this.useItem;}

    public void setUseItem(Item i) {
        this.useItem = i;
    }

    public ArrayList<Choice> getListOfChoices() {
        return listOfChoices;
    }

    public void gainItem() throws Exception {
        if (this.gainItem != null) {
            Inventory.getInstance().add(this.gainItem);
        }
    }

    public void useItem() throws Exception {
        if (this.useItem != null) {
            Inventory.getInstance().remove(this.useItem);
        }
    }

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

    public Mission getCompleteMission() {return this.completeMission;}
    public Mission getInitiateMission() {return this.initiateMission;}


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

        Printer.getInstance().printChoice(this);
    }

    /* prints active text when a choice is selected */
    public void printActiveText() throws InterruptedException {
        Printer.getInstance().printActiveText(this);
    }

    /* print end text for choices that have a text for finishing a taks for example */
    public void printEndText() {
        Printer.getInstance().printEndText(this);
    }

    /* prints (B) Go back option at the end of all choices */
    private void printBackOption() {
        Printer.getInstance().printBackOption();
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
            if (this.parentChoice.listOfChoices.size() > 1) {
                this.parentChoice.removeChoice(this);
                this.parentChoice.runChoiceSelection();
            } else {
                this.parentChoice.parentChoice.removeChoice(this.parentChoice);
                this.parentChoice.parentChoice.runChoiceSelection();

            }

        }
//        if (this.parentChoice.parentChoice != null) {
//
//            this.parentChoice.parentChoice.removeChoice(this.parentChoice);
//            this.parentChoice.parentChoice.runChoiceSelection();
//        }
    }

    /* activates any linked choice, called first everytime during execution of a choice */
    private void checkLinkedChoice() throws Exception {
        this.linkedChoice.parentChoice.addChoice(this.linkedChoice);
    }

    private void checkLinkedDeactivateChoice() {
        this.linkedDeactivateChoice.parentChoice.removeChoice(this.linkedDeactivateChoice);
    }


    /* MAIN FUNCTION
        checks any linked choice to activate or deactivate first,
        then executes choice based on State
     */
    public void runChoiceSelection() throws Exception {
        this.useItem();
        this.gainItem();
        if (this.linkedChoice != null) {
            this.checkLinkedChoice();
        }

        if (this.linkedDeactivateChoice != null) {
            this.checkLinkedDeactivateChoice();
        }

        this.state.execute(this);
    }
}
