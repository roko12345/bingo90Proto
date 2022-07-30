package com.kraljevic.bingo;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Strip {
    private Ticket[] tickets;
    private Map<Integer, List<Integer>> remainingNumbers;

    public Strip() {
        tickets = new Ticket[6];
        for (int i = 0; i < tickets.length; i++) {
            tickets[i] = new Ticket();
        }
        initialFillRemainingNumbersMap();
        fillAllColumnsEachTicketWithOneNumber(remainingNumbers);
        fillAllTicketsWithRemainingNumbers(remainingNumbers);
        
        //seedTheTicketWithNumbers();
    }
    
    private void initialFillRemainingNumbersMap() {
        remainingNumbers = new LinkedHashMap<>();
        remainingNumbers.put(0,IntStream.rangeClosed(1, 9).boxed().collect(Collectors.toList()));
        for (int i=1; i < 8; i++) {
            remainingNumbers.put(i,IntStream.rangeClosed(i*10, i*10+9).boxed().collect(Collectors.toList()));
        }
        remainingNumbers.put(8,IntStream.rangeClosed(80, 90).boxed().collect(Collectors.toList()));
    }
    
    public Map<Integer, List<Integer>> getRemainingNumbers() {
        return remainingNumbers;
    }

    public void seedTheTicketWithNumbers() {
        fillFirstStripColumn();
        fillSecondToFifthStripColumn();

    }

    public void fillFirstStripColumn() {
        List<Integer> tens = IntStream.rangeClosed(1, 9).boxed().collect(Collectors.toList());
        Integer numbersRemainingInList = 9;

        numbersRemainingInList = fillFirstColumnEachTicketWithOneNumber(tens, numbersRemainingInList);
        fillFirstColumnWithRemainingNumbers(tens, numbersRemainingInList);
    }


    public Integer fillFirstColumnEachTicketWithOneNumber(List<Integer> tens, Integer numbersRemainingInList) {
        // iterate through tickets from 1 to 6
        for (int i = 0; i < tickets.length; i++) {
            int positionInTheList = Utils.getRandomNumber(0, numbersRemainingInList - 1);
            tickets[i].setTicketFieldValue(tens.get(positionInTheList), 0, Utils.getRandomNumber(0, 2));
            tens.remove(positionInTheList);
            numbersRemainingInList--;
        }
        return numbersRemainingInList;
    }

    public void fillFirstColumnWithRemainingNumbers(List<Integer> tensRemaining, Integer numbersRemainingInList) {
        var initialRemainingItems = tensRemaining.size();

        for (int i = 0; i < initialRemainingItems; i++) {
            int randomTicketToFillWithTens = Utils.getRandomNumber(0, 5);
            int positionInTheList = Utils.getRandomNumber(0, numbersRemainingInList - 1);

            List<Integer> freePositionsInColumn = tickets[randomTicketToFillWithTens].freePositionsInColumn(0);
            if (freePositionsInColumn.isEmpty()) {
                // Take new ticket and fill the column there
                this.fillFirstColumnWithRemainingNumbers(tensRemaining, numbersRemainingInList);
                return;
            }

            Random rand = new Random();
            tickets[randomTicketToFillWithTens].setTicketFieldValue(tensRemaining.get(positionInTheList), 0, freePositionsInColumn.get(rand.nextInt(freePositionsInColumn.size())));

            tensRemaining.remove(positionInTheList);
            numbersRemainingInList--;
        }
    }


    public void fillSecondToFifthStripColumn() {
        for (int i = 1; i < 5; i++) {
            List<Integer> twentiesToFifties = getValuesBasedOnColumnNumber(i);
            Integer numbersRemainingInList = 10;

            numbersRemainingInList = fillSecondToFifthColumnEachTicketWithOneNumber(twentiesToFifties, numbersRemainingInList, i);
            fillSecondToFifthColumnWithRemainingNumbers(twentiesToFifties, numbersRemainingInList, i);
        }

    }

    public Integer fillSecondToFifthColumnEachTicketWithOneNumber(List<Integer> twentiesToFifties, Integer numbersRemainingInList, int columnNumber) {
        // iterate through tickets from 1 to 6
        for (int i = 0; i < tickets.length; i++) {
            int positionInTheList = Utils.getRandomNumber(0, numbersRemainingInList - 1);
            tickets[i].setTicketFieldValue(twentiesToFifties.get(positionInTheList), columnNumber, Utils.getRandomNumber(0, 2));
            twentiesToFifties.remove(positionInTheList);
            numbersRemainingInList--;
        }
        return numbersRemainingInList;
    }

    public void fillSecondToFifthColumnWithRemainingNumbers(List<Integer> twentiesToFiftiesRemaining, Integer numbersRemainingInList, int columnNumber) {
        var initialRemainingItems = twentiesToFiftiesRemaining.size();

        for (int i = 0; i < initialRemainingItems; i++) {
            int randomTicketToFillWithTens = Utils.getRandomNumber(0, 5);
            int positionInTheList = Utils.getRandomNumber(0, numbersRemainingInList - 1);

            List<Integer> freePositionsInColumn = tickets[randomTicketToFillWithTens].freePositionsInColumn(columnNumber);
            if (freePositionsInColumn.isEmpty()) {
                // Take new ticket and fill the column there
                this.fillSecondToFifthColumnWithRemainingNumbers(twentiesToFiftiesRemaining, numbersRemainingInList, columnNumber);
                return;
            }

            Random rand = new Random();
            tickets[randomTicketToFillWithTens].setTicketFieldValue(twentiesToFiftiesRemaining.get(positionInTheList), columnNumber, freePositionsInColumn.get(rand.nextInt(freePositionsInColumn.size())));

            twentiesToFiftiesRemaining.remove(positionInTheList);
            numbersRemainingInList--;
        }
    }

    public List<Integer> getValuesBasedOnColumnNumber(int columnNumber) {
        switch (columnNumber){
            case 1:
                return IntStream.rangeClosed(10, 19).boxed().collect(Collectors.toList());
            case 2:
                return IntStream.rangeClosed(20, 29).boxed().collect(Collectors.toList());
            case 3:
                return IntStream.rangeClosed(30, 39).boxed().collect(Collectors.toList());
            case 4:
                return IntStream.rangeClosed(40, 49).boxed().collect(Collectors.toList());
            default:
                return null;
        }
        
            
    }

    
    /////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////
    // NEW APPROACH
    // First fill all columns on each ticket with at least one number
    // Then go ticket by ticket and redistribute remaining numbers, having in mind that we need to have
    // 5 numbers exactly in each row
    /////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////
    
    public void fillAllColumnsEachTicketWithOneNumber(Map<Integer, List<Integer>> numbersRemaining) {
        // iterate through each column
        for (var column: numbersRemaining.entrySet()) {
            
            // iterate through tickets from 1 to 6
            for (int i = 0; i < tickets.length; i++) {
                int positionInTheList = Utils.getRandomNumber(0, column.getValue().size());
                int columnPositionToInsert = Utils.getRandomNumber(0, 3);
                
                // if the row already has 5 numbers (max) then insert the value into column position in one of other two rows
                while(tickets[i].isRowFull(columnPositionToInsert))
                    columnPositionToInsert = Utils.getRandomNumber(0, 3);
                    
                tickets[i].setTicketFieldValue(column.getValue().get(positionInTheList), column.getKey(), columnPositionToInsert);
                column.getValue().remove(positionInTheList);
            }
        }
    }
    
    public void fillAllTicketsWithRemainingNumbers(Map<Integer, List<Integer>> numbersRemaining) {
        
    }
    
    
    

    public Ticket[] getSeededStrip() {
        return tickets;
    }
}
