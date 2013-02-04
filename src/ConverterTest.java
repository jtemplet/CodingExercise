import org.junit.Test;
import static org.junit.Assert.*;

public class ConverterTest extends Converter{


    @Test
    public void testGoodStuff() {
        Converter converter = new Converter();

        assertEquals("One dollars", converter.convert("1"));

        assertEquals("Ten dollars", converter.convert("10"));

        assertEquals("Fourteen dollars", converter.convert("14"));

        assertEquals("One hundred dollars", converter.convert("100"));

        assertEquals("One hundred ten dollars", converter.convert("110"));

        assertEquals("Five hundred one dollars", converter.convert("501"));

        assertEquals("Six thousand nine dollars", converter.convert("6009"));

        assertEquals("Six hundred ninety-nine dollars", converter.convert("699"));

        assertEquals("One thousand eight hundred ninety-nine and 98/100 dollars", converter.convert("1899.98"));

        assertEquals("Six hundred ninety-nine dollars", converter.convert("          699         "));

        assertEquals("Ten thousand twenty-two dollars", converter.convert("10022"));

        assertEquals("Sixty thousand nine dollars", converter.convert("60009"));

        assertEquals("Six hundred thousand nine dollars", converter.convert("600009"));

        assertEquals("Six hundred twenty thousand nine dollars", converter.convert("620009"));

        assertEquals("Six hundred thousand one hundred nine dollars", converter.convert("600109"));

        assertEquals("Six hundred seven thousand one hundred nine dollars", converter.convert("607109"));

        assertEquals("Six hundred sixty-seven thousand one hundred nine dollars", converter.convert("667109"));

        assertEquals("Six million nine dollars", converter.convert("6000009"));

        assertEquals("One million twenty-six dollars", converter.convert("1000026"));

    }

    @Test (expected = IllegalArgumentException.class)
    public void testBadStuff0(){
        Converter converter = new Converter();
        converter.convert("0501");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testBadStuff1(){
        Converter converter = new Converter();
        converter.convert(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testBadStuff2(){
        Converter converter = new Converter();
        converter.convert("89--0");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testBadStuff3(){
        Converter converter = new Converter();
        converter.convert("890.");
    }

    @Test (expected = IllegalArgumentException.class)
        public void testBadStuff4(){
            Converter converter = new Converter();
            converter.convert("890.0");
        }

    @Test (expected = IllegalArgumentException.class)
    public void testBadStuff5(){
        Converter converter = new Converter();
        converter.convert("890.0000000000000001");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testBadStuff6(){
        Converter converter = new Converter();
        converter.convert("");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testBadStuff7(){
        Converter converter = new Converter();
        converter.convert(" 1 2 3 4 5");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testBadStuff8(){
        Converter converter = new Converter();
        converter.convert("15% ");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testBadStuff9(){
        Converter converter = new Converter();
        converter.convert("10.00.0");
    }

/*
    @Test
    public void testAll() throws InterruptedException{
        Converter converter = new Converter();
        for (int i = 100000009; i < 110000000; i++) {
            System.out.println(converter.convert("" + i));
            Thread.sleep(1000);
        }
    }
*/
}
