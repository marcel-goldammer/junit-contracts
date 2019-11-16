# junit-contracts
This is a library to create JUnit tests for your contracts. It will automatically lookup every class implementing a certain interface and runs the tests on this class.

## Building from Source
Run the following commands to get a local copy of this repository:
```
git clone https://github.com/marcel-goldammer/junit-contracts
cd junit-contracts
gradlew clean assemble
```
junit-contracts can be tested by using the following command
```
gradlew clean test
```

## Usage
```java
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import me.marcelgoldammer.contracts.ContractTestSuite;
import me.marcelgoldammer.contracts.Contract;

@RunWith(ContractTestSuite.class)
@Contract(Adder.class) // Declare the tested contract
public class AdderTest {

  private Adder adder;

  public AdderTest(Adder adder) { // Test class must have exactly one public one-argument constructor 
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
```

## Bug Tracker
Have a bug ar a feature request? [Please open a new issue.](https://github.com/marcel-goldammer/junit-contracts/issues)