package com.events.photo.services;

import org.opencv.core.Mat;

public interface FaceRecognitionService {

    float[] extractEmbedding(Mat faceImage);

    double computeSimilarity(float[] emb1, float[] emb2);
}
