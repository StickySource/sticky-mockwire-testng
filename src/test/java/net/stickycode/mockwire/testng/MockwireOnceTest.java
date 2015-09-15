package net.stickycode.mockwire.testng;

import org.testng.annotations.Test;


public class MockwireOnceTest {
  
  @Test
  public void sameTest() {
    MockwireRunner session = new MockwireRunner();
    MockwireRunnerTest test = new MockwireRunnerTest();
    session.startTest(test);
    session.startTest(test);
    session.startTest(test);
    session.startTest(test);
    session.startTest(new MockwireRunnerTest());
  }

}
