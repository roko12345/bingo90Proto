package com.kraljevic.bingo;
import java.util.Arrays;

public class Row {
    private int rowFields [];
    
    public Row() {
        rowFields = new int[9];
        Arrays.setAll(rowFields, p -> 0);
    }

    public void setRowField(int value, int position) {
        this.rowFields[position] = value;
    }
    
    public int[] getRowFields(){
        return rowFields;
    }
}
