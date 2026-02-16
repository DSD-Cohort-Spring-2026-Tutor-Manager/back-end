package org.tutortoise.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TutortoiseApplicationTest {

  @Autowired private TutortoiseApplication application;

//  @Test
//  void contextLoads() {
//    assertNotNull(application);
//  }
}
