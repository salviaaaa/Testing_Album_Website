package util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class FacesContextWrapper {

    public void addMessage(String clientId, FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(clientId, message);
    }
}
