package assignments.ex2;
// Add your documentation below:

public class SCell implements Cell {
    private String line;
    private int type;
    // Add your code here

    public SCell(String s) {
        // Add your code here
        setData(s);
    }

    @Override
    public int getOrder() {
        // Add your code here

        return 0;
        // ///////////////////
    }

    //@Override
    @Override
    public String toString() {
        return getData();
    }

    @Override
public void setData(String s) {
        // Add your code here
        line = s;
        if (isNumber(line)) {
            type = Ex2Utils.NUMBER;
        }
        else if (isForm(line)) {
            type = Ex2Utils.FORM;
        }
        else if (isText(line)) {
            type = Ex2Utils.TEXT;
        }
    }

    @Override
    public String getData() {
        return line;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public void setType(int t) {
        type = t;
    }

    @Override
    public void setOrder(int t) {
        // Add your code here

    }

    public boolean isNumber(String str) {

        try {
            int a =Integer.parseInt(str);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
    }

    public boolean isText(String str){
        return (!isNumber(str) && !isForm(str));

    }

    public boolean isForm(String str){
       if (str != null && str.length() > 0) {
           return str.charAt(0) == '=';
       }
       else return false;
    }


}
