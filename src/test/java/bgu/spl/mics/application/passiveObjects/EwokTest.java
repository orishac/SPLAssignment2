package bgu.spl.mics.application.passiveObjects;

import org.junit.Assert;

import static org.junit.Assert.*;

public class EwokTest {

    private Ewok test;

    @org.junit.Before
    public void setUp() throws Exception {
        test = new Ewok();
    }

    @org.junit.Test
    public void acquire() {
        test.available = true;
        test.acquire();
        Assert.assertFalse(test.available);
    }

    @org.junit.Test
    public void release() {
        test.available=false;
        test.release();
        Assert.assertTrue(test.available);
    }
}