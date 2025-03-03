package com.events.photo.services.impl;

import com.events.photo.models.entities.Face;
import com.events.photo.models.entities.Photo;
import com.events.photo.repositories.PhotoRepository;
import com.events.photo.services.FaceService;
import com.events.photo.services.PhotoService;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PhotoServiceImpl implements PhotoService {

  private final PhotoRepository photoRepository;
  private final StorageService storageService;
  private final FaceService faceService;

  public Photo uploadPhoto(Photo photo, MultipartFile file) throws IOException {
    String photoUrl = storageService.uploadFile(file);
    photo.setPhotoUrl(photoUrl);
    photo.setUploadDate(LocalDateTime.now());

    List<Face> faces = recognizeFaces(photo);
    photo.setFaces(faces);

    return photoRepository.save(photo);
  }

  public List<Photo> getPhotosByEvent(Long eventId) {
    return photoRepository.findAllByEventId(eventId);
  }

  public List<Face> recognizeFaces(Photo photo) {
    return faceService.detectFaces(photo);
  }
}
