package org.tutortoise.service.subject;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.tutortoise.service.session.Session;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "subject")
public class Subject {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "subject_id", nullable = false)
  private Integer id;

  @Size(max = 255)
  @NotNull
  @Column(name = "subject", nullable = false)
  private String subject;

  @ColumnDefault("0")
  @Column(name = "total_sessions_hours")
  private Integer totalSessionsHours;

  @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Session> sessions = new ArrayList<>();
}
