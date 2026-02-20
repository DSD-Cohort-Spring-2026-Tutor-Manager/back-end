package org.tutortoise.service.student;

import org.tutortoise.service.session.Session;

import java.util.ArrayList;
import java.util.List;

public class StudentDTO {

  private Integer studentId;
  private Integer parentId;
  private String firstName;
  private String lastName;
  private String notes;
  private List<Session> sessions = new ArrayList<>();

  public StudentDTO() {
  }

  public StudentDTO(Integer studentId, Integer parentId, String firstName, String lastName, String notes, List<Session> sessions) {
    this.studentId = studentId;
    this.parentId = parentId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.notes = notes;
    this.sessions = sessions;
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

  public List<Session> getSessions() {
    return sessions;
  }

  public void setSessions(List<Session> sessions) {
    this.sessions = sessions;
  }
}

