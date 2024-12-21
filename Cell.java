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

    public boolean isNumber(){
       try {
           int a =Integer.parseInt(value);
           return true;
       }
       catch (NumberFormatException e){
           return false;
       }
    }

    public boolean isText(){
        return false;
    }

    public boolean isForm(){
        return value.charAt(0) == '0';
    }

    public Double computeForm(String form){
        return null;
    }
}
