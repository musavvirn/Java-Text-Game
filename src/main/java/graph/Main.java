package graph;

import javax.swing.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class Main extends JPanel {

    public static void main(String[] args) throws Exception {
        Location L1 = new Location();
        City C1 = new City("Sasau"), C2 = new City("Rattay"), C3 = new City("Ledetchko");
        Village V1 = new Village("Sasau Farms"), V2 = new Village("South Road Farms"),
                V3 = new Village("Rattay Countryside"), V4 = new Village("Terak Village");

        L1.addNode(C1, Arrays.asList(C2, V1, V2, V3));
        L1.addNode(C3, Arrays.asList(C2, V2, V4));
        L1.addVertex(V1, V2);
        L1.addVertex(V1, V3);



        JFrame frame = new JFrame("Demo Frame");
        frame.setSize(500, 500); frame.setVisible(true); frame.getContentPane();

        JPanel panel = new JPanel(); panel.setLayout(null);

        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(panel);

        HashMap map = L1.getMap();

        for (Object o : map.keySet()) {
            Vertex v = (Vertex) o;
            int x = new Random().nextInt(480);
            int y = new Random().nextInt(480);
            v.setCoordinates(x, y);
        }

        for (Object o : map.keySet()) {
            Vertex v = (Vertex) o;
            JButton l = new JButton(v.getName());
            l.setBounds(v.getXY()[0], v.getXY()[1], 80, 20);
            panel.add(l);
        }





    }


}
