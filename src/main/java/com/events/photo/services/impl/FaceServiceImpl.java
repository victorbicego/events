package com.events.photo.services.impl;

import com.events.photo.models.entities.Face;
import com.events.photo.models.entities.Photo;
import com.events.photo.services.FaceService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FaceServiceImpl implements FaceService {

  public List<Face> detectFaces(Photo photo) {
    List<Face> detectedFaces = new ArrayList<>();
    return detectedFaces;
  }
}
