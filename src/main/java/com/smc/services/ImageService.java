package com.smc.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

	String uploadImage(MultipartFile contactImage,String filename);

	String getUrlFormPublicId(String publicId);
}
