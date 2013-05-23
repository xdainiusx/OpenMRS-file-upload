/**
 * DSSInput class
 * Inputs DSS program
 * @author dainius
 */
package org.openmrs.module.dssuploadmodule.web.model;


import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class DSSInput {

    private String name = null;
    private String textarea = null;
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
     * Get textarea field
     * @return 
     */
    public String getTextarea() {
        return this.textarea;
    }

    /**
     * Set textarea field
     * @param textarea 
     */
    public void setTextarea(String textarea) {
        this.textarea = textarea;
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
    
    public String toString(){
        return "in toString()...";
    }
}
