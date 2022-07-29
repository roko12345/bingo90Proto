package com.kraljevic.bingo;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Strip {
    private Ticket[] tickets;

    public Strip() {
        tickets = new Ticket[6];
        for (int i = 0; i < tickets.length; i++) {
            tickets[i] = new Ticket();
        }
        seedTheTicketWithNumbers();
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
    

    public Ticket[] getSeededStrip() {
        return tickets;
    }
}
