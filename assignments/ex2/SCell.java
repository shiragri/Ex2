package assignments.ex2;
// Add your documentation below:

/**
 * this class represent cell in spread sheet
 */
public class SCell implements Cell {
    private String line;
    private int type;
    // Add your code here

    /**
     * constructor that take string as value
     * @param s the value of the cell
     */
    public SCell(String s) {
        // Add your code here
        setData(s);
    }

    /**
     * deprecated
     * @return
     */
    @Override
    public int getOrder() {
        // Add your code here

        return 0;
        // ///////////////////
    }

    /**
     * get the text of the cell
     * @return
     */
    //@Override
    @Override
    public String toString() {
        return getData();
    }

    /**
     * set data in the given cell
     * @param s
     */
    @Override
    public void setData(String s) {
        // Add your code here
        line = s;
        if(s != null) {

            if (isNumber(line)) {
                line = s.replaceAll("\\s", "");
                type = Ex2Utils.NUMBER;
            } else if (isForm(line)) {
                line = s.replaceAll("\\s", "");
                type = Ex2Utils.FORM;
            } else if (isText(line)) {
                type = Ex2Utils.TEXT;
            }
        }
    }

    /**
     * return the data of the cell
     * @return
     */
    @Override
    public String getData() {
        return line;
    }

    /**
     * get th type of the cell
     * the cell available types are define in  Ex2Utils
     * @return
     */
    @Override
    public int getType() {
        return type;
    }

    /**
     * set the type of the cell
     * @param t an int type value as defines in Ex2Utils.
     */
    @Override
    public void setType(int t) {
        type = t;
    }

    /**
     * deprecated
     * @param t
     */
    @Override
    public void setOrder(int t) {
        // Add your code here

    }

    /**
     * check if the value is number
     * @param str
     * @return true if number
     */
    public boolean isNumber(String str) {

        try {
            int a =Integer.parseInt(str);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
    }

    /**
     * checl if the value os text
     * @param str
     * @return true if text
     */
    public boolean isText(String str){
        return (!isNumber(str) && !isForm(str));

    }

    /**
     * check if the cell value is fotrmula
     * @param str
     * @return true if it formula
     */
    public boolean isForm(String str){
       if (str != null && str.length() > 0) {
           return str.charAt(0) == '=';
       }
       else return false;
    }


}
