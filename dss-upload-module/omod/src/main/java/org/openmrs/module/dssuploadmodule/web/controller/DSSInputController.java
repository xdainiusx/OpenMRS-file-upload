/**
 * DSSUInputController
 * 
 * @author Dainius
 */
package org.openmrs.module.dssuploadmodule.web.controller;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.dssuploadmodule.web.model.DSSInput;
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
@RequestMapping(value="/module/dssupload/inputDSS.form")
public class DSSInputController {
	
	protected final Log log = LogFactory.getLog(getClass());
    /* Note: '/module/dssuploadmodule/' mapped to WebPages directory  */
	private   final String SUCCESS_FORM_VIEW = "/module/dssuploadmodule/jsp/fileUploadForm";
    private   final String SUCCESS_FORM_PAGE = "/module/dssuploadmodule/jsp/dssInputSuccess";
    private   final String uploadDirectory;
    private   final String tempUploadDirectory;
    private   final String fileName;
    private   final String tempFileName;
    private   Integer defaultPatientID;
    
    /**
     * Constructor
     */
    public DSSInputController(){
        this.uploadDirectory = System.getProperty("user.home");
        this.tempUploadDirectory = System.getProperty("java.io.tmpdir");
        this.fileName = "sample.dss";
        this.tempFileName = "temp.dss";
    }

    /**
     * Processes the form
     * @param form
     * @param result
     * @return success view or form view with the error in case of error
     */
	@RequestMapping(value = "/inputDSS", method = RequestMethod.POST)
    public ModelAndView processDSSInput(@ModelAttribute(value="DSS_CODE") DSSInput form, BindingResult result){
        ModelMap model = new ModelMap();
        PrintWriter output;
        String textarea = form.getTextarea();
        String dssErrors = null;
        String filePath = this.uploadDirectory + "/" + this.fileName;
        String tempFilePath = this.tempUploadDirectory + this.tempFileName;
        System.out.println("IN processDSSInput()");
        
        /* 
         * This part is for error checking only - upload the file to temp directory
         * and check for errors, if there are no errors upload to user's home dir
         */
        try {
            output = new PrintWriter(tempFilePath);
            output.println(textarea);
            output.close();
        } catch (Exception e) {
            dssErrors = "Please select a valid file for upload!";
            model.put("errors", dssErrors);
            return new ModelAndView(SUCCESS_FORM_VIEW, model);
        }
        
        model.put("dssErrors", dssErrors);
        if(!result.hasErrors()){
            try {
                output = new PrintWriter(filePath);
                output.println(textarea);
                output.close();
            } catch (FileNotFoundException e) {
                System.out.println("Error while saving dss program");
                return new ModelAndView(SUCCESS_FORM_VIEW, model);
            }
            System.out.println("IN processDSSInput() - will look for success page...");
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
            model.put("dssErrors", "File size should be less then "+
            ((MaxUploadSizeExceededException)exception).getMaxUploadSize()+" byte.");
        } else{
            model.put("dssErrors", "Unexpected error: " + exception.getMessage());
        }
        model.put("FORM", new FileUpload());
        return new ModelAndView(SUCCESS_FORM_VIEW, (Map) model);
    }
	
}
