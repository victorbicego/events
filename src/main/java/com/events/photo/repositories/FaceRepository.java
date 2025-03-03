package com.events.photo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.events.photo.models.entities.Face;

@Repository
public interface FaceRepository extends JpaRepository<Face, Long> {
}
