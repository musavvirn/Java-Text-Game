package choice;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;


public class ChoiceTest {

    @Mock
    Choice C1;
    @Mock
    Choice C2;

    @Spy
    Choice C3 = new Choice("C3");

    @InjectMocks
    Choice CMain;


    @InjectMocks
    Choice CMain2;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddChoice() throws Exception {
        CMain.addChoice(C1); CMain.addChoice(C2);
        Assert.assertEquals(2, CMain.getChoices().size());

        CMain.addChoice(C3);
        Assert.assertEquals(CMain, C3.parentChoice);
    }

    @Test(expected = Exception.class)
    public void testAddChoice2() throws Exception {
        CMain.addChoice(C1);
        CMain.addChoice(C1);
    }



}
