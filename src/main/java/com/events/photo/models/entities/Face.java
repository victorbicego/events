package com.events.photo.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.events.photo.models.Auditable;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "face")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Face extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relacionamento ManyToOne com Photo.
    @ManyToOne
    @JoinColumn(name = "photo_id", nullable = false)
    private Photo photo;

    private int x;
    private int y;
    private int width;
    private int height;

    private String faceData;

    // @Lob permite armazenar o vetor de embedding.
    @Lob
    private float[] embedding;
}
