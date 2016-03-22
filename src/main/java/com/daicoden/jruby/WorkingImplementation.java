package com.daicoden.jruby;

public class WorkingImplementation implements TestInterface {
  private Boolean lockAcquired = null;

  @Override public void doWork() {
    lockAcquired = true;
  }

  public boolean isLockAcquired() {
    return lockAcquired;
  }
}
