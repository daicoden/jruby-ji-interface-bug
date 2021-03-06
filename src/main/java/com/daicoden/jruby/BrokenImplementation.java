package com.daicoden.jruby;

class BrokenImplementation implements TestInterface {
  private Boolean lockAcquired = null;

  @Override public void doWork() {
    lockAcquired = true;
  }

  public boolean isLockAcquired() {
    return lockAcquired;
  }
}
