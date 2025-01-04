package assignments.ex2;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
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
            else if (type==Ex2Utils.ERR_CYCLE_FORM) {
                return Ex2Utils.ERR_CYCLE;
            } else if (type == Ex2Utils.ERR_FORM_FORMAT) {
                return Ex2Utils.ERR_FORM;
            }
            return values[x][y].toString();

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


        for (int i=0;i<width();i++) {
            for (int j=0;j<height();j++) {
                //we recalculte the error cells
                if (get(i,j).getType() == Ex2Utils.ERR_CYCLE_FORM || get(i,j).getType() == Ex2Utils.ERR_FORM_FORMAT)
                    get(i,j).setType(Ex2Utils.FORM);
                //find the max depth value
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
                            try {
                                values[x][y] = computeForm(ans);
                            }
                            catch (Exception e) {
                                c.setType(Ex2Utils.ERR_FORM_FORMAT);
                            }

                    }
                }
            }
        }
        //sign the cells that cyclyc
        for (int i=0;i<width();i++) {
            for (int j=0;j<height();j++) {
                if (dd[i][j] == -1) {
                    //if the cell is -1 and it is not empty then there is a cyclic problem
                    if (!get(i,j).toString().isEmpty())
                        get(i,j).setType(Ex2Utils.ERR_CYCLE_FORM);
                }
            }
        }

        // ///////////////////
    }

    @Override
    public boolean isIn(int xx, int yy) {
        boolean ans = xx>=0 && yy>=0 && xx<width() && yy<height();
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
            if (c.getType()==Ex2Utils.TEXT )
             /*   //if the cell is empty return false
                if (c.toString().isEmpty())
                    return false;
                else*/
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
                       if (token.charAt(0) >= 'a' && token.charAt(0) <= 'z') {
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
        // first we clean the current table
        for (int x = 0; x < width(); x++) {
            for (int y = 0; y < height(); y++) {
                get(x,y).setData(Ex2Utils.EMPTY_CELL);
            }
        }
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false; // skip the first line
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length == 3) {
                    int x = Integer.parseInt(parts[0].trim());
                    int y = Integer.parseInt(parts[1].trim());
                    String value = parts[2].trim();
                    if (isIn(x,y) ){
                        get(x, y).setData(value);
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            throw e;
        }
        eval();
        /////////////////////
    }

    @Override
    public void save(String fileName) throws IOException {
        // Add your code here
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {

            bw.write("I2CS ArielU: SpreadSheet (Ex2) assignment ");
            bw.newLine();

            for (int x = 0; x < width(); x++) {
                for (int y = 0; y < height(); y++) {
                    if (!get(x, y).getData().isEmpty()) {
                        bw.write(x + "," + y + "," + get(x, y).getData());
                        bw.newLine();
                    }
                }
            }
        }
        catch (IOException e) {
             throw e;
        }

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
                return values[ce.getX()][ce.getY()].doubleValue();
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

        CellEntry rce = new CellEntry(rightSide);
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
