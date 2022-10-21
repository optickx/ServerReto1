package logic.objects.message;

import java.io.Serializable;

import logic.objects.User;
import logic.objects.message.types.ResponseType;

public class Response implements Serializable {
    private User user;
    private ResponseType responseType;

    public Response(User pUser, ResponseType pResponseType) {
        this.user = pUser;
        this.responseType = pResponseType;
    }

    // Getters.
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    // Setters.
    public ResponseType getResponseType() {
        return responseType;
    }
    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }

    
}
