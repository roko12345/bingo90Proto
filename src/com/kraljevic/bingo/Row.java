package com.kraljevic.bingo;
import java.util.Arrays;

public class Row {
    private Integer[] rowFields ;
    
    public Row() {
        rowFields = new Integer[9];
        Arrays.setAll(rowFields, p -> 0);
    }

    public void setRowField(int value, int position) {
        this.rowFields[position] = value;
    }
    
    public Integer[] getRowFields(){
        return rowFields;
    }
}
