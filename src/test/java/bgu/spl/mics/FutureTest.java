package bgu.spl.mics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;


import static org.junit.jupiter.api.Assertions.*;


public class FutureTest {

    // We made a few changes in those tests compared to the tests that we submitted.
    private Future<String> future;

    @BeforeEach
    public void setUp(){
        future = new Future<>();
    }

    @Test
    public void testResolve(){
        String str = "someResult";
        future.resolve(str);
        assertTrue(future.isDone());
        assertEquals(str, future.get());
    }

    @Test
    public void testGet1() {
        String str = "someResult";
        future.resolve(str);
        assertEquals("someResult", future.get());
    }

    @Test
    public void testIsDone() {
        String str = "someResult";
        future.resolve(str);
        assertTrue(future.isDone());
    }

    @Test
    public void testGet2() {
        String str = "someResult";
        future.resolve(str);
        assertEquals("someResult", future.get(1, TimeUnit.SECONDS));
    }
}
