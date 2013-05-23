/**
 * FileUpload class
 * Uploads file to the directory set in the controller
 * @author dainius
 */
package org.openmrs.module.dssuploadmodule.web.model;


import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class FileUpload {

    private String name = null;
    private CommonsMultipartFile file = null;

    /**
     * Get file name
     * @return name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Set file name
     * @param name 
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Get file
     * @return file
     */
    public CommonsMultipartFile getFile() {
        return this.file;
    }
    
    /**
     * Set file
     * @param file 
     */
    public void setFile(CommonsMultipartFile file) {
        this.file = file;
        this.name = file.getOriginalFilename();
    }
}
