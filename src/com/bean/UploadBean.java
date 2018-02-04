package com.bean;

import java.io.IOException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.model.UploadedFile;
import com.amazon.UploadObjectSingleOperation;

@ManagedBean(name = "upload")
@SessionScoped
public class UploadBean {

	private UploadedFile file;
	
	public void uploadVideo() throws IOException, InterruptedException {
        if(file != null) {
        	UploadObjectSingleOperation objectUpload = new UploadObjectSingleOperation();
        	
        	objectUpload.upload(file.getFileName(), file.getContents());
        	
            FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect(objectUpload.getVideoUrl());
        }
    }
	
	public UploadedFile getFile() {
        return file;
    }
 
    public void setFile(UploadedFile file) {
        this.file = file;
    }
}
