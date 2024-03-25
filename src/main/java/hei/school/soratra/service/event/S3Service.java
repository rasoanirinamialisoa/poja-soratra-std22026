package hei.school.soratra.service.event;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class S3Service {

    private final S3Client s3Client;
    private final S3PresignedURLGenerator s3PresignedURLGenerator;

    @Value("${s3.bucket.name}")
    private String bucketName;

    @Autowired
    public S3Service(S3Client s3Client, S3PresignedURLGenerator s3PresignedURLGenerator) {
        this.s3Client = s3Client;
        this.s3PresignedURLGenerator = s3PresignedURLGenerator;
    }

    public void uploadPhrase(String id, String phrase) {
        String key = "poetic_phrases/" + id + ".txt";

        s3Client.putObject(PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .build(),
                RequestBody.fromString(phrase));
    }

    public String getPreSignedURL(String id, boolean transform) {
        String originalKey = "poetic_phrases/" + id + ".txt";
        String transformedKey = "poetic_phrases/transformed/" + id + ".txt";

        if (transform) {
            String phrase = ""; // Récupérez et transformez la phrase ici
            uploadPhrase(id + "-transformed", phrase.toUpperCase());
            return s3PresignedURLGenerator.generatePreSignedURL(bucketName, transformedKey);
        } else {
            return s3PresignedURLGenerator.generatePreSignedURL(bucketName, originalKey);
        }
    }
}

