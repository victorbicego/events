package com.events.photo.repositories;

import com.events.photo.models.entities.Event;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

  Optional<Event> findByQrCode(String qrCode);

  boolean existsByName(String name);
}
