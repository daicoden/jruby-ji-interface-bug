package com.daicoden.jruby;

public class ImplementationFactory {
  BrokenImplementation getBrokenImplementation(String argument) {
    return new BrokenImplementation(argument);
  }
}
