package com.momot.trakball.manager;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class CloudinaryManager {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${CLOUDINARY_URL}")
    private String cloudinaryUrl;

    public String uploadAvatarAndGetId(MultipartFile file) {
        Cloudinary cloudinary = new Cloudinary(cloudinaryUrl);
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("folder", "avatars/",
                    "transformation", new Transformation().width(1000).height(1000).crop("fill").gravity("face")));
            return uploadResult.get("url").toString();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }

    public String updateAvatarAndGetPublicId(MultipartFile file, String publicId) {
        Cloudinary cloudinary = new Cloudinary(cloudinaryUrl);
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("override", "true",
                    "public_id", publicId,
                    "folder", "avatars/",
                    "transformation", new Transformation().width(1000).height(1000).crop("fill").gravity("face")));
            return uploadResult.get("url").toString();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }

    public String uploadPlacePhotoAndGetPublicId(MultipartFile file) {
        Cloudinary cloudinary = new Cloudinary(cloudinaryUrl);
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("folder", "places/",
                    "transformation", new Transformation().width(2000).height(2000).crop("fill")));
            return uploadResult.get("url").toString();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }

    public String updatePlacePhotoAndGetPublicId(MultipartFile file, String publicId) {
        Cloudinary cloudinary = new Cloudinary(cloudinaryUrl);
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("override", "true",
                    "public_id", publicId,
                    "folder", "places/",
                    "transformation", new Transformation().width(2000).height(2000).crop("fill")));
            return uploadResult.get("url").toString();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }

    public String deleteAndGetId(String publicId) {
        Cloudinary cloudinary = new Cloudinary(cloudinaryUrl);
        try {
            Map uploadResult = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            return uploadResult.get("result").toString();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }
}