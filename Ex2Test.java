
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;




public class Ex2Test {
    /*   @Test
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
        String s = "=1+3";
        Cell c = new Cell("c");
        double a;

         a = c.computeForm(s);
        assertEquals(a, 4);

        s = "=11+3*3";
        a= c.computeForm(s);
        assertEquals(a,20);

        s = "=11+3*3-20";
        a= c.computeForm(s);
        assertEquals(a,0);

        s = "=11+3/3-10";
        a= c.computeForm(s);
        assertEquals(a,2);

        s = "=(11+3/3)-10";
        a= c.computeForm(s);
        assertEquals(a,2);

        s = "=((11+4)/3)-10";
        a= c.computeForm(s);
        assertEquals(a,-5);

        s = "=((11+4)/3-10)";
        a= c.computeForm(s);
        assertEquals(a,-5);

        s = "=((11*3)/3)*10";
        a= c.computeForm(s);
        assertEquals(a,110);





    }

}

