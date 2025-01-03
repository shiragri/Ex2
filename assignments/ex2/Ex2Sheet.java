package assignments.ex2;

import java.io.IOException;
// Add your documentation below:

public class Ex2Sheet implements Sheet {
    private Cell[][] table;
    private Double [][] values;
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
        if(c!=null) {
            ans = c.toString();

            int type=c.getType();
            if (type==Ex2Utils.TEXT)
                return ans;
            else
                return values[x][y].toString();

            // else if (type==Ex2Utils.TEXT)
            //    return ans;
            // else return Double.toString(computeForm(ans));
        }


        /////////////////////
        return ans;
    }

    @Override
    public Cell get(int x, int y) {
        return table[x][y];
    }

    @Override
    public Cell get(String cords) {
        CellEntry ce = new CellEntry(cords);
        int x = ce.getX();
        int y = ce.getY();
        if (ce.isValid()) {
            Cell c = get(x, y);
            return c;
        }
        else
            return null;
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

        int maxDepth = 0;

        //find the max depth value
        for (int i=0;i<width();i++) {
            for (int j=0;j<height();j++) {
                if (dd[i][j] > maxDepth) {
                    maxDepth = dd[i][j];
                }
            }
        }
        // Add your code here

        values = new Double[width()][height()];

        for(int d=0;d<maxDepth+1;d++) {
            for (int x = 0; x < width(); x++) {
                for (int y = 0; y < height(); y++) {
                    //calculate only cells within the current depth
                    if (dd[x][y] == d) {
                        String ans = null;
                        Cell c = get(x, y);
                        if (c != null) {
                            ans = c.toString();
                        }
                        int type = c.getType();
                        if (type == Ex2Utils.NUMBER)
                            values[x][y] = Double.parseDouble(ans);
                        else if (type == Ex2Utils.FORM)
                            values[x][y] = computeForm(ans);

                    }
                }
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
        for (int x = 0; x < width(); x++) {
            for (int y = 0; y < height(); y++) {
                ans [x][y]=-1;
            }
        }

        int depth = 0;
        int counter = 0;
        int max = width()*height();

        boolean flagC = true;
        while(counter <max && flagC) {
            flagC = false;
            for(int x = 0;x<width();x++) {
                for(int y = 0;y<height();y++) {
                    if (ans[x][y] ==-1) {
                        if (canBeCompudedNow(x, y,ans)) { // DIY
                            ans[x][y] = depth;
                            counter += 1;
                            flagC = true;
                        } // if
                    }
                } // for yt
            }  // for x
            depth+=1;
        }// while


        // Add your code here

        // ///////////////////
        return ans;
    }

    private boolean canBeCompudedNow(int x, int y, int[][] ans) {
        Cell c = get(x,y);
        if (c !=null){
            if (c.getType()==Ex2Utils.TEXT)
                return true;
            else if (c.getType()==Ex2Utils.NUMBER) {
                return true;
            }
            else{
               String val = c.toString().toLowerCase();
               //get all the tokens withing the cell content
               String[] tokens = val.split("[-+*/=()]");
               for (String token : tokens) {
                   if (!token.isEmpty()) {
                       if (token.charAt(0) > 'a' && token.charAt(0) < 'z') {
                           CellEntry ce = new CellEntry(token);
                           int x1 = ce.getX();
                           int y1 = ce.getY();
                           if (ce.isValid()) {
                               //this cell has dependency that has not been able to calculate yet
                               if (ans[x1][y1] == -1) {
                                   return false;
                               }
                           }

                       }
                   }
               }
               //all the tokens can be calculate
               return true;



            }
        }
        else
            return true;
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
        return "SSSSS";
        /////////////////////
        //  return ans;
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
        //if there is no two sides but only one token
        if (opIndex == -1){
            CellEntry ce = new CellEntry(form1);
            if (isNumber(form1))
                return Double.parseDouble(form1);
            else if (ce.isValid())
                return values[ce.getX()][ce.getY()];
        }

        String leftSide = form1.substring(0, opIndex);
        String rightSide = form1.substring(opIndex+1);
        char op = form1.charAt(opIndex);

        double leftSideVal;
        CellEntry lce = new CellEntry(leftSide);
        if (isNumber(leftSide))
            leftSideVal = Double.parseDouble(leftSide);
        else if (lce.isValid())
            leftSideVal = values[lce.getX()][lce.getY()];
        else
            leftSideVal = computeForm (leftSide);

        double rightSideVal;

        CellEntry rce = new CellEntry(leftSide);
        if (isNumber(rightSide))
            rightSideVal = Double.parseDouble(rightSide);
        else if (rce.isValid())
            rightSideVal = values[rce.getX()][rce.getY()];
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
