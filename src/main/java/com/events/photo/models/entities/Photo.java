package com.events.photo.models.entities;

import com.events.photo.models.Auditable;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(
        name = "photo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Photo extends Auditable {
    private Long id;
    private Event event;
    private String photoUrl;
    private LocalDateTime uploadDate;
    private List<Face> faces;
}
