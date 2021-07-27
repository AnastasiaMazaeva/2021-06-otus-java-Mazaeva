package testing;

import annotations.After;
import annotations.Before;
import annotations.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TestClass {

    private static final Logger log = LoggerFactory.getLogger(TestClass.class);

    private final SomeTestClass someTestClass = new SomeTestClass();

    @Before
    public void setUp() {
        log.info("Setting up once, test instance value {}", someTestClass.getValue());
        someTestClass.setValue(11);
        log.info("Setting up done, test instance value {}", someTestClass.getValue());
    }

    @Before
    public void setUpWithRuntimeException() {
        log.info("Gonna die in setup");
        throw new RuntimeException("Oh no");
    }

    @After
    public void tearDown() {
        log.info("Tearing down now, test instance value {}", someTestClass.getValue());
        someTestClass.setValue(14);
        log.info("Tearing down done, test instance value {}", someTestClass.getValue());
    }

    @Test
    public void test_first() {
        log.info("First test began, test instance value {}", someTestClass.getValue());
        someTestClass.setValue(124);
        log.info("First test ended, test instance value {}", someTestClass.getValue());
    }

    @Test
    public void test_exception() {
        log.info("It seems we are gonna die here");
        throw new RuntimeException("You see, nothing wrong is going on here");
    }

    @Test
    public void test_second() {
        log.info("Second test began, test instance value {}", someTestClass.getValue());
        someTestClass.setValue(348);
        log.info("Second test ended, test instance value {}", someTestClass.getValue());
    }
}
