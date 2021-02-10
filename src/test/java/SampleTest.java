import org.junit.Assert;
import org.junit.Test;

public class SampleTest {
    @Test
    public void trueTest(){
        Assert.assertTrue(1 == 1);
    }
    @Test
    public void falseTest(){
        Assert.assertFalse(1 == 0);
    }
}
