package net.stickycode.mockwire.testng;

import static org.assertj.core.api.StrictAssertions.assertThat;

import org.testng.annotations.Test;

public class SanityTest {

  @Test
  public void check() {
    assertThat(true).isTrue();
  }

}
