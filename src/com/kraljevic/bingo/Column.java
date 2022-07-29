package com.kraljevic.bingo;
import java.util.Arrays;

public class Column {
    private int columnFields [];
    
    public Column() {
        columnFields = new int[3];
        Arrays.setAll(columnFields, p -> 0);
    }

    public void setColumnField(int value, int position) {
        this.columnFields[position] = value;
    }
    
    public int[] getColumnFields() {
        return columnFields;
    }
}
