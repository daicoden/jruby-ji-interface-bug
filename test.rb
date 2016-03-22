
# this will work when running from the command line, but not when running main from java

unless ENV["CONTAINER"] == "NATIVE" # set by main.java
  begin
    require_relative 'target/jruby-ji-interface-bug-HEAD-SNAPSHOT.jar'
  rescue LoadError
    puts "run `mvn -am package` first please"
    exit(-1)
  end
end

implementation = com.daicoden.jruby.ImplementationFactory.getWorkingImplementation
implementation.doWork
puts " #{implementation.isLockAcquired} should be true"

implementation = com.daicoden.jruby.ImplementationFactory.getBrokenImplementation
implementation.doWork
puts " #{implementation.isLockAcquired} should be true"


