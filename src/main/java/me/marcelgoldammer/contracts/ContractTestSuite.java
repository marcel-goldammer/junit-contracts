package me.marcelgoldammer.contracts;

import java.io.IOException;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;

public class ContractTestSuite extends Suite {

  public ContractTestSuite(Class<?> testClass) throws InitializationError, IOException {
    super(testClass, new RunnersFactory(testClass).createRunners());
  }
}
