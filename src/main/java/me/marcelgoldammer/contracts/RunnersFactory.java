package me.marcelgoldammer.contracts;

import com.google.common.reflect.ClassPath;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.runner.Runner;
import org.junit.runners.model.InitializationError;

class RunnersFactory {

  private Class<?> testClass;
  private Contract annotation;

  public RunnersFactory(Class<?> testClass) {
    Objects.requireNonNull(testClass);
    this.testClass = testClass;
    annotation = testClass.getAnnotation(Contract.class);
    Objects.requireNonNull(annotation);
  }

  public List<Runner> createRunners() throws IOException, InitializationError {
    if (!annotation.value().isInterface()) {
      throw new ContractTestInitializationError("The value of @Contract must be an interface");
    }

    List<Runner> runners = new ArrayList<>();
    for (Class<?> clazz : findClassesImplementingContract()) {
      runners.add(new ContractRunner(testClass, clazz));
    }
    return runners;
  }

  private Set<Class<?>> findClassesImplementingContract() throws IOException {
    return ClassPath.from(Thread.currentThread().getContextClassLoader()).getAllClasses().stream()
        .map(this::loadClass)
        .filter(Objects::nonNull)
        .filter(this::isNotAnInterface)
        .filter(this::classIsImplementingContract)
        .collect(Collectors.toSet());
  }

  private Class<?> loadClass(ClassPath.ClassInfo classInfo) {
    try {
      return classInfo.load();
    } catch (LinkageError e) {
      // ignore exception
      return null;
    }
  }

  private boolean isNotAnInterface(Class<?> clazz) {
    return !clazz.isInterface();
  }

  private boolean classIsImplementingContract(Class<?> clazz) {
    return annotation.value().isAssignableFrom(clazz);
  }
}
