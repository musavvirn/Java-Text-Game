package quest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MissionManager {
    private static MissionManager ourInstance = new MissionManager();
    private static ArrayList<Mission> listOfActiveMission = new ArrayList<>();
    private static Map<Mission, Status> mapOfQuest = new HashMap<>();

    public static MissionManager getInstance() {
        return ourInstance;
    }

    private MissionManager() {
    }

    public void addQuest(Mission mission) throws Exception {
        if (!mapOfQuest.containsKey(mission)) {
            mapOfQuest.put(mission, Status.DISCOVERED);
            listOfActiveMission.add(mission);
            System.out.println(String.format("New mission added: %s", mission.getTitle()));
        } else throw new Exception("Mission already exists in the Mission Log.");
    }

    public void updateQuest(Mission mission, Status status) throws Exception {
        if (mapOfQuest.containsKey(mission)) {
            mapOfQuest.replace(mission, status);
            if (status == Status.COMPLETED) {
                System.out.println(String.format("Mission completed: %s", mission.getTitle()));
                listOfActiveMission.remove(mission);

            } else if (status == Status.FAILED) {
                System.out.println(String.format("Mission failed: %s", mission.getTitle()));
                listOfActiveMission.remove(mission);

            } else throw new Exception("Invalid mission status update.");

        } else throw new Exception("Mission Manager did not have initiateMission to begin with.");
    }

}
