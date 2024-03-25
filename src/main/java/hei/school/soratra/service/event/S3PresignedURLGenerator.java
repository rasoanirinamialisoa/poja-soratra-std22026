package hei.school.soratra.service.event;

import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.net.URL;
import java.time.Duration;

@Component
public class S3PresignedURLGenerator {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    public S3PresignedURLGenerator(S3Client s3Client, S3Presigner s3Presigner) {
        this.s3Client = s3Client;
        this.s3Presigner = s3Presigner;
    }

    public String generatePreSignedURL(String bucketName, String key) {
        // Durée de validité de l'URL pré-signée
        Duration duration = Duration.ofMinutes(10);

        // Construire la demande de l'objet à pré-signer
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        // Générer la requête pré-signée
        PresignedGetObjectRequest presignedRequest = s3Presigner.presignGetObject(b -> b.signatureDuration(duration)
                .getObjectRequest(getObjectRequest)
                .build());

        // Obtenir l'URL pré-signée à partir de la requête pré-signée
        URL url = presignedRequest.url();

        // Retourner l'URL pré-signée en tant que String
        return url.toString();
    }
}

