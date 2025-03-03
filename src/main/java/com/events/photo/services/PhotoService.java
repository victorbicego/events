package com.events.photo.services;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.events.photo.exceptions.NotFoundException;
import com.events.photo.models.entities.Photo;

public interface PhotoService {

    Photo uploadPhoto(String qrCode, MultipartFile file) throws IOException, NotFoundException;

    List<Photo> getPhotosByEvent(String qrCode) throws NotFoundException;
}
