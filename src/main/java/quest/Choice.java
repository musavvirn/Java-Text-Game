package quest;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Choice {
    private String name;
    private String activeText = null;
    private String endText = null;
    protected Choice parentChoice;
    protected Choice otherChoice;
    protected Mission initiateMission;
    protected Mission completeMission;
    private ArrayList<Choice> listOfChoices = new ArrayList<Choice>();
    private boolean isFinalChoice = false;

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
    public void setAsFinalChoice() {
        this.isFinalChoice = true;
    }

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


    public void setOtherChoice(Choice c) {
        this.otherChoice = c;
    }

    public void implementChoice() throws InterruptedException {
        if (this.activeText != null) {
            System.out.println(this.activeText);
        }

        Thread.sleep(500);
        System.out.println(".");
        Thread.sleep(250);
        System.out.println(".");

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

    public void printChoice() {

        if (this.listOfChoices.size() != 0) {
            int i = 0;
            for (Choice c : this.listOfChoices) {
                System.out.println(String.format("(%d) %s", i, c.getName()));
                i++;
            }
        }
        System.out.println(String.format("(B) (Go back)"));
    }

    private void activateOtherChoice() throws Exception {
        this.otherChoice.parentChoice.addChoice(this.otherChoice);
    }

    private void checkAssociatedQuest() throws Exception {
        if (this.initiateMission != null) {
            if (this.initiateMission.getStatus() == Status.UNDISCOVERED) {
                this.initiateMission.setStatus(Status.DISCOVERED);
            }
        }

        if (this.completeMission != null) {
            this.completeMission.setStatus(Status.COMPLETED);
        }
    }


    public void runChoiceSelection() throws Exception {
        this.checkAssociatedQuest();
        this.printEndText();
        this.checkFinalChoice();
        this.printChoice();
        this.getUserSelection().runChoiceSelection();

    }

    private Choice getUserSelection() throws Exception {
        String input = new Scanner(System.in).next();
        int num = 100;

        // Detect "Go Back" option selected with key - "b" or "B"
            if (input.equalsIgnoreCase("B")) {
                this.parentChoice.runChoiceSelection();
            } else {
                try {
                    num = Integer.parseInt(input);
                } catch (Exception e) {
                    System.out.println(String.format("Invalid. Choose one from: "));
                    this.runChoiceSelection();
                }
            }


        Choice c = null;
        try {
            c = this.getChoices().get(num);
            System.out.println(String.format("%d - selected.", num));
            c.implementChoice();
        } catch (Exception e) {
            System.out.println(String.format("Invalid. Choose one from: "));
            this.runChoiceSelection();
        }
        return c;
    }

    private void printEndText() {
        if (this.endText != null) {
            System.out.println(this.endText);
        }
    }

    private void checkFinalChoice() throws Exception {
        if (this.listOfChoices.size() == 0) {
            // Chekcs if this choice ends here, completes a task
            // then delete it from parent choice list
            // if it does not complete a task, then this choice still remains with parent
            if (this.isFinalChoice) {
                if (this.parentChoice.parentChoice != null) {
                    if (this.otherChoice != null) {
                        this.activateOtherChoice();
                    }
                    this.parentChoice.parentChoice.removeChoice(this.parentChoice);

                }

            }

            this.parentChoice.parentChoice.runChoiceSelection();
        }
    }


}
