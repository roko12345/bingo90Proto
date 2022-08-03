package BingoApp;

public class Utils {
    public static final int COLUMNS_PER_TICKET = 9;
    public static final int ROWS_PER_TICKET = 3;
    public static final int TICKETS_PER_STRIP = 6;
    public static final int TOTAL_ROWS_PER_STRIP = 18;

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
