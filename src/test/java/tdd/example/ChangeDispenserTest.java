package tdd.example;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class ChangeDispenserTest {

    @Mock
    private DenominationsRetriever denominationsRetriever;

    @Mock
    private MonetaryItemDispenser monetaryItemDispenser;

    @InjectMocks
    private ChangeDispenser changeDispenser;


    @Test
    public void shouldDispenseCoinsIfOnyOneCoinIsAvailable() throws Exception {
        when(denominationsRetriever.getValidDenominations()).thenReturn(singletonList(1));

        changeDispenser.dispensesChange(3);

        verify(monetaryItemDispenser, times(3)).dispense(1);
    }

    @Test
    public void shouldDispenseCoinsWithMinimumCoins() throws Exception {
        when(denominationsRetriever.getValidDenominations()).thenReturn(asList(1, 2, 5));

        changeDispenser.dispensesChange(8);

        verify(monetaryItemDispenser, times(1)).dispense(5);
        verify(monetaryItemDispenser, times(1)).dispense(2);
        verify(monetaryItemDispenser, times(1)).dispense(1);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowExceptionIfReturnIsNotPossible() throws Exception {
        when(denominationsRetriever.getValidDenominations()).thenReturn(asList(7, 13));

        changeDispenser.dispensesChange(11);
    }

    @Test
    public void shouldDispenseCoinsMultipleTimes() throws Exception {
        when(denominationsRetriever.getValidDenominations()).thenReturn(asList(1, 10));

        changeDispenser.dispensesChange(44);

        verify(monetaryItemDispenser, times(4)).dispense(10);
        verify(monetaryItemDispenser, times(4)).dispense(1);
    }

    @Test
    public void shouldDispenseNothing() throws Exception {
        when(denominationsRetriever.getValidDenominations()).thenReturn(asList(1, 2));

        changeDispenser.dispensesChange(0);
        verify(monetaryItemDispenser, times(0)).dispense(anyInt());
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowExceptionOnNegativeValues() throws Exception {
        when(denominationsRetriever.getValidDenominations()).thenReturn(asList(1, 10));

        changeDispenser.dispensesChange(-5);
    }

}
