package com.events.photo.repositories;

import com.events.photo.models.entities.Photo;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {

  List<Photo> findAllByEventId(Long eventId);
}
