package assignments.ex2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;




public class Ex2Test {
    /*   @Test

     this is private method and can be tested only when we change the method to public
    void indOfMainOp() {
          String a = "11+3";
          Cell c = new Cell("c");
          int s = c.indOfMainOp(a);
          assertEquals(s, 2);

          String a1 = "11+3*3";
          int s1 = c.indOfMainOp(a1);
          assertEquals(s1, 2);

          String a2 = "11+3-9";
          int s2 = c.indOfMainOp(a2);
          assertEquals(s2, 4);

          String a3 = "11+(3-9)*3";
          int s3 = c.indOfMainOp(a3);
          assertEquals(s3, 2);

          String a4 = "11+((2*3-1)-9)*3";
          int s4 = c.indOfMainOp(a4);
          assertEquals(s4, 2);

          a4 = "11+((2*3-1)-9)-3";
          s4 = c.indOfMainOp(a4);
          assertEquals(s4, 14);


      }
  */
    @Test
    void computeForm() {
        Ex2Sheet sheet = new Ex2Sheet();
        String s = "=1+3";
        sheet.set(0,0,s);
        sheet.eval();
        String a;

        a = sheet.value(0,0);
        assertEquals(a, "4.0");

        s = "=11+3*3";
        sheet.set(0,0,s);
        sheet.eval();
        a = sheet.value(0,0);
        assertEquals(a,"20.0");

        s = "=11+3*3-20";
        sheet.set(0,0,s);
        sheet.eval();
        a = sheet.value(0,0);
        assertEquals(a,"0.0");

        s = "=11+3/3-10";
        sheet.set(0,0,s);
        sheet.eval();
        a = sheet.value(0,0);
        assertEquals(a,"2.0");

        s = "=(11+3/3)-10";
        sheet.set(0,0,s);
        sheet.eval();
        a = sheet.value(0,0);
        assertEquals(a,"2.0");

        s = "=((11+4)/3)-10";
        sheet.set(0,0,s);
        sheet.eval();
        a = sheet.value(0,0);
        assertEquals(a,"-5.0");

        s = "=((11+4)/3-10)";
        sheet.set(0,0,s);
        sheet.eval();
        a = sheet.value(0,0);
        assertEquals(a,"-5.0");

        s = "=((11*3)/3)*10";
        sheet.set(0,0,s);
        sheet.eval();
        a = sheet.value(0,0);
        assertEquals(a,"110.0");


    }

    @Test
    void width() {
        Ex2Sheet s = new Ex2Sheet(3, 4);
        int x = s.width();
        assertEquals(x, 3);
    }

    @Test
    void height()  {
        Ex2Sheet s = new Ex2Sheet(3, 4);
        int y = s.height();
        assertEquals(y, 4);


    }

    @Test
    void getX(){
        String t= "A12";
        CellEntry ce = new CellEntry(t);

        int a = ce.getX();
        assertEquals(a, 0);

        t= "j2";
        ce = new CellEntry(t);
        a = ce.getX();
        assertEquals(a, 9);

        t= "aa2";
        ce = new CellEntry(t);
        a = ce.getX();
        assertEquals(a, -1);

        t= "a2a";
        ce = new CellEntry(t);
        a = ce.getX();
        assertEquals(a, -1);
    }

    @Test
    void getY(){


        String t= "A12";
        CellEntry ce = new CellEntry(t);

        int a = ce.getY();
        assertEquals(a, 12);

        t= "j2";
        ce = new CellEntry(t);
        a = ce.getY();
        assertEquals(a, 2);

        t= "aa2";
        ce = new CellEntry(t);
        a = ce.getY();
        assertEquals(a, -1);

        t= "a2a";
        ce = new CellEntry(t);
        a = ce.getY();
        assertEquals(a, -1);
    }



    @Test
    public void testSetAndGetCellData() {
        Ex2Sheet sheet = new Ex2Sheet(5, 5);
        sheet.set(0, 0, "5");
        sheet.set(1, 1, "=A1+10");
        sheet.set(2, 2, "Hello");

        assertEquals("5", sheet.get(0, 0).getData());
        assertEquals("=A1+10", sheet.get(1, 1).getData());
        assertEquals("Hello", sheet.get(2, 2).getData());
    }

    @Test
    public void testCellTypes() {
        Ex2Sheet sheet = new Ex2Sheet(5, 5);
        sheet.set(0, 0, "5");
        sheet.set(1, 1, "=A1+10");
        sheet.set(2, 2, "Hello");

        assertEquals(Ex2Utils.NUMBER, sheet.get(0, 0).getType());
        assertEquals(Ex2Utils.FORM, sheet.get(1, 1).getType());
        assertEquals(Ex2Utils.TEXT, sheet.get(2, 2).getType());
    }

