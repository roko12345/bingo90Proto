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
        fillAllColumnsEachTicketWithOneNumber();
        fillAllTicketsWithRemainingNumbers();

        //seedTheTicketWithNumbers();
    }

    private void initialFillRemainingNumbersMap() {
        remainingNumbers = new LinkedHashMap<>();
        remainingNumbers.put(0, IntStream.rangeClosed(1, 9).boxed().collect(Collectors.toList()));
        for (int i = 1; i < 8; i++) {
            remainingNumbers.put(i, IntStream.rangeClosed(i * 10, i * 10 + 9).boxed().collect(Collectors.toList()));
        }
        remainingNumbers.put(8, IntStream.rangeClosed(80, 90).boxed().collect(Collectors.toList()));
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
        switch (columnNumber) {
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

    private void fillAllColumnsEachTicketWithOneNumber() {
        // iterate through each column
        for (var column : remainingNumbers.entrySet()) {

            // iterate through tickets from 1 to 6
            for (int i = 0; i < tickets.length; i++) {
                int positionInTheList = Utils.getRandomNumber(0, column.getValue().size());
                int columnPositionToInsert = Utils.getRandomNumber(0, 3);

                // if the row already has 5 numbers (max) then insert the value into column position in one of other two rows
                while (tickets[i].isRowFull(columnPositionToInsert))
                    columnPositionToInsert = Utils.getRandomNumber(0, 3);

                tickets[i].setTicketFieldValue(column.getValue().get(positionInTheList), column.getKey(), columnPositionToInsert);
                column.getValue().remove(positionInTheList);
            }
        }
    }

    private void fillAllTicketsWithRemainingNumbers() {
        boolean rowOk = true;
        
        
        // iterate through tickets from 1 to 6
        // if ticket row is not full, fill it with random numbers from remaining numbers map 
        for (int i = 0; i < tickets.length; i++) {
            for (int row = 0; row < 3; row++) {
                
                if(rowOk == false) { // we returned one row back and try to remove values added to the columns in last iteration and
                                    // filling different row columns now
                    List<Integer> valuesFilledInPreviousTry = new ArrayList<>();
                    try {
                        valuesFilledInPreviousTry = tickets[i].getRows()[row].filledWithSecondIteration;    
                    } catch (Exception e) {
                        // If we end up here, it means that i=-1 , and that the remaining number could not be fixed along the way even 
                        // in the first ticket. We need to seed it from the begining and repeat the proccess.
                        System.out.println("Exception");
                        createTheStripAgain();
                     return;   
                    }
                    
                    // check if there is a free column where we can put one of remamining map values after we remove "lastFilledElement" from the row
                    boolean freeColumnExists = false;
                    List<Integer> remainingRowPositions = tickets[i].freePositionsInRow(row);
                    for(Integer rowPosition: remainingRowPositions) {
                        if (!remainingNumbers.get(rowPosition).isEmpty()) {
                            freeColumnExists = true;
                            break;
                        }
                    }
                    
                    if(valuesFilledInPreviousTry.isEmpty() || !freeColumnExists) {
                        if(row == 0) { // first row in the ticket is fucked, go back to last row on predccessor ticket
                            i = i-2;
                            row = 2;
                            rowOk = false;
                        } else {
                            row = row - 2;
                            rowOk = false;
                        }

                      //  System.out.println("Exiting");
                        continue;
                    }

                    for(Integer rowPosition: remainingRowPositions) {
                        if (!remainingNumbers.get(rowPosition).isEmpty()) {
                            // add one map number to empty column in this row. We will remove "lastFilledElement" from the row and add it to the map
                            tickets[i].getRows()[row].getRowFields()[rowPosition] = remainingNumbers.get(rowPosition).get(0);
                            remainingNumbers.get(rowPosition).remove(0);
                            break;
                        }
                    }
                    
                    Integer lastFilledElement = valuesFilledInPreviousTry.get(valuesFilledInPreviousTry.size() - 1);
                    for(int j=0; j<9; j++) {
                        if (tickets[i].getRows()[row].getRowFields()[j] == lastFilledElement) {

                            // by removing item from "filledWithSecondIteration" we lower the count of that list so we have one less
                            // to check if the next occurrance comes. We also put empty space in place of that value in the row array
                            tickets[i].getRows()[row].filledWithSecondIteration.remove(valuesFilledInPreviousTry.size()-1);
                            tickets[i].getRows()[row].getRowFields()[j] = 0;
                            
                            // add the number which we removed from this row to the map with remaining numbers
                            remainingNumbers.get(j).add(lastFilledElement);
                        }
                    }
                    rowOk = true;
                    
                }
                
                while (!tickets[i].isRowFull(row)) {
                    List<Integer> remainingRowPositions = tickets[i].freePositionsInRow(row);

                    fillOneOfTheRemainingRowPositions(tickets[i], remainingRowPositions, row);
                    if(remainingRowPositions.isEmpty()) {
                        if(row == 0) { // first row in the ticket is fucked, go back to last row on predccessor ticket
                            i = i-2;
                            row = 2;
                            rowOk = false;
                        } else {
                            row = row - 2;
                            rowOk = false;
                        }
                        
                        break;
                    }
                }
            }
        }
    }

    // fill one of the free positions in the row from the Map containing all remaining values
    private void fillOneOfTheRemainingRowPositions(Ticket ticket, List<Integer> remainingRowPositions, int row) {
        
        // If remainingRowPositions is null in currnet row, that means that none of the remaining numbers can be fit into that row.
        // We fucked up earlier, so we backtrack the proccess and go one row back, try to change numbers used there
        if (remainingRowPositions.isEmpty()) {

          /*  List<Integer> valuesFilledInPreviousTry = ticket.getRows()[row].filledWithSecondIteration;
            if(valuesFilledInPreviousTry.isEmpty()) {
                return;
            }
            Integer lastFilledElement = valuesFilledInPreviousTry.get(valuesFilledInPreviousTry.size() - 1);
            for(int j=0; j<9; j++) {
                if (ticket.getRows()[row].getRowFields()[j] == lastFilledElement) {
                    ticket.getRows()[row].getRowFields()[j] = 0;
                    remainingNumbers.get(j).add(lastFilledElement);
                }
            }*/
            return;
        }
        
        
        int randomValue = Utils.getRandomNumber(0, remainingRowPositions.size());
        
        int randomRowPositionValue = remainingRowPositions.get(randomValue);
        List<Integer> availableValuesForTheRowSpot = remainingNumbers.get(randomRowPositionValue);

        // if this is true that means no more available numbers to put into that row spot
        if (availableValuesForTheRowSpot.isEmpty()) {
            remainingRowPositions.remove(randomValue);
            fillOneOfTheRemainingRowPositions(ticket, remainingRowPositions, row);
            return;
        } else {
            // take one of the available values and put into that row spot. Also remove the value from the map.
            int randomValueToTakeFromRemainingNumbers = Utils.getRandomNumber(0, availableValuesForTheRowSpot.size());
            var valueFromRemainingValues = availableValuesForTheRowSpot.get(randomValueToTakeFromRemainingNumbers);
            ticket.setTicketFieldValue(valueFromRemainingValues, randomRowPositionValue, row);
            ticket.getRows()[row].filledWithSecondIteration.add(valueFromRemainingValues);
            remainingNumbers.get(randomRowPositionValue).remove(randomValueToTakeFromRemainingNumbers);
        }
    }

   /* private List<Integer> getColumsWithRemainingValuesLeft() {
        var filteredMap = remainingNumbers.entrySet().stream().filter(r -> !r.getValue().isEmpty()).collect(Collectors.toMap(m -> m.getKey(), m -> m.getValue()));
        return filteredMap.keySet().stream().collect(Collectors.toList());
    }*/
    
    public void createTheStripAgain() {
        tickets = new Ticket[6];
        for (int i = 0; i < tickets.length; i++) {
            tickets[i] = new Ticket();
        }

        initialFillRemainingNumbersMap();
        fillAllColumnsEachTicketWithOneNumber();
        fillAllTicketsWithRemainingNumbers();
    }
    
    public Ticket[] getSeededStrip() {
        return tickets;
    }
}
