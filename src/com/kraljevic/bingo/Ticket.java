package com.kraljevic.bingo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Ticket {
    private Row[] rows;
    private Column[] columns;
    
    public Ticket() {
        rows = new Row[3];
        columns = new Column[9];
        initializeRows();
        initializeColumns();
    }
    
    private void initializeRows() {
        for(int i=0; i < rows.length; i++) {
            rows[i] = new Row();
        }
    }

    private void initializeColumns() {
        for(int i=0; i < columns.length; i++) {
            columns[i] = new Column();
        }
    }

    public Row[] getRows() {
        return rows;
    }

    public Column[] getColumns() {
        return columns;
    }

    public void setTicketFieldValue(int value, int rowPosition, int columnPosition) {
        rows[columnPosition].setRowField(value, rowPosition);
        columns[rowPosition].setColumnField(value, columnPosition);
    }
    
    // Column is full if there are no more empty values in it (represented by 0)
    public boolean isColumnFull(int columnnNumber) {
        return new ArrayList<>(Arrays.asList(columns[columnnNumber].getColumnFields())).contains(0);
    }

    // Row is full if there are 4 zeros left in it
    public boolean isRowFull(int rowNumber) {
        return new ArrayList<>(Arrays.asList(rows[rowNumber].getRowFields())).stream().filter(r -> (r == 0)).count() <= 4;
    }
    
    // free positions in column are those with value 0
    public List<Integer> freePositionsInColumn(int columnNumber) {
        List<Integer> freePositions = new ArrayList<>();
        var allColumnFields = columns[columnNumber].getColumnFields();
        for(int i=0; i < 3; i++) {
            if(allColumnFields[i] == 0) {
                freePositions.add(i);
            }
        }
        return freePositions;
    }

}
