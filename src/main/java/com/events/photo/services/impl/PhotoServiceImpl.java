package com.events.photo.services.impl;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgcodecs.Imgcodecs;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.events.photo.exceptions.BadRequestException;
import com.events.photo.exceptions.NotFoundException;
import com.events.photo.models.entities.Event;
import com.events.photo.models.entities.Face;
import com.events.photo.models.entities.Photo;
import com.events.photo.repositories.PhotoRepository;
import com.events.photo.services.EventService;
import com.events.photo.services.FaceRecognitionService;
import com.events.photo.services.FaceService;
import com.events.photo.services.PhotoService;
import com.events.photo.services.StorageService;

@Service
@RequiredArgsConstructor
public class PhotoServiceImpl implements PhotoService {

    private final PhotoRepository photoRepository;
    private final StorageService storageService;
    private final FaceService faceService;
    private final FaceRecognitionService faceRecognitionService;
    private final EventService eventService;

    @Override
    public Photo uploadPhoto(String qrCode, MultipartFile file) throws IOException, NotFoundException {
        Event foundEvent = eventService.getEventByQrCode(qrCode);
        String photoUrl = storageService.uploadFile(file);

        Photo photo = new Photo();
        photo.setPhotoUrl(photoUrl);
        photo.setUploadDate(LocalDateTime.now());

        List<Face> faces = recognizeFaces(photo);
        photo.setFaces(faces);
        photo.setEvent(foundEvent);

        return photoRepository.save(photo);
    }

    @Override
    public List<Photo> getPhotosByEvent(String qrCode) throws NotFoundException {
        Event foundEvent = eventService.getEventByQrCode(qrCode);
        return foundEvent.getPhotos();
    }

    public List<Face> recognizeFaces(Photo photo) {
        return faceService.detectFaces(photo);
    }

    public List<Photo> findRelatedPhotosByFace(MultipartFile file, Long eventId) throws IOException,
            BadRequestException {
        // 1. Realiza o upload da foto de consulta
        Photo queryPhoto = new Photo();
        queryPhoto.setPhotoUrl(storageService.uploadFile(file));

        // 2. Detecta as faces na foto enviada
        List<Face> queryFaces = faceService.detectFaces(queryPhoto);
        if (queryFaces.isEmpty()) {
            throw new BadRequestException("Nenhuma face detectada na foto fornecida.");
        }
        // Utiliza a primeira face detectada
        Face queryFace = queryFaces.get(0);

        // 3. Carrega a imagem completa e recorta a região da face
        Mat fullImage = Imgcodecs.imread(queryPhoto.getPhotoUrl());
        Rect faceRect = new Rect(queryFace.getX(), queryFace.getY(), queryFace.getWidth(), queryFace.getHeight());
        Mat faceImage = new Mat(fullImage, faceRect);

        // 4. Extrai o embedding da face da foto de consulta
        float[] queryEmbedding = faceRecognitionService.extractEmbedding(faceImage);

        // 5. Recupera todas as fotos do evento
        List<Photo> eventPhotos = photoRepository.findAllByEventId(eventId);
        List<Photo> relatedPhotos = new ArrayList<>();
        final double THRESHOLD = 0.8; // Define o limiar de similaridade desejado

        // 6. Para cada foto do evento, detecta as faces e compara os embeddings
        for (Photo p : eventPhotos) {
            List<Face> faces = faceService.detectFaces(p);
            for (Face f : faces) {
                // Se a face ainda não tiver o embedding calculado, calcula e armazena (opcionalmente, atualiza o BD)
                if (f.getEmbedding() == null) {
                    Mat img = Imgcodecs.imread(p.getPhotoUrl());
                    Rect r = new Rect(f.getX(), f.getY(), f.getWidth(), f.getHeight());
                    Mat faceImg = new Mat(img, r);
                    float[] embedding = faceRecognitionService.extractEmbedding(faceImg);
                    f.setEmbedding(embedding);
                }
                double similarity = faceRecognitionService.computeSimilarity(queryEmbedding, f.getEmbedding());
                if (similarity >= THRESHOLD) {
                    relatedPhotos.add(p);
                    break; // Se encontrar correspondência, passa para a próxima foto
                }
            }
        }
        return relatedPhotos;
    }
}
