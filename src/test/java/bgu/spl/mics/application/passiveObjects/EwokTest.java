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
        test.release();
        test.acquire();
        assertFalse(test.isAvailable());
    }

    @Test
    public void release() {
        test.release();
        test.acquire();
        test.release();
        assertTrue(test.isAvailable());
    }
}