package com.moneyjar;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.moneyjar.statement.StatementImporter;

@Controller
public class FileUploadController {
	
	@Autowired
	StatementImporter statementImporter;

	String systemUploadPath = "/opt/upload/";
	Logger logger = Logger.getLogger(FileUploadController.class);
	
	@RequestMapping(value = "/upload", method = RequestMethod.GET) 
	public String pageRequest() {
		return "upload";
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String handleFileUpload(@RequestParam("file") MultipartFile file, 
									Model model) {
		String fileName = file.getOriginalFilename();
		logger.debug("Uploading " + fileName + " to server.");
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				
				File uploadedFile = new File(systemUploadPath, fileName);
				
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(uploadedFile));
				stream.write(bytes);
				stream.close();
				logger.debug("File stored to local file system " 
								+ uploadedFile.getAbsolutePath());
				
				statementImporter.importStatement(uploadedFile);
				logger.debug("File upload and persistence completed.");
				
				model.addAttribute("message", "The file " + fileName
						+ " was successfully uploaded.");
				
				return "success";

			} catch (Exception e) {
				model.addAttribute("message", "The file" + fileName
						+ " could not be uploaded - because " + e.getMessage());
				logger.info("File upload failed - because" + e.getMessage());
				return "failure";
			} finally {
				logger.debug("Deleting uploaded file.");
				Boolean deleted = uploadedFile.delete();
				if (!deleted) {
					logger.error("The working file was not deleted.");
				}
			}
		} else {
			model.addAttribute("message", "The file " + fileName
					+ "could not be uploaded, because the file is empty.");
			logger.info("File upload failed.");
			return "failure";
		}
	}

}
