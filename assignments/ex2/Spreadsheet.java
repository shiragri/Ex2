package assignments.ex2;

public class Spreadsheet {

    Cell1[][] excell;

    public Spreadsheet(int x, int y) {
        excell = new Cell1[x][y];


    }

    public int width() {
        return excell.length;
    }

    public int height() {
        return excell[0].length;
    }

    public int xCell(String x) {
        String lower = x.toLowerCase();
        try {
            int a = Integer.parseInt(lower.substring(1));
        } catch (NumberFormatException e) {
            return -1;
        }

        char ch = lower.charAt(0);
        if (ch >= 'a' && ch <= 'z') {
            return ch - 97;
        } else return -1;


    }

    public int yCell(String x) {
    int a;
        try {
            a = Integer.parseInt(x.substring(1));
        } catch (NumberFormatException e) {
            return -1;
        }

        return a;
    }

    public Cell1 get(int x, int y) {
        return excell[x][y];
    }
    public void set(int x, int y, Cell1 cell1) {
        excell[x][y] = cell1;
    }
}
