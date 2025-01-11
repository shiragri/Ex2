package assignments.ex2;
// Add your documentation below:

/**
 * help object to control cels
 */
public class CellEntry  implements Index2D {
    private String data;
    private int x;
    private int y;

    /**
     * constructor get cell name e.g A0, B12
     * @param cords
     */
    public CellEntry(String cords) {
        this.data = cords ;
        if(cords == null || cords.isEmpty()) {
            this.x=-1;
            this.y=-1;
        }
        else {
            this.x = xCell(cords);
            this.y = yCell(cords);
        }
    }

    /**
     * check if the cell cords are valid
     * @return
     */
    @Override
    public boolean isValid() {
        if (x==-1 || y==-1)
            return false;
        return true;
    }

    /**
     * return the x data as number e.g. A is 0
     * @return
     */
    @Override
    public int getX() {return x;}

    /**
     * return the y data of the cell
     * @return
     */
    @Override
    public int getY() {return y;}

    /**
     * calculate the x value of the cell e.g. A is 0
     * @param x
     * @return
     */
    private int xCell(String x) {
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

    /**
     * calculate the y data of the cell
     * @param x
     * @return
     */
    private int yCell(String x) {
        int a;
        try {
            a = Integer.parseInt(x.substring(1));
        } catch (NumberFormatException e) {
            return -1;
        }

        return a;
    }
}
