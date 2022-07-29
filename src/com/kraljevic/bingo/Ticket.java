package com.kraljevic.bingo;

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

    public void setTicketFieldValue(int value, int rowPosition, int columnPosition) {
        rows[columnPosition].setRowField(value, rowPosition);
        columns[rowPosition].setColumnField(value, columnPosition);
    }
    
    public Row[] getRows() {
        return rows;
    }
    
    public Column[] getColumns() {
        return columns;
    }
}
