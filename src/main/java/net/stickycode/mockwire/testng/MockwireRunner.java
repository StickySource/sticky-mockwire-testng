package net.stickycode.mockwire.testng;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import net.stickycode.mockwire.Mockwire;
import net.stickycode.mockwire.MockwireContainer;

/**
 * Only one instance of test class will be used and it will be injected only once.
 */
public class MockwireRunner
    implements ITestListener {

  static {
    // init logger before the tests otherwise threaded tests fail to log
    LoggerFactory.getLogger(MockwireRunner.class);
  }

  private static Map<Object, MockwireContainer> contexts = new HashMap<Object, MockwireContainer>();

  private static Lock lock = new ReentrantLock();

  @Override
  public void onTestStart(ITestResult result) {
    startTest(result.getInstance());
  }

  void startTest(Object object) {
    lock.lock();
    try {
      if (!contexts.containsKey(object)) {
        LoggerFactory.getLogger(MockwireRunner.class).debug("create context for {}", object.getClass());
        MockwireContainer context = Mockwire.isolate(object);
        contexts.put(object, context);
      }
    }
    finally {
      lock.unlock();
    }
  }

  @Override
  public void onTestSuccess(ITestResult result) {
  }

  @Override
  public void onTestFailure(ITestResult result) {
  }

  @Override
  public void onTestSkipped(ITestResult result) {
  }

  @Override
  public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
  }

  @Override
  public void onStart(ITestContext context) {
  }

  @Override
  public void onFinish(ITestContext context) {
    lock.lock();
    try {
      for (Object key : new HashSet<Object>(contexts.keySet())) {
        contexts.remove(key).shutdown();
      }
    }
    finally {
      lock.unlock();
    }
  }

}
