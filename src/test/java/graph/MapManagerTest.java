package graph;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

public class MapManagerTest {

    @Mock
    Village V1, V2, V3;

    @Mock
    City C1, C2;

    @InjectMocks
    MapManager L1;

//    Village V1 = new Village(""), V2 = new Village(""), V3 = new Village("");
//    City C1 = new City(""), C2 = new City("");
//    Location L1 = new Location();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddNode() throws Exception {
        L1.addNode(C1, Arrays.asList(V1, V2));
        L1.addVertex(V1, V2); L1.addVertex(V1, V3);
        L1.addNode(C2, Arrays.asList(V1));

        Assert.assertEquals(5, L1.getSize());
        Assert.assertEquals(4, L1.getNodeSize(V1));
        Assert.assertEquals(1, L1.getNodeSize(V3));
        Assert.assertTrue(L1.get(V1).contains(C2));
    }

    @Test(expected = Exception.class)
    public void testAddNode2() throws Exception {
        L1.addNode(C1, Arrays.asList(C1));
    }

    @Test public void testRemoveVertex() throws Exception {
        L1.addNode(C1, Arrays.asList(V1, V2));
        L1.addVertex(C1, C2);
        L1.removeNode(V1);
        Assert.assertEquals(3, L1.getSize());
        Assert.assertEquals(2, L1.getNodeSize(C1));
        Assert.assertFalse(L1.get(C1).contains(V1));
    }


}
