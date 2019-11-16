package me.marcelgoldammer.contracts;

import static org.junit.Assert.*;

import java.lang.reflect.Method;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

public class ContractRunnerTest {

  private ContractRunner runner;

  @Before
  public void before() throws InitializationError {
    runner = new ContractRunner(AdderTest.class, SimpleAdder.class);
  }

  @Test
  public void getDescription() {
    assertEquals("me.marcelgoldammer.contracts.SimpleAdder", runner.getDescription().toString());
    assertEquals(2, runner.getDescription().getChildren().size());
  }

  @Test
  public void describeChild() throws NoSuchMethodException {
    Description description = runner.describeChild(new FrameworkMethod(SimpleAdder.class.getMethod("add", int.class, int.class)));
    assertEquals("add(me.marcelgoldammer.contracts.AdderTest)", description.toString());
  }

  @Test
  public void createTest() throws Exception {
    Object obj = runner.createTest();
    assertTrue(obj instanceof  AdderTest);
  }

}
