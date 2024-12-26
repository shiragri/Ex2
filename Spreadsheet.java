public class Spreadsheet {

    Cell[][] excell;

    public Spreadsheet(int x, int y) {
        excell = new Cell[x][y];


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
            return ch - 141;
        } else return -1;


    }

    public int yCell(String x) {

        try {
            int a = Integer.parseInt(x.substring(1));
        } catch (NumberFormatException e) {
            return -1;
        }

        return Integer.parseInt(x.substring(1, x.length() - 1));
    }



}
