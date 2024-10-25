package util;

import javax.faces.application.FacesMessage;

public class FacesUtil {

    private static FacesContextWrapper facesContextWrapper = new FacesContextWrapper();

    public static void setFacesContextWrapper(FacesContextWrapper wrapper) {
        facesContextWrapper = wrapper;
    }

    public static void addSuccessMessage(String message) {
        facesContextWrapper.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", message));
    }

    public static void addErrorMessage(String message) {
        facesContextWrapper.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", message));
    }
}
