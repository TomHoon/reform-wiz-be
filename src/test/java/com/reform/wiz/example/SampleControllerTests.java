package com.reform.wiz.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import lombok.extern.log4j.Log4j2;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Log4j2
public class SampleControllerTests {

  @Autowired(required = false)
  private TestRestTemplate testRestTemplate;

  @Test
  public void testHello() {
    String result = testRestTemplate.getForObject("/api/v1/sample/hello", String.class);

    log.info(result);
    System.out.println("result >> " + result);
  }
}
