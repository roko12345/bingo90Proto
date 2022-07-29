package com.kraljevic.bingo;
import java.util.Arrays;

public class Column {
    private Integer[] columnFields ;
    
    public Column() {
        columnFields = new Integer[3];
        Arrays.setAll(columnFields, p -> 0);
    }

    public void setColumnField(int value, int position) {
        this.columnFields[position] = value;
    }
    
    public Integer[] getColumnFields() {
        return columnFields;
    }
}
