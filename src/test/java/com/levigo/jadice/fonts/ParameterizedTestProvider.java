package com.levigo.jadice.fonts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;

public class ParameterizedTestProvider implements TestTemplateInvocationContextProvider {

  private static class SingleParamTestContext implements TestTemplateInvocationContext {
    private final String resourceName;

    SingleParamTestContext(String resourceName) {
      this.resourceName = resourceName;
    }

    @Override
    public String getDisplayName(int invocationIndex) {
      return "Resource #" + invocationIndex + ": " + resourceName;
    }

    @Override
    public List<Extension> getAdditionalExtensions() {
      return Collections.singletonList(new TestExecutor(resourceName));
    }
  }

  private static class TestExecutor implements BeforeTestExecutionCallback, AfterTestExecutionCallback, TestExecutionExceptionHandler {

    private final String resourceName;

    TestExecutor(String resourceName) {
      this.resourceName = resourceName;
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
      AbstractFontResourceTest instance = (AbstractFontResourceTest) context.getRequiredTestInstance();
      instance.fontResourceName = resourceName;
      instance.initializeResource();
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
      AbstractFontResourceTest instance = (AbstractFontResourceTest) context.getRequiredTestInstance();
      instance.freeResource();
    }

    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
      throw throwable; // just go ahead and rethrow
    }
  }

  @Override
  public boolean supportsTestTemplate(ExtensionContext context) {
    return AbstractFontResourceTest.class.isAssignableFrom(context.getRequiredTestClass());
  }

  @Override
  public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {
    Class<?> testClass = context.getRequiredTestClass();

    try {
      AbstractFontResourceTest dummy = (AbstractFontResourceTest) testClass.getDeclaredConstructor().newInstance();
      Iterable<String> parameters = dummy.fontResourceNames();

      List<TestTemplateInvocationContext> contexts = new ArrayList<>();
      for (String param : parameters) {
        contexts.add(new SingleParamTestContext(param));
      }

      return contexts.stream();
    } catch (Exception e) {
      throw new RuntimeException("Exception during test context creation", e);
    }
  }
}