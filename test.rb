
# this will work when running from the command line, but not when running main from java

unless ENV["CONTAINER"] == "NATIVE" # set by main.java
  begin
    require_relative 'target/jruby-ji-interface-bug-HEAD-SNAPSHOT.jar'
  rescue LoadError
    puts "run `mvn -am package` first please"
    exit(-1)
  end
end

x = true
if x
  implementation = com.daicoden.jruby.ImplementationFactory.new.getBrokenImplementation("boop")
end

if implementation
  puts "running with broken implementation"
  puts "#{implementation.doWork()} should be true"
  puts "#{implementation.wasCalled} should be true"
end

