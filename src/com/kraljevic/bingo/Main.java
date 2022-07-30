package com.kraljevic.bingo;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
	// write your code here
        long start1 = System.nanoTime();


        
       
        Strip strip = new Strip();
        Ticket[] tickets = strip.getSeededStrip();
        Map<Integer, List<Integer>> remainingNumbersMap = strip.getRemainingNumbers();
        for(int i=0; i < tickets.length; i++){
            System.out.println("\n");
            tickets[i].isRowFull(1);
            Row[] ticketRows = tickets[i].getRows();
            for(int j=0; j < ticketRows.length; j++) {
                System.out.println(Arrays.toString(ticketRows[j].getRowFields()));
               // System.out.println("\n");
            }
        }
        
        for(var x : remainingNumbersMap.entrySet()) {
            System.out.println("Column " + x.getKey() + ":");
            for(var y : x.getValue()) {
                System.out.print(y + ",");
            }
            System.out.println();
        }

        /*
        for(int i=0; i<10000; i++) {
            System.out.println("Execution: " + i);
            Strip strip = new Strip();
            Ticket[] tickets = strip.getSeededStrip();
        }

         */

        for(int i=0; i < tickets.length; i++){ {
            for(int j=0; j < 3; j++)
            System.out.println(tickets[i].isRowFull(j));
        }}
        
        
        long end1 = System.nanoTime();
        System.out.println("Elapsed Time in nano seconds: "+ (end1-start1));
    }
}
