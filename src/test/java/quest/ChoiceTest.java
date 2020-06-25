package quest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class ChoiceTest {

    @Mock
    Choice C1;
    @Mock
    Choice C2;
    @Mock
    Choice C3;


    @InjectMocks
    private Choice CMain;

    @InjectMocks
    private Choice C4;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddChoice() throws Exception {
        CMain.addChoice(C1); CMain.addChoice(C2);
        Assert.assertEquals(2, CMain.getChoices().size());

    }

    @Test(expected = Exception.class)
    public void testAddChoice2() throws Exception {
        CMain.addChoice(C1);
        CMain.addChoice(C1);
    }

}
