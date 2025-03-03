package com.events.photo.controllers;

import static com.events.photo.controllers.ControllerUtilService.buildResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.events.photo.exceptions.NotFoundException;
import com.events.photo.models.ApiResponse;
import com.events.photo.models.entities.Photo;
import com.events.photo.services.PhotoService;

@RestController
@RequestMapping("/api/v1/photo")
@RequiredArgsConstructor
@Validated
public class PhotoController {

    private final PhotoService photoService;

    @PostMapping("/event/{qrCode}")
    public ResponseEntity<ApiResponse<Photo>> uploadPhoto(
            @PathVariable String qrCode,
            @RequestParam("file") MultipartFile file) throws IOException, NotFoundException {

        Photo photo = photoService.uploadPhoto(qrCode, file);
        return buildResponse(HttpStatus.OK, photo, "Photos found.");
    }

    @GetMapping("/event/{qrCode}")
    public ResponseEntity<ApiResponse<List<Photo>>> getPhotosByEvent(@PathVariable String qrCode) throws
            NotFoundException {
        List<Photo> photos = photoService.getPhotosByEvent(qrCode);
        return buildResponse(HttpStatus.OK, photos, "Photos found.");
    }
}
