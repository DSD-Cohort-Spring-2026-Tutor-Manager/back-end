package org.tutortoise.service.student;

import java.util.ArrayList;
import java.util.List;

public class StudentDTO {

  private Integer studentId;
  private Integer parentId;
  private String firstName;
  private String lastName;
  private String notes;
  private List<Integer> sessionIds = new ArrayList<>();

  public StudentDTO() {
  }

  public StudentDTO(Integer studentId, Integer parentId, String firstName, String lastName, String notes, List<Integer> sessionIds) {
    this.studentId = studentId;
    this.parentId = parentId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.notes = notes;
    this.sessionIds = sessionIds;
  }

  public Integer getStudentId() {
    return studentId;
  }

  public void setStudentId(Integer studentId) {
    this.studentId = studentId;
  }

  public Integer getParent() {
    return parentId;
  }

  public void setParent(Integer parentId) {
    this.parentId = parentId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public List<Integer> getSessions() {
    return sessionIds;
  }

  public void setSessions(List<Integer> sessionIds) {
    this.sessionIds = sessionIds;
  }
}

