package mission;

import choice.Choice;

public class Mission {
    private String name;
    private Status status = Status.UNDISCOVERED;
    private Choice startingChoice = new Choice("Starting mission..");

    public Mission(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public Status getStatus() {
        return this.status;
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

}
