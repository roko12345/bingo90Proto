package com.kraljevic.bingo;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
	// write your code here
        long start1 = System.nanoTime();


        
        
        Strip strip = new Strip();
        Ticket[] tickets = strip.getSeededStrip();
        for(int i=0; i < tickets.length; i++){
            System.out.println("\n");
            tickets[i].isRowFull(1);
            Row[] ticketRows = tickets[i].getRows();
            for(int j=0; j < ticketRows.length; j++) {
                System.out.println(Arrays.toString(ticketRows[j].getRowFields()));
               // System.out.println("\n");
            }
        }

        /*for(int i=0; i<10000; i++) {
            System.out.println("Execution: " + i);
            Strip strip = new Strip();
            Ticket[] tickets = strip.getSeededStrip();
        }*/

        long end1 = System.nanoTime();
        System.out.println("Elapsed Time in nano seconds: "+ (end1-start1));
    }
}
