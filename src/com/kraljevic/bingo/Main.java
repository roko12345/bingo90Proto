package com.kraljevic.bingo;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Strip strip = new Strip();
        Ticket[] tickets = strip.getSeededStrip();
        for(int i=0; i < tickets.length; i++){
            System.out.println("\n");
            Row[] ticketRows = tickets[i].getRows();
            for(int j=0; j < ticketRows.length; j++) {
                System.out.println(Arrays.toString(ticketRows[j].getRowFields()));
               // System.out.println("\n");
            }
        }
        
    }
}
