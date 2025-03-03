package com.events.photo.models.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import com.events.photo.models.Auditable;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "photo", uniqueConstraints = {@UniqueConstraint(columnNames = "photoUrl")})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Photo extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relacionamento ManyToOne com Event.
    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    private String photoUrl;
    private LocalDateTime uploadDate;

    // Relacionamento OneToMany com Face.
    @OneToMany(mappedBy = "photo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Face> faces;
}
