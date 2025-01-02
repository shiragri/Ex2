package assignments.ex2;

import java.io.IOException;
// Add your documentation below:

public class Ex2Sheet implements Sheet {
    private Cell[][] table;
    private double [][] values;
    // Add your code here

    // ///////////////////
    public Ex2Sheet(int x, int y) {
        table = new SCell[x][y];
        for(int i=0;i<x;i=i+1) {
            for(int j=0;j<y;j=j+1) {
                table[i][j] = new SCell(Ex2Utils.EMPTY_CELL);
            }
        }
        eval();
    }

    public Ex2Sheet() {
        this(Ex2Utils.WIDTH, Ex2Utils.HEIGHT);
    }

    @Override
    public String value(int x, int y) {
        String ans = Ex2Utils.EMPTY_CELL;
        // Add your code here

        Cell c = get(x,y);
        if(c!=null) {ans = c.toString();}

        /////////////////////
        return ans;
    }

    @Override
    public Cell get(int x, int y) {
        return table[x][y];
    }

    @Override
    public Cell get(String cords) {
        Cell ans = null;
        int x = xCell(cords);
        int y = yCell(cords);
        ans=get(x,y);

        return ans;
    }

    @Override
    public int width() {
        return table.length;
    }
    @Override
    public int height() {
        return table[0].length;
    }
    @Override
    public void set(int x, int y, String s) {
        Cell c = new SCell(s);
        table[x][y] = c;
        // Add your code here

        /////////////////////
    }
    @Override
    public void eval() {
        int[][] dd = depth();

        // Add your code here

        values = new double[width()][height()];

        for (int x = 0; x < width(); x++) {
            for (int y = 0; y < height(); y++) {
                String ans = null;
                if(get(x,y)!=null) {
                    ans = get(x,y).toString();}
                // Add your code here
                Cell c =get(x,y);
                int type=c.getType();
                if (type== Ex2Utils.FORM)
                    return values[x][y]=computeForm(ans);

               // else if (type==Ex2Utils.TEXT)
                //    return ans;
               // else return Double.toString(computeForm(ans));

                values[x][y] = ans;

            }
        }
        // ///////////////////
    }

    @Override
    public boolean isIn(int xx, int yy) {
        boolean ans = xx>=0 && yy>=0;
        // Add your code here

        /////////////////////
        return ans;
    }

    @Override
    public int[][] depth() {
        int[][] ans = new int[width()][height()];
        // Add your code here

        // ///////////////////
        return ans;
    }

    @Override
    public void load(String fileName) throws IOException {
        // Add your code here

        /////////////////////
    }

    @Override
    public void save(String fileName) throws IOException {
        // Add your code here

        /////////////////////
    }

    @Override
    public String eval(int x, int y) {


        /////////////////////
      //  return ans;
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

    public boolean isNumber(String str) {

        try {
            int a =Integer.parseInt(str);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
    }

    public double computeForm(String form){
        String form1 = "";
        if (form.charAt(0)== '=')
            form1 = form.substring(1);
        else
            form1 = form;
        if (form1.charAt(0) == '(' && form1.charAt(form1.length()-1) == ')')
            form1 = form1.substring(1, form1.length()-1);

        int opIndex = indOfMainOp(form1);
        String leftSide = form1.substring(0, opIndex);
        String rightSide = form1.substring(opIndex+1);
        char op = form1.charAt(opIndex);

        double leftSideVal;
        if (isNumber(leftSide))
            leftSideVal = Double.parseDouble(leftSide);
        /*else if (isCell(leftSide)) {
           int x= excell.xCell(str);
           int y= excell.yCell(str);
           Cell cell =excell.get(x,y);
            leftSideVal= cell.computeForm("3+5");

        }
        */
        else
            leftSideVal = computeForm (leftSide);

        double rightSideVal;
        if (isNumber(rightSide))
            rightSideVal = Double.parseDouble(rightSide);
        else
            rightSideVal = computeForm (rightSide);

        double retVal=-1;
        if (op == '+')
            retVal= leftSideVal + rightSideVal;
        else if (op == '-')
            retVal= leftSideVal - rightSideVal;
        else if (op == '*')
            retVal= leftSideVal * rightSideVal;
        else if (op == '/')
            retVal= leftSideVal / rightSideVal;


        return retVal;


        // return null;
    }

    private  int indOfMainOp(String form){
        int opIndex = -1;
        boolean isInParenthesis = false;
        int parenthesisCounter = 0;
        for (int i = form.length()-1; i > 0; i--) {
            if (form.charAt(i) == ')') {
                isInParenthesis = true;
                parenthesisCounter++;
            }
            else if (form.charAt(i) == '(') {
                parenthesisCounter--;
                if (parenthesisCounter == 0)
                    isInParenthesis = false;

            }


            if (!isInParenthesis) {
                if (form.charAt(i) == '+' || form.charAt(i) == '-')
                    return i;
                else if ((form.charAt(i) == '*' || form.charAt(i) == '/') && opIndex == -1)
                    opIndex = i;
            }
        }
        return opIndex;
    }
}
