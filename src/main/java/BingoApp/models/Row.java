package BingoApp.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Row {
    private Integer[] rowFields;
    public List<Integer> filledWithSecondIteration;

    public Row() {
        rowFields = new Integer[9];
        Arrays.setAll(rowFields, p -> 0);
        filledWithSecondIteration = new ArrayList<>();
    }

    public void setRowField(int value, int position) {
        this.rowFields[position] = value;
    }

    public Integer[] getRowFields() {
        return rowFields;
    }
}
