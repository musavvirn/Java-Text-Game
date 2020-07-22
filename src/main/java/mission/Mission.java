package mission;

import choice.Choice;

import java.util.ArrayList;

public class Mission {
    private String name;
    private ArrayList<String> updates = new ArrayList<>();
    private int updatePos = 0;
    private Status status = Status.UNDISCOVERED;
    private Choice startingChoice = new Choice("Starting mission..");

    public Mission(String name) {
        this.name = name;
        this.updates.add(this.name);
    }

    public String getName() {
        return this.name;
    }

    public Status getStatus() {
        return this.status;
    }

    public void addUpdate(String text) {
        this.updates.add(text);
    }


    public void update() {
        this.updatePos++;
        this.name = updates.get(this.updatePos);
        //this.printName();
    }

    public void setStatus(Status status) throws Exception {
        this.status = status;
        switch (status) {
            case DISCOVERED:
                MissionManager.getInstance().addQuest(this);
                break;
            case COMPLETED:
                MissionManager.getInstance().updateQuest(this, Status.COMPLETED);
                break;
            case FAILED:
                MissionManager.getInstance().updateQuest(this, Status.FAILED);
                break;


                default:
                    throw new Exception("Invalid initiateMission status update");
        }
    }

    public Choice getStartingChoice() {
        return this.startingChoice;
    }

    public void activate() throws Exception {
        String s = String.format("<< %s has started. >>", this.name);
        System.out.println(s);
        System.out.println();
        /* only used for the Main objective in the start of the game */
        this.startingChoice.runChoiceSelection();

    }

    private void printName() {

    }

}
