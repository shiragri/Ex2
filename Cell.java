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
        int opIndex = indOfMainOp(form);
        String leftSide = form.substring(0, opIndex);
        String rightSide = form.substring(opIndex+1);
        char op = form.charAt(opIndex);

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
    private int indOfMainOp(String form){
        for (int i = form.length(); i > 0; i--) {
            if (form.charAt(i-1) == '+' || form.charAt(i-1) == '-');
        }
        return 4;
    }
}
