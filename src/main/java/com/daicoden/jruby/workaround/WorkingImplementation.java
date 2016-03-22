package com.daicoden.jruby.workaround;

import com.daicoden.jruby.another_package.TestInterface;

public class WorkingImplementation implements TestInterface<Boolean>, FixInterface {
  private boolean wasCalled = false;

  public Boolean doWork() {
    wasCalled = true;
    return true;
  }

  public boolean wasCalled() {
    return wasCalled;
  }
}
