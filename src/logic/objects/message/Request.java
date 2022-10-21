package logic.objects.message;

import java.io.Serializable;

import logic.objects.User;
import logic.objects.message.types.RequestType;

public class Request implements Serializable {
    private User user;
    private RequestType requestType;

    public Request(User pUser, RequestType pRequestType) {
        this.user = pUser;
        this.requestType = pRequestType;
    }
    
    // Getters.
    public User getUser() {
        return user;
    }
    public RequestType getRequestType() {
        return requestType;
    }
    // Setters.
    public void setUser(User user) {
        this.user = user;
    }
    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }
}