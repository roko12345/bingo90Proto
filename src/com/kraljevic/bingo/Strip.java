package com.kraljevic.bingo;
import java.util.Arrays;
import java.util.stream.IntStream;

public class Strip {
    private Ticket[] tickets;
    
    public Strip() {
        tickets = new Ticket[6];
        for(int i=0; i < tickets.length; i++) {
            tickets[i] = new Ticket();
        }
    }
    
    public void seedTheTicketWithNumbers(){
        
    }
    
    public void fillFirstColumnEachTicketWithOneNumber() {
        int tens[] = IntStream.rangeClosed(1, 9).toArray();
        
    }
    
    
    
    
    public Ticket[] getSeededStrip() {
        return tickets;
    }
}
