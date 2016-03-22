package com.daicoden.jruby;

public class ImplementationFactory {
  public static BrokenImplementation getBrokenImplementation() {
    return new BrokenImplementation();
  }

  public static WorkingImplementation getWorkingImplementation() {
    return new WorkingImplementation();
  }
}
