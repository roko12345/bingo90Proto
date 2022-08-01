package com.kraljevic.bingo;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
	
        long start1 = System.nanoTime();
       
        Strip strip = new Strip();
        strip.printStrip();

        Map<Integer, List<Integer>> remainingNumbersMap = strip.getRemainingNumbers();
        System.out.println("\nRemaining numbers per column...");
        for(var x : remainingNumbersMap.entrySet()) {
            System.out.println("Column " + x.getKey() + ":");
            for(var y : x.getValue()) {
                System.out.print(y + ",");
            }
        }
        
        /*
        for(int i=0; i<10000; i++) {
            System.out.println("Execution: " + i);
            Strip strip = new Strip();
            Ticket[] tickets = strip.getSeededStrip();
        }
        */
        
        long end1 = System.nanoTime();
        System.out.println("Elapsed Time in nano seconds: "+ (end1-start1));
    }
}
