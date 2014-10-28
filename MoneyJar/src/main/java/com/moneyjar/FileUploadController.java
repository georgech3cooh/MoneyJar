package com.moneyjar;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import jxl.common.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {

	Logger logger = Logger.getLogger(FileUploadController.class);
	
	@RequestMapping(value = "/upload", method = RequestMethod.GET) 
	public String pageRequest() {
		return "upload";
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String handleFileUpload(@RequestParam("file") MultipartFile file, 
									Model model) {
		String name = file.getOriginalFilename();
		logger.debug("Uploading " + name + " to server.");
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(new File(name)));
				stream.write(bytes);
				stream.close();
				model.addAttribute("message", "The file " + name
						+ " was successfully uploaded.");
				logger.debug("File upload successful.");
				return "success";

			} catch (Exception e) {
				model.addAttribute("message", "The file" + name
						+ " could not be uploaded - because " + e.getMessage());
				logger.debug("File upload failed.");
				return "failure";
			}
		} else {
			model.addAttribute("message", "The file " + name
					+ "could not be uploaded, because the file is empty.");
			logger.debug("File upload failed.");
			return "failure";
		}
	}

}
