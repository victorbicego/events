package com.events.photo.models.entities;

import com.events.photo.models.Auditable;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(
        name = "event")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event extends Auditable {
  private Long id;
  private String name;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private String qrCode;
  private List<Photo> photos;
  private List<User> organizers;
}
