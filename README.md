default behavior for scripting container vs jruby invocation are different

if you run: jruby test.rb, vs runing Main.java, you will see that the jruby style invocation works, while the Main.java errors out with a NoMethodError: undefined method `isLockAcquired' for #<Java::ComDaicodenJruby::BrokenImplementation:0x27f74733>.

This is because the class is not public.

Either behavior is fine, however having this descrepency can cause confusion.  For example all the tests in a spec suite may pass, but it fails in production.


Output from running each.

```
~/Development/jruby-ji-interface-bug master * jruby test.rb
 true should be true
 true should be true
~/Development/jruby-ji-interface-bug master *
```

from maven

```
/Users/mattwilson/Development/jruby-ji-interface-bug
/Users/mattwilson/Development/jruby-ji-interface-bug
here we go
 true should be true
NoMethodError: undefined method `isLockAcquired' for #<Java::ComDaicodenJruby::BrokenImplementation:0x27f74733>
  (root) at /Users/mattwilson/Development/jruby-ji-interface-bug/test.rb:19
Exception in thread "main" org.jruby.embed.EvalFailedException: (NoMethodError) undefined method `isLockAcquired' for #<Java::ComDaicodenJruby::BrokenImplementation:0x27f74733>
        at org.jruby.embed.internal.EmbedEvalUnitImpl.run(EmbedEvalUnitImpl.java:133)
        at org.jruby.embed.ScriptingContainer.runUnit(ScriptingContainer.java:1245)
        at org.jruby.embed.ScriptingContainer.runScriptlet(ScriptingContainer.java:1261)
        at com.daicoden.jruby.Main.main(Main.java:35)
        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.lang.reflect.Method.invoke(Method.java:483)
        at com.intellij.rt.execution.application.AppMain.main(AppMain.java:140)
Caused by: org.jruby.exceptions.RaiseException: (NoMethodError) undefined method `isLockAcquired' for #<Java::ComDaicodenJruby::BrokenImplementation:0x27f74733>
        at RUBY.(root)(/Users/mattwilson/Development/jruby-ji-interface-bug/test.rb:19)
```

