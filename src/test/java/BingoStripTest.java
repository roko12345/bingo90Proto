import BingoApp.Strip;
import BingoApp.Utils;
import BingoApp.models.Column;
import BingoApp.models.Ticket;
import com.google.common.collect.Comparators;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BingoStripTest {

    private Strip strip = new Strip();
    private static int TEN_THOUSAND = 10000;


    @Test
    public void noEmptyColumnsOnAnyTicketTest() {

        Ticket[] tickets = strip.getSeededStrip();
        int emptyColumns = 0;

        for (int i = 0; i < Utils.TICKETS_PER_STRIP; i++) {
            for (int j = 0; j < Utils.COLUMNS_PER_TICKET; j++) {
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

        for (int i = 0; i < Utils.TICKETS_PER_STRIP; i++) {
            for (int j = 0; j < Utils.ROWS_PER_TICKET; j++) {
                boolean rowCorrect = Arrays.asList(tickets[i].getRows()[j].getRowFields()).stream().filter(v -> v == 0).count() == 4;
                if (rowCorrect == true) {
                    rowsWithExactlyFiveNumbers++;
                }
            }
        }
        Assert.assertEquals(rowsWithExactlyFiveNumbers, Utils.TOTAL_ROWS_PER_STRIP);

    }

    @Test
    public void stripHasAllNinetyNumbersTest() {
        Ticket[] tickets = strip.getSeededStrip();
        List<Integer> allNumbers = new ArrayList<>();

        // Collect all numbers from the Strip
        for (int i = 0; i < Utils.TICKETS_PER_STRIP; i++) {
            for (int j = 0; j < Utils.ROWS_PER_TICKET; j++) {
                // get all ticket numbers that are not 0 (empty space)
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
    public void allTicketsSortedTest() {
        Ticket[] tickets = strip.getSeededStrip();

        for (int i = 0; i < Utils.TICKETS_PER_STRIP; i++) {
            Column[] columns = tickets[i].getColumns();
            for (int j = 0; j < Utils.COLUMNS_PER_TICKET; j++) {
                // we will take values which are not zero (as zeros are intact when sorting the original tickets), 
                // and see if the values are sorted
                List<Integer> coolumnNonZeroValues = Arrays.asList(columns[j].getColumnFields()).stream().filter(v -> v != 0).collect(Collectors.toList());

                Assert.assertTrue(Comparators.isInOrder(coolumnNonZeroValues, Comparator.naturalOrder()));
            }
        }
    }

    @Test
    public void checkAllRulesToTicketValidityTest() {
        noEmptyColumnsOnAnyTicketTest();
        everyRowHasExactlyFiveNumbersTest();
        stripHasAllNinetyNumbersTest();
        allTicketsSortedTest();
    }

    @Test
    public void tenThousandTimesCheckEveryRowHasExactlyFiveNumbersTest() {
        for (int i = 0; i < TEN_THOUSAND; i++) {
            strip = new Strip();
            everyRowHasExactlyFiveNumbersTest();
        }
    }

    @Test
    public void tenThousandTimesNoEmptyColumnsTest() {
        for (int i = 0; i < TEN_THOUSAND; i++) {
            strip = new Strip();
            noEmptyColumnsOnAnyTicketTest();
        }
    }

    @Test
    public void tenThousandTimescheckAllRulesToTicketValidityTest() {
        for (int i = 0; i < TEN_THOUSAND; i++) {
            strip = new Strip();
            checkAllRulesToTicketValidityTest();
        }
    }
}
