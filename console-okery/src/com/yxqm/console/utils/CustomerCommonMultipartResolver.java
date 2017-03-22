/**  
 * Project Name:console-web  
 * File Name:CustomerCommonMultipartResolver.java  
 * Package Name:com.yxqm.console.web.upload.file  
 * Date:2015年9月8日下午5:43:52  
 * Copyright (c) 2015, 雷湘剑 All Rights Reserved.  
 *  
*/  
  
package com.yxqm.console.utils;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**  
 * ClassName:CustomerCommonMultipartResolver <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason:   TODO ADD REASON. <br/>  
 * Date:     2015年9月8日 下午5:43:52 <br/>  
 * @author  雷湘剑 
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
public class CustomerCommonMultipartResolver extends CommonsMultipartResolver {
	protected MultipartParsingResult parseRequest(HttpServletRequest request) throws MultipartException {
		String encoding = determineEncoding(request);
		FileUpload fileUpload = prepareFileUpload(encoding);
		try {
			List<FileItem> fileItems = ((ServletFileUpload) fileUpload).parseRequest(request);
			return parseFileItems(fileItems, encoding);
		}
		catch (FileUploadBase.SizeLimitExceededException ex) {
			throw new MaxUploadSizeExceededException(fileUpload.getSizeMax(), ex);
		}
		catch (FileUploadException ex) {
			throw new MultipartException("Could not parse multipart servlet request", ex);
		}
	}
}
  
