import com.devfoFikiCar.main;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

public class for_test {
    @Rule
    public final SystemOutRule systemOut = new SystemOutRule().enableLog();

    @Test
    public void classicGoto() {
        main m = new main();
        main.runParser("tests/for/for_test_1.fclang");
        Assert.assertEquals("======================================\nBeginning of FCLang execution: \n" +
                "======================================\n0\n1\n2\n3\n4\n======================================\n" +
                "Successful execution.\n======================================\n", systemOut.getLog());
    }

    @Test
    public void ifGoto() {
        main m = new main();
        main.runParser("tests/for/for_test_2.fclang");
        Assert.assertEquals("======================================\nBeginning of FCLang execution: \n" +
                "======================================\n5\n4\n3\n2\n1\n======================================\n" +
                "Successful execution.\n======================================\n", systemOut.getLog());
    }

    @Test
    public void forGoto() {
        main m = new main();
        main.runParser("tests/for/for_test_3.fclang");
        Assert.assertEquals("======================================\nBeginning of FCLang execution: \n" +
                "======================================\n60\n======================================\n" +
                "Successful execution.\n======================================\n", systemOut.getLog());
    }
}