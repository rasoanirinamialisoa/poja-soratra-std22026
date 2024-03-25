package hei.school.soratra.endpoint.rest.controller.health;

import hei.school.soratra.service.event.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import java.util.HashMap;

@RestController
@RequestMapping("/soratra")
public class SoratraController {

    @Autowired
    private S3Service s3Service;

    @PutMapping("/{id}")
    public ResponseEntity<?> uploadPhrase(@PathVariable String id, @RequestBody String phrase) {
        s3Service.uploadPhrase(id, phrase.toLowerCase());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTransformedPhrase(@PathVariable String id) {
        String originalUrl = s3Service.getPreSignedURL(id, false);
        String transformedUrl = s3Service.getPreSignedURL(id, true);

        // Insérer les URL pré-signées dans un objet Map pour la réponse JSON
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("original_url", originalUrl);
        responseMap.put("transformed_url", transformedUrl);

        // Retourner la ResponseEntity avec l'objet JSON contenant les URL pré-signées
        return ResponseEntity.ok(responseMap);
    }
}

