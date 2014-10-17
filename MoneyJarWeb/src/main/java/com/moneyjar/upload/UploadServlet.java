package com.moneyjar.upload;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.log4j.Logger;

import com.moneyjar.statement.StatementImporter;

@WebServlet("/upload")
@MultipartConfig
public class UploadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIR = "uploads";
	private static Logger logger = Logger.getLogger(UploadServlet.class);

	public UploadServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String uploadFilePath = getUploadFilePath(request);
		String fileName = writeFileToUploadDirectory(request, uploadFilePath);
		
		File uploadedFile = new File(uploadFilePath + File.separator
					+ fileName);
		String uploadStatus = parseFileToDatabase(uploadedFile);
		
		request.setAttribute("status", uploadStatus);
		getServletContext().getRequestDispatcher("/upload-response.jsp")
				.forward(request, response);

	}

	private String parseFileToDatabase(File uploadedFile) {
		// Parse file and trasfer data to database
		String message = "The file '" + uploadedFile.getName()
				+ "' has been uploaded successfully!";

		StatementImporter importer = new StatementImporter();
		try {
			importer.statementImport(uploadedFile);
		} catch (Exception e) {
			message = e.getMessage();
		}
		return message;
	}

	private String writeFileToUploadDirectory(HttpServletRequest request,
			String uploadFilePath) throws IOException, ServletException {
			
		// Get all the parts from request and write it to the file on server
		String fileName = null;
		for (Part part : request.getParts()) {
			fileName = getFileName(part);
			part.write(uploadFilePath + File.separator + fileName);
		}
		return fileName;
	}

	private String getUploadFilePath(HttpServletRequest request) {

		logger.debug(">> getUploadFilePath()");

		// gets absolute path of the web application
		String applicationPath = request.getServletContext().getRealPath("");

		// constructs path of the directory to save uploaded file
		String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;

		// creates the save directory if it does not exists
		File fileSaveDir = new File(uploadFilePath);
		if (!fileSaveDir.exists()) {
			fileSaveDir.mkdirs();
		}

		logger.debug("<< getUploadFilePath() - returning " + uploadFilePath
				+ "as upload file path");
		return uploadFilePath;
	}

	/**
	 * Utility method to get file name from HTTP header content-disposition
	 */
	private String getFileName(Part part) {
		String contentDisp = part.getHeader("content-disposition");
		System.out.println("content-disposition header= " + contentDisp);
		String[] tokens = contentDisp.split(";");
		for (String token : tokens) {
			if (token.trim().startsWith("filename")) {
				return token.substring(token.indexOf("=") + 2,
						token.length() - 1);
			}
		}
		return "";
	}

}
