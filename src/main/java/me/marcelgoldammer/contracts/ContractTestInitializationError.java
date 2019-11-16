package me.marcelgoldammer.contracts;

import org.junit.runners.model.InitializationError;

public class ContractTestInitializationError extends InitializationError {

  public ContractTestInitializationError(String message) {
    super(message);
  }

}
