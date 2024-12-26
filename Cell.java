public class Cell {
    private int x;
    private int y;
    private int intVal;
    private String strVal;
    private String formVal;
    private String value;

    public Cell(String str) {
        this.value = str;

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
