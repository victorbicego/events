package com.events.photo.services.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.objdetect.CascadeClassifier;
import org.springframework.stereotype.Service;

import com.events.photo.models.entities.Face;
import com.events.photo.models.entities.Photo;
import com.events.photo.services.FaceService;

@Service
@RequiredArgsConstructor
public class FaceServiceImpl implements FaceService {

    static {
        // Carrega a biblioteca nativa do OpenCV.
        System.loadLibrary("opencv_java455");
    }

    private CascadeClassifier faceCascade;

    @PostConstruct
    public void init() {
        // Carrega o classificador Haar Cascade a partir dos recursos da aplicação.
        // Certifique-se de que o arquivo "haarcascade_frontalface_alt.xml" esteja na pasta "resources" do seu projeto.
        String classifierPath = getClass().getResource("/haarcascade_frontalface_alt.xml").getPath();
        faceCascade = new CascadeClassifier(classifierPath);
        if (faceCascade.empty()) {
            throw new RuntimeException("Falha ao carregar o classificador Haar Cascade.");
        }
    }

    @Override
    public List<Face> detectFaces(Photo photo) {
        List<Face> detectedFaces = new ArrayList<>();
        // Supondo que photo.getPhotoUrl() retorne o caminho do arquivo de imagem armazenado localmente.
        Mat image = Imgcodecs.imread(photo.getPhotoUrl());
        if (image.empty()) {
            System.err.println("Não foi possível carregar a imagem: " + photo.getPhotoUrl());
            return detectedFaces;
        }
        MatOfRect faceDetections = new MatOfRect();
        faceCascade.detectMultiScale(image, faceDetections);
        for (Rect rect : faceDetections.toArray()) {
            Face face = new Face();
            face.setX(rect.x);
            face.setY(rect.y);
            face.setWidth(rect.width);
            face.setHeight(rect.height);
            detectedFaces.add(face);
        }
        return detectedFaces;
    }
}
