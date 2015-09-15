package net.stickycode.mockwire.testng;

import static org.assertj.core.api.StrictAssertions.assertThat;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import net.stickycode.mockwire.MockwireConfigured;
import net.stickycode.mockwire.UnderTest;


@Listeners(MockwireRunner.class)
@MockwireConfigured
public class MockwireRunnerConfiguredTest {

  @UnderTest("value=something")
  Testable testable;

  @Test
  public void hi() {
    assertThat(testable).isNotNull();
    assertThat(testable.value).isEqualTo("something");
  }

  @Test(invocationCount = 10,threadPoolSize=10)
  public void repeat() {
    assertThat(testable).isNotNull();
    assertThat(testable.value).isEqualTo("something");
  }
}
