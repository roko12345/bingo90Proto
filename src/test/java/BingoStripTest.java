import BingoApp.Strip;
import BingoApp.models.Ticket;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BingoStripTest {

    private Strip strip = new Strip();


    @Test
    public void noEmptyColumnsOnAnyTicketTest() {

        Ticket[] tickets = strip.getSeededStrip();
        int emptyColumns = 0;

        for (int i = 0; i < tickets.length; i++) {
            for (int j = 0; j < tickets[i].getColumns().length; j++) {
                boolean x = Arrays.asList(tickets[i].getColumns()[j].getColumnFields()).stream().filter(v -> v == 0).count() == 3;
                if (x == true) {
                    emptyColumns++;
                }
            }
        }
        Assert.assertEquals(emptyColumns, 0);
    }

    @Test
    public void everyRowHasExactlyFiveNumbersTest() {

        // Row has 5 numbers, but we will test for 4 empty spaces, it's the same thing
        Ticket[] tickets = strip.getSeededStrip();
        int rowsWithExactlyFiveNumbers = 0;
        int totalRowsOnStrip = 18;

        for (int i = 0; i < tickets.length; i++) {
            for (int j = 0; j < tickets[i].getRows().length; j++) {
                boolean x = Arrays.asList(tickets[i].getRows()[j].getRowFields()).stream().filter(v -> v == 0).count() == 4;
                if (x == true) {
                    rowsWithExactlyFiveNumbers++;
                }
            }
        }
        Assert.assertEquals(rowsWithExactlyFiveNumbers, totalRowsOnStrip);

    }

    @Test
    public void stripHasAllNinetyNumbersTest() {
        Ticket[] tickets = strip.getSeededStrip();
        List<Integer> allNumbers = new ArrayList<>();

        // Collect all numbers from the Strip
        for (int i = 0; i < tickets.length; i++) {
            for (int j = 0; j < tickets[i].getRows().length; j++) {
                allNumbers.addAll(Arrays.asList(tickets[i].getRows()[j].getRowFields()).stream().filter(v -> v != 0).collect(Collectors.toList()));
            }
        }

        //Check that every number from 1 to 90 is present on the strip
        Assert.assertEquals(allNumbers.size(), 90);
        for (int i = 1; i <= 90; i++) {
            Assert.assertTrue(allNumbers.contains(i));
        }
    }

    @Test
    public void checkAllRulesToTicketValidityTest() {
        noEmptyColumnsOnAnyTicketTest();
        everyRowHasExactlyFiveNumbersTest();
        stripHasAllNinetyNumbersTest();
    }

    @Test
    public void tenThousandTimesCheckEveryRowHasExactlyFiveNumbersTest() {
        for (int i = 0; i < 10000; i++) {
            strip = new Strip();
            everyRowHasExactlyFiveNumbersTest();
        }
    }

    @Test
    public void tenThousandTimesNoEmptyColumnsTest() {
        for (int i = 0; i < 10000; i++) {
            strip = new Strip();
            noEmptyColumnsOnAnyTicketTest();
        }
    }

    @Test
    public void tenThousandTimescheckAllRulesToTicketValidityTest() {
        for (int i = 0; i < 10000; i++) {
            strip = new Strip();
            checkAllRulesToTicketValidityTest();
        }
    }
    
}
