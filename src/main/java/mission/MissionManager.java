package mission;

import printer.PrintItems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* Manages Mission log
    SINGLETON
 */

public class MissionManager implements PrintItems {
    private static MissionManager instance = new MissionManager();
    private static ArrayList<Mission> listOfActiveMission = new ArrayList<>();
    private static Map<Mission, Status> mapOfQuest = new HashMap<>();

    public static MissionManager getInstance() {
        return instance;
    }

    private MissionManager() {
    }

    public void addQuest (Mission mission) throws Exception {
        if (!mapOfQuest.containsKey(mission)) {
            mapOfQuest.put(mission, Status.DISCOVERED);
            listOfActiveMission.add(mission);
            System.out.println(String.format("New mission added: %s", mission.getName()));
        } else throw new Exception("Mission already exists in the Mission Log.");
    }

    public void updateQuest(Mission mission, Status status) throws Exception {
        if (mapOfQuest.containsKey(mission)) {
            mapOfQuest.replace(mission, status);
            if (status == Status.COMPLETED) {
                System.out.println(String.format("Mission completed: %s", mission.getName()));
                listOfActiveMission.remove(mission);

            } else if (status == Status.FAILED) {
                System.out.println(String.format("Mission failed: %s", mission.getName()));
                listOfActiveMission.remove(mission);

            } else throw new Exception("Invalid mission status update.");

        } else {
            mapOfQuest.replace(mission, status);
        }

        //else throw new Exception("Mission Manager did not have this mission to begin with.");
    }

    @Override
    public void print() {
        System.out.println("<< Missions Log >>");
        if (this.listOfActiveMission.size() == 0) {
            System.out.println("No active missions.");
        } else {
            int i = 0;
            for (Mission m : this.listOfActiveMission) {
                System.out.println(String.format("%d - %s", i, m.getName()));
                i++;
            }
        }
    }

}
