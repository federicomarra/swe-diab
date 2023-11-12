package test;

import org.junit.Assert;
import org.junit.Test;

public class ExampleTest {
    public static int square(int n) {
        return n * n;
    }

    @Test
    public void squareTest() {
        Assert.assertEquals(100, square(10));
        Assert.assertEquals(36, square(6));
        Assert.assertEquals(25, square(5));
        Assert.assertEquals(16, square(4));
        Assert.assertEquals(9, square(3));
        Assert.assertEquals(4, square(2));
        Assert.assertEquals(1, square(1));
        Assert.assertEquals(0, square(0));
    }
}