package com.bean;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
	private boolean redirectPageStatus = true;
	private InputStream uploadedVideo;
	
	public void uploadVideo() throws IOException, InterruptedException {
        if(file != null) {
        	UploadObjectSingleOperation objectUpload = new UploadObjectSingleOperation();
        	
        	objectUpload.upload(file.getFileName(), file.getContents());
        	
            FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            
            setRedirectPageStatus(false);
        }
    }
	
	public void downloadVideo() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		OutputStream outputStream = externalContext.getResponseOutputStream();
		
		byte [] buffer = new byte[1024];
		int length;
		
		while((length = uploadedVideo.read(buffer)) > 0) {
			outputStream.write(buffer, 0 , length);
		}
		
		context.responseComplete();
	}
	
	public UploadedFile getFile() {
        return file;
    }
 
    public void setFile(UploadedFile file) {
        this.file = file;
    }

	public boolean isRedirectPageStatus() {
		return redirectPageStatus;
	}

	public void setRedirectPageStatus(boolean redirectPageStatus) {
		this.redirectPageStatus = redirectPageStatus;
	}
}
