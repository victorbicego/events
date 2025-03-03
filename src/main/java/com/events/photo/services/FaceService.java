package com.events.photo.services;

import java.util.List;

import com.events.photo.models.entities.Face;
import com.events.photo.models.entities.Photo;

public interface FaceService {

    List<Face> detectFaces(Photo photo);
}
