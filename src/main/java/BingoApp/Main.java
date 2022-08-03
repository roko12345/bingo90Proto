package BingoApp;

import BingoApp.models.Row;
import BingoApp.models.Ticket;

import java.io.PrintWriter;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        // Checking the input command line args (should be integer)
        int numberOfStrips;
        try {
            numberOfStrips = Integer.parseInt(args[0]);
            if (numberOfStrips == 0) {
                System.out.println("You need to request more than 0 strips to create.");
                return;
            }
        } catch (Exception e) {
            System.out.println("Wrong input format " + e.getMessage());
            return;
        }

        // Start time measurement 
        long start1 = System.nanoTime();

        // Create the strips
        Strip[] strips = new Strip[numberOfStrips];
        for (int i = 0; i < numberOfStrips; i++) {
            strips[i] = new Strip();
        }
        // End time measurement for Strips creation
        long end1 = System.nanoTime();

        PrintWriter out = new PrintWriter(System.out);

        // Print the strips to the console
        for (int i = 0; i < numberOfStrips; i++) {
            Ticket[] tickets = strips[i].getSeededStrip();
            out.print("Execution: " + i + "\n");

            for (int j = 0; j < Utils.TICKETS_PER_STRIP; j++) {
                Row[] ticketRows = tickets[j].getRows();
                for (int k = 0; k < Utils.ROWS_PER_TICKET; k++) {
                    out.print(Arrays.toString(ticketRows[k].getRowFields()) + "\n");
                }
                out.print("\n");
            }
        }
        out.flush();

        // End time measurement for Strips printing in the console
        long end2 = System.nanoTime();

        System.out.println("Elapsed Time to create strips: " + (end1 - start1) + " ns. (" + (double) (end1 - start1) / 1_000_000_000 + " s)");
        System.out.println("Elapsed Time to print strips: " + (end2 - end1) + " ns. (" + (double) (end2 - end1) / 1_000_000_000 + " s)");
        System.out.println("Elapsed Time Total: " + (end2 - start1) + " ns. (" + (double) (end2 - start1) / 1_000_000_000 + " s)");
    }
}
