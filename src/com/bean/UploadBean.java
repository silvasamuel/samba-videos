package com.bean;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.model.UploadedFile;

import com.amazon.UploadObjectSingleOperation;

@ManagedBean(name = "upload")
public class UploadBean {

	private UploadedFile file;
	
	public void uploadVideo() throws IOException {
        if(file != null) {
        	UploadObjectSingleOperation objectUpload = new UploadObjectSingleOperation();
        	
        	InputStream fileInputStream = file.getInputstream();
        	objectUpload.upload(new File("C:\\Users\\silva\\Downloads\\Video.mp4"));
            FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
	
	public UploadedFile getFile() {
        return file;
    }
 
    public void setFile(UploadedFile file) {
        this.file = file;
    }
}
