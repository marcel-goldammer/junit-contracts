package me.marcelgoldammer.contracts;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(ContractTestSuite.class)
@Contract(Adder.class)
public class AdderTest {

  private Adder adder;

  public AdderTest(Adder adder) {
    this.adder = adder;
  }

  @Test
  public void test1() {
    assertEquals(4, adder.add(3, 1));
  }

  @Test
  public void test2() {
    assertEquals(adder.add(1, 3), adder.add(3, 1));
  }
}
