package org.tutortoise.service;


import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TutortoiseApplicationTest {

  @InjectMocks
  private TutortoiseApplication application;

  @Test
  void contextLoads() {
    assertNotNull(application);
  }
}
