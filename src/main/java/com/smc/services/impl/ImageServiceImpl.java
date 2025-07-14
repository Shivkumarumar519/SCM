package com.smc.services.impl;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.smc.helper.AppConstants;
import com.smc.services.ImageService;

@Service
public class ImageServiceImpl implements ImageService{

	private Cloudinary cloudinary;

	public ImageServiceImpl(Cloudinary cloudinary) {
		super();
		this.cloudinary = cloudinary;
	}



	@Override
	public String uploadImage(MultipartFile contactImage,String filename) {

		//String filename = UUID.randomUUID().toString();

		try {
			byte[] data=new byte[contactImage.getInputStream().available()];

			contactImage.getInputStream().read(data);
			cloudinary.uploader().upload(data, ObjectUtils.asMap(
					"public_id",filename
					));
			return this.getUrlFormPublicId(filename);
		} catch (IOException e) {

			e.printStackTrace();
			return null;
		}


	}



	@Override
	public String getUrlFormPublicId(String publicId) {

		return cloudinary
				.url()
				.transformation(
						new Transformation<>()
						.width(AppConstants.CONTACT_IMAGE_WIDTH)
						.height(AppConstants.CONTACT_IMAGE_HEIGHT)
						.crop(AppConstants.CONTACT_IMAGE_CROP))
				.generate(publicId);
	}





}
