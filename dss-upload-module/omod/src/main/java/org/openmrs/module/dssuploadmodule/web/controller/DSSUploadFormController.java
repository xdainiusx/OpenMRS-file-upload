/**
 * DSSUploadFormController
 * 
 * @author Dainius
 */
package org.openmrs.module.dssuploadmodule.web.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.dssuploadmodule.web.model.FileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(value="/module/dssupload/dssForm.form")
public class DSSUploadFormController{
	
	protected final Log log = LogFactory.getLog(getClass());
    /* Note: '/module/dssuploadmodule/' mapped to WebPages directory  */
	private   final String SUCCESS_FORM_VIEW = "/module/dssuploadmodule/jsp/fileUploadForm";
    private   final String SUCCESS_FORM_PAGE = "/module/dssuploadmodule/jsp/fileUploadSuccess";
    private   final String uploadDirectory;
    private   final String tempUploadDirectory;
    
    /**
     * Constructor
     */
    public DSSUploadFormController(){
        this.uploadDirectory = System.getProperty("user.home");
        this.tempUploadDirectory = System.getProperty("java.io.tmpdir");
    }

    /**
     * Displays the form
     * @param model
     * @return form view
     */
	@RequestMapping(method = RequestMethod.GET)
    public String showForm(ModelMap model){
        FileUpload form = new FileUpload();
        model.addAttribute("FORM", form);
        System.out.println("In showForm() Returning the view...");
        return SUCCESS_FORM_VIEW;
    }
	
    /**
     * Processes the form
     * @param form
     * @param result
     * @return success view or form view with the error in case of error
     */
	@RequestMapping(method = RequestMethod.POST)
    //@RequestMapping(value = "/uploadDSS", method = RequestMethod.POST)
    public ModelAndView processForm(@ModelAttribute(value="FORM") FileUpload form, BindingResult result){
        ModelMap model = new ModelMap();
        String errors = null;
        FileOutputStream outputStream;
        String originalFileName = form.getFile().getOriginalFilename();
        String filePath = this.uploadDirectory + "/" + originalFileName;
        String tempFilePath = this.tempUploadDirectory + originalFileName;
        System.out.println("IN processForm() - FORM");
        
        /* 
         * This part is for error checking only - upload the file to temp directory
         * and check for errors, if there are no errors upload to user's home dir
         */
        try {
            outputStream = new FileOutputStream(new File(tempFilePath));
            outputStream.write(form.getFile().getFileItem().get());
            outputStream.close();
        } catch (Exception e) {
            errors = "Please select a valid file for upload!";
            model.put("errors", errors);
            return new ModelAndView(SUCCESS_FORM_VIEW, model);
        }

        model.put("errors", errors);
        if(!result.hasErrors()){
            System.out.println("IN processForm() - NO ERRORS");
                   
            form.setName(originalFileName);
            model.put("originalFileName", originalFileName);
            try {
                outputStream = new FileOutputStream(new File(filePath));
                outputStream.write(form.getFile().getFileItem().get());
                outputStream.close();
            } catch (Exception e) {
                System.out.println("Error while saving file");
                errors = "Error while saving file";
                model.put("errors", errors);
                return new ModelAndView(SUCCESS_FORM_VIEW, model);
            }
            System.out.println("IN processForm() - will look for success page...");
            return new ModelAndView(SUCCESS_FORM_PAGE, model);
        }else{
            return new ModelAndView(SUCCESS_FORM_VIEW, model);
        }
    }

    /**
     * Wrong file size exception
     * @param arg0
     * @param arg1
     * @param arg2
     * @param exception
     * @return form view
     */
    public ModelAndView resolveException(HttpServletRequest arg0,HttpServletResponse arg1, Object arg2, Exception exception) {
        Map<Object, Object> model = new HashMap<Object, Object>();
        if (exception instanceof MaxUploadSizeExceededException){
            model.put("errors", "File size should be less then "+
            ((MaxUploadSizeExceededException)exception).getMaxUploadSize()+" byte.");
        } else{
            model.put("errors", "Unexpected error: " + exception.getMessage());
        }
        model.put("FORM", new FileUpload());
        return new ModelAndView(SUCCESS_FORM_VIEW, (Map) model);
    }
	
}
