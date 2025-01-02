package assignments.ex2;
// Add your documentation below:

public class CellEntry  implements Index2D {
    private String data;
    private int x;
    private int y;

    public CellEntry(String cords) {
        this.data = cords;
        this.x = xCell(cords);
        this.y = yCell(cords);
    }

    @Override
    public boolean isValid() {
        if (x==-1 || y==-1)
            return false;
        return true;
    }

    @Override
    public int getX() {return x;}

    @Override
    public int getY() {return y;}

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
}
