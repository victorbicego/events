package com.events.photo.models.entities;

import com.events.photo.models.Auditable;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(
        name = "face")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Face extends Auditable {
  private Long id;
  private Photo photo;
  private String faceData;
}
