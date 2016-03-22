package com.daicoden.jruby;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import org.jruby.embed.ScriptingContainer;
import org.jruby.util.JRubyClassLoader;

import static java.lang.System.getenv;
import static org.jruby.CompatVersion.RUBY1_9;
import static org.jruby.embed.AttributeName.BASE_DIR;

public class Main {
  private static final Joiner SLASH = Joiner.on("/");

  // Run this in order to see bug
  public static void main(String[] args) throws FileNotFoundException {
    ScriptingContainer ruby = getScriptingContainer();

    JRubyClassLoader classLoader = ruby.getProvider().getRuntime().getJRubyClassLoader();
    Thread.currentThread().setContextClassLoader(classLoader);
    ruby.runScriptlet("puts `pwd`");
    System.out.println(rootDir());
    ruby.runScriptlet("$:.unshift(\"" + rootDir() + "\")");
    System.out.println("here we go");
    String scriptName = rootDir() + "/test.rb";
    ruby.runScriptlet(new FileReader(scriptName), scriptName);
  }

  private static ScriptingContainer getScriptingContainer() {
    ScriptingContainer ruby = new ScriptingContainer();
    ruby.setAttribute(BASE_DIR, rootDir());
    ruby.setCompatVersion(RUBY1_9);
    ruby.setEnvironment(getEnvironment());
    ruby.setCurrentDirectory(rootDir());
    return ruby;
  }

  static Map<String, String> getEnvironment() {
    ImmutableMap.Builder<String, String> environment = ImmutableMap.<String, String>builder()
        .put("CONTAINER", "NATIVE")
        .put("ENVIRONMENT", "TEST")
        .put("GEM_HOME", getenv("GEM_HOME"))
        .put("GEM_PATH", getenv("GEM_PATH"))
        .put("HOME", getenv("HOME"))
            // http://www.jillesvangurp.com/2013/06/14/jruby-utf-8/
        .put("LC_ALL", "en_US.UTF-8")
        .put("LANG", "en_US.UTF-8")
        .put("LANGUAGE", "en_US.UTF-8");

    return environment.build();
  }


  private static String jarPath() {
    File file = toFile(
        Main.class.getProtectionDomain().getCodeSource().getLocation());
    return file.isDirectory() ? file.getPath() : file.getParent();
  }


  private static String rootDir() {
    return canonicalize(jarPath(), "..", "..");
  }

  private static String canonicalize(String... parts) {
    try {
      return new File(SLASH.join(parts)).getCanonicalPath();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private static File toFile(URL url) {
    try {
      URLConnection e = url.openConnection();
      if(e instanceof JarURLConnection) {
        JarURLConnection urlCon = (JarURLConnection)e;
        return new File(urlCon.getJarFile().getName());
      }
    } catch (IOException var4) {
      return null;
    }

    try {
      return new File(url.toURI());
    } catch (URISyntaxException var3) {
      return new File(url.getPath());
    }
  }
}
