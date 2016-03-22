package com.daicoden.jruby;

import com.daicoden.jruby.another_package.TestInterface;

public class BrokenImplementation implements TestInterface<Boolean> {
  private final String foop;
  private boolean wasCalled = false;

  public BrokenImplementation(String foop) {
    this.foop = foop;
  }

  @Override public Boolean doWork() {
    wasCalled = true;
    return true;
  }

  public boolean wasCalled() {
    System.out.println("NOOooo");
    return wasCalled;
  }
}
