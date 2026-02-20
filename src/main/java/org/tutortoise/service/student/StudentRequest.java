package org.tutortoise.service.student;

public class StudentRequest {

  private Integer parentId;
  private String firstName;
  private String lastName;

  public StudentRequest() {}

  public StudentRequest(Integer parentId, String firstName, String lastName) {
    this.parentId = parentId;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public Integer getParentId() {
    return parentId;
  }

  public void setParentId(Integer parentId) {
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
}
