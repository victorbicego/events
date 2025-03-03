package com.events.photo.models.entities;

import com.events.photo.models.Auditable;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends Auditable {
  private Long id;
  private String username;
  private String password;
  private String email;
  private List<Event> events;
}
