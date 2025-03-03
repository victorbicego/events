package com.events.photo.services.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import java.nio.FloatBuffer;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.springframework.stereotype.Service;
import org.tensorflow.SavedModelBundle;
import org.tensorflow.Tensor;

import com.events.photo.services.FaceRecognitionService;

@Service
@RequiredArgsConstructor
public class FaceRecognitionServiceImpl implements FaceRecognitionService {

    private static final String MODEL_DIR = "models/facenet";
    private SavedModelBundle model;

    @PostConstruct
    public void init() {
        // Carrega o modelo TensorFlow
        model = SavedModelBundle.load(MODEL_DIR, "serve");
    }

    @Override
    public float[] extractEmbedding(Mat faceImage) {
        // Pré-processamento da imagem para criar o tensor de entrada
        Tensor<Float> inputTensor = preprocess(faceImage);

        // Executa a inferência para obter o embedding
        Tensor<Float> embeddingTensor = model.session().runner()
                .feed("input", inputTensor)       // nome da entrada, conforme o modelo
                .fetch("embeddings")                // nome da saída, conforme o modelo
                .run().get(0).expect(Float.class);

        // Converte o resultado para um vetor de float
        long[] shape = embeddingTensor.shape();
        int embeddingSize = (int) shape[1];
        float[][] embeddingArray = new float[1][embeddingSize];
        embeddingTensor.copyTo(embeddingArray);
        return embeddingArray[0];
    }

    private Tensor<Float> preprocess(Mat faceImage) {
        // Redimensiona a imagem para 160x160
        Mat resizedImage = new Mat();
        Imgproc.resize(faceImage, resizedImage, new Size(160, 160));

        // Converte a imagem para float e normaliza (dividindo por 255)
        resizedImage.convertTo(resizedImage, CvType.CV_32F, 1.0 / 255);

        int rows = resizedImage.rows();
        int cols = resizedImage.cols();
        int channels = resizedImage.channels();

        // Converte os dados da imagem para um array de float
        float[] data = new float[rows * cols * channels];
        resizedImage.get(0, 0, data);

        // Define o shape do tensor: [1, rows, cols, channels]
        long[] shape = new long[]{1, rows, cols, channels};
        return Tensor.create(shape, FloatBuffer.wrap(data));
    }

    @Override
    public double computeSimilarity(float[] emb1, float[] emb2) {
        double dot = 0.0, norm1 = 0.0, norm2 = 0.0;
        for (int i = 0; i < emb1.length; i++) {
            dot += emb1[i] * emb2[i];
            norm1 += emb1[i] * emb1[i];
            norm2 += emb2[i] * emb2[i];
        }
        return dot / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }
}
