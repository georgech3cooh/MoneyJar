package com.moneyjar;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String handleFileUpload(@RequestParam("name") String name,
			@RequestParam("file") MultipartFile file, Model model) {
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(new File(name + "-uploaded")));
				stream.write(bytes);
				stream.close();

				model.addAttribute("message", "The file " + name
						+ "was successfully uploaded.");
				return "success";

			} catch (Exception e) {
				model.addAttribute("message", "The file" + name
						+ " could not be uploaded - because " + e.getMessage());
				return "failure";
			}
		} else {
			model.addAttribute("message", "The file " + name
					+ "could not be uploaded, because the file is empty.");
			return "failure";
		}

	}

}
