package assignments.ex2;

public class Cell1 {
    private int x;
    private int y;
    private int intVal;
    private String strVal;
    private String formVal;
    private String value;
    private Spreadsheet excell;

    public Cell1( String str) {
        this.value = str;
      //  this.excell =ex;

    }



    private boolean isNumber(String str) {

        try {
            int a =Integer.parseInt(str);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }


    }

    public boolean isNumber() {

        return isNumber(value);

    }

    public boolean isText(){
        return (!isNumber() && !isForm());

    }

    public boolean isForm(){
        return value.charAt(0) == '0';
    }

    private boolean isCell(String str) {
        if(excell.xCell(str)==-1 || excell.yCell(str)==-1 ){
            return false;
        }
        return true;
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