    @Test
    public void testValueCalculation() {
        Ex2Sheet sheet = new Ex2Sheet(5, 5);
        sheet.set(0, 0, "5");
        sheet.set(1, 1, "=A0+10");
        sheet.eval();

        assertEquals("5.0", sheet.value(0, 0));
        assertEquals("15.0", sheet.value(1, 1));
    }

    @Test
    public void testCircularDependency() {
        Ex2Sheet sheet = new Ex2Sheet(5, 5);
        sheet.set(0, 0, "=B0");
        sheet.set(1, 0, "=A0");
        sheet.eval();

        assertEquals(Ex2Utils.ERR_CYCLE, sheet.value(0, 0));
        assertEquals(Ex2Utils.ERR_CYCLE, sheet.value(1, 0));
    }

    @Test
    public void testInvalidFormula() {
        Ex2Sheet sheet = new Ex2Sheet(5, 5);
        sheet.set(0, 0, "=5+");
        sheet.eval();

        assertEquals(Ex2Utils.ERR_FORM, sheet.value(0, 0));
    }

    @Test
    public void testEmptyCells() {
        Ex2Sheet sheet = new Ex2Sheet(5, 5);
        assertEquals("", sheet.value(0, 0));
    }

    @Test
    public void testTextCells() {
        Ex2Sheet sheet = new Ex2Sheet(5, 5);
        sheet.set(0, 0, "Hello World");
        assertEquals("Hello World", sheet.value(0, 0));
    }

    @Test
    public void testSaveAndLoad() throws Exception {
        Ex2Sheet sheet = new Ex2Sheet(5, 5);
        sheet.set(0, 0, "5");
        sheet.set(1, 1, "=A1+10");
        sheet.set(2, 2, "Hello");

        sheet.save("test_sheet.csv");

        Ex2Sheet loadedSheet = new Ex2Sheet(5, 5);
        loadedSheet.load("test_sheet.csv");

        assertEquals("5", loadedSheet.get(0, 0).getData());
        assertEquals("=A1+10", loadedSheet.get(1, 1).getData());
        assertEquals("Hello", loadedSheet.get(2, 2).getData());
    }

    @Test
    public void testComplexFormula() {
        Ex2Sheet sheet = new Ex2Sheet(5, 5);
        sheet.set(0, 0, "5");
        sheet.set(1, 1, "10");
        sheet.set(2, 2, "=A0+B1*2");
        sheet.eval();

        assertEquals("25.0", sheet.value(2, 2));
    }


    @Test
    public void testOutOfBoundsCellReference() {
        Ex2Sheet sheet = new Ex2Sheet();
        sheet.set(Ex2Utils.WIDTH, Ex2Utils.HEIGHT, "=A19"); // תא מחוץ לטווח
        String result = sheet.value(Ex2Utils.WIDTH, Ex2Utils.HEIGHT);
        assertEquals(Ex2Utils.ERR_FORM, result); // או שגיאה אחרת
    }

    @Test
    public void testCircularReference() {
        Ex2Sheet sheet = new Ex2Sheet();
        sheet.set(0, 0, "=b0"); // תא A1 מפנה לעצמו
        sheet.set(1, 0, "=A0"); // התא A0 מפנה לתא A1 (הפניה מעגלית)
        sheet.eval();
        String result = sheet.value(0, 0);
        assertEquals(Ex2Utils.ERR_CYCLE, result); // שגיאה של הפניה מעגלית
    }

    @Test
    public void testInvalidFormulaSyntax() {
        Ex2Sheet sheet = new Ex2Sheet();
        sheet.set(0, 0, "=+A1"); // נוסחא עם סימן חיובי לא חוקי
        sheet.eval();
        String result = sheet.value(0, 0);
        assertEquals(Ex2Utils.ERR_FORM, result); // שגיאה תואמת
    }

    @Test
    public void testFormulaWithTextCell() {
        Ex2Sheet sheet = new Ex2Sheet();
        sheet.set(0, 0, "=B1");
        sheet.set(1, 1, "Hello World"); // תא B1 מכיל טקסט
        sheet.eval();
        String result = sheet.value(0, 0);
        assertEquals(Ex2Utils.ERR_FORM, result); // שגיאה בתוצאה
    }

    @Test
    public void testFormulaWithTooManyParentheses() {
        Ex2Sheet sheet = new Ex2Sheet();
        sheet.set(0, 0, "=(A1))");
        sheet.eval();
        String result = sheet.value(0, 0);
        assertEquals(Ex2Utils.ERR_FORM, result); // שגיאה עקב סוגריים לא מאוזנים
    }

    @Test
    public void testValidFormula() {
        Ex2Sheet sheet = new Ex2Sheet();
        sheet.set(0, 0, "=A1 + A2");
        sheet.set(0, 1, "2");
        sheet.set(0, 2, "3");
        sheet.eval();
        String result = sheet.value(0, 0);
        assertEquals("5.0", result); // תוצאה נכונה
    }
}








