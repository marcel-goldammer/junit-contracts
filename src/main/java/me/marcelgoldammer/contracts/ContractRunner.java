package me.marcelgoldammer.contracts;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.junit.runner.Description;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

public class ContractRunner extends BlockJUnit4ClassRunner {

  private final Class<?> implementation;
  private final Map<FrameworkMethod, Description> methodDescriptions = new ConcurrentHashMap<>();

  ContractRunner(Class<?> testClass, Class<?> implementation) throws InitializationError {
    super(testClass);
    this.implementation = implementation;
  }

  @Override
  public Description getDescription() {
    Description description =
        Description.createSuiteDescription(implementation.getName(), getRunnerAnnotations());
    getChildren().stream().map(this::describeChild).forEach(description::addChild);
    return description;
  }

  @Override
  protected Description describeChild(FrameworkMethod method) {
    Description description = methodDescriptions.get(method);

    if (description == null) {
      description =
          Description.createTestDescription(
              getTestClass().getName(),
              method.getName(),
              formatDisplayName(implementation, method));
      methodDescriptions.putIfAbsent(method, description);
    }

    return description;
  }

  private String formatDisplayName(Class<?> clazz, FrameworkMethod method) {
    return String.format("%s(%s)", clazz.getName(), method.getName());
  }

  @Override
  protected Object createTest() throws Exception {
    Class<?> contract = getTestClass().getAnnotation(Contract.class).value();
    if (!contract.isAssignableFrom(implementation)) {
      throw new ContractTestInitializationError(
          "Implementation '"
              + implementation.getName()
              + "' does not implement the interface '"
              + contract.getName()
              + "'");
    }
    return getTestClass()
        .getOnlyConstructor()
        .newInstance(implementation.getDeclaredConstructor().newInstance());
  }

  @Override
  protected void validateConstructor(List<Throwable> errors) {
    validateExactlyOneConstructor(errors);
    validateOneArgConstructor(errors);
  }

  private void validateExactlyOneConstructor(List<Throwable> errors) {
    if (!hasOneConstructor()) {
      errors.add(
          new ContractTestValidationException(
              "Test class must have exactly one public constructor"));
    }
  }

  private void validateOneArgConstructor(List<Throwable> errors) {
    if (!getTestClass().isANonStaticInnerClass()
        && hasOneConstructor()
        && getTestClass().getOnlyConstructor().getParameterTypes().length != 1) {
      errors.add(
          new ContractTestValidationException(
              "Test class must have exactly one public one-argument constructor"));
    }
  }

  private boolean hasOneConstructor() {
    return getTestClass().getJavaClass().getConstructors().length == 1;
  }
}
