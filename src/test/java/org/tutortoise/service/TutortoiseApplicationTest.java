package org.tutortoise.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class TutortoiseApplicationTest {

  @InjectMocks
  private TutortoiseApplication application;

  @Test
  void contextLoads() {
    assertNotNull(application);
  }
}
