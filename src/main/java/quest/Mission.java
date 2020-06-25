package quest;

public class Mission {
    private String title;
    private Status status = Status.UNDISCOVERED;
    private Choice startingChoice = new Choice("Starting mission..");

    public Mission(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
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


    public void start() throws Exception {
        String s = String.format("<< %s has started. >>", this.title);
        System.out.println(s);
        System.out.println();
        this.startingChoice.runChoiceSelection();

    }

    public void complete() {
        // check conditions to finish initiateMission
    }






}
