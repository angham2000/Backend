package tn.esprit.immobilier.controllers;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.immobilier.entities.Image;
import tn.esprit.immobilier.services.CloudinaryService;
import tn.esprit.immobilier.services.ImageService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/cloudinary")
public class CloudinaryController {
    private static final Logger log = LoggerFactory.getLogger(CloudinaryController.class);
    private  CloudinaryService cloudinaryService;
    private  ImageService imageService;

    @GetMapping("/list")
    public ResponseEntity<List<Image>> list() {
        List<Image> list = imageService.list();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity<String> upload(@RequestParam MultipartFile multipartFile) {
        try {
            BufferedImage bi = ImageIO.read(multipartFile.getInputStream());
            if (bi == null) {
                log.warn("Invalid image file: " + multipartFile.getOriginalFilename());
                return new ResponseEntity<>("Image non valide!", HttpStatus.BAD_REQUEST);
            }
            Map<String, String> result = cloudinaryService.upload(multipartFile);
            String imageUrl = result.get("url");
            Image image = new Image((String) result.get("original_filename"),
                    imageUrl,
                    (String) result.get("public_id"));
            imageService.save(image);
            log.info("Image uploaded successfully: " + multipartFile.getOriginalFilename());
            return new ResponseEntity<>("Image ajoutée avec succès ! URL: " + imageUrl, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error during image upload", e);
            return new ResponseEntity<>("Erreur lors de l'upload de l'image", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") int id) {
        Optional<Image> imageOptional = imageService.getOne(id);
        if (imageOptional.isEmpty()) {
            return new ResponseEntity<>("n'existe pas", HttpStatus.NOT_FOUND);
        }
        Image image = imageOptional.get();
        String cloudinaryImageId = image.getImageId();
        try {
            cloudinaryService.delete(cloudinaryImageId);
        } catch (IOException e) {
            log.error("Failed to delete image from Cloudinary", e);
            return new ResponseEntity<>("Failed to delete image from Cloudinary", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        imageService.delete(id);
        return new ResponseEntity<>("image supprimée !", HttpStatus.OK);
    }
}