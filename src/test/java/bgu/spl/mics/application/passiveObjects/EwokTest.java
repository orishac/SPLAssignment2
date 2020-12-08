package bgu.spl.mics.application.passiveObjects;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EwokTest {

    private Ewok test;

    @BeforeEach
    public void setUp()  {
        test = new Ewok(0);
    }

    @Test
    public void acquire() {
        test.available = true;
        test.acquire();
        assertFalse(test.available);
    }

    @Test
    public void release() {
        test.available=false;
        test.release();
        assertTrue(test.available);
    }
}