package logic.objects;

public class Message {
    private User user;
    private Action action;

    // Getters.
    public User getUser() {
        return user;
    }
    public Action getAction() {
        return action;
    }

    // Setters.
    public void setUser(User pUser) {
        user = pUser;
    }
    public void setAction(Action pAction) {
        action = pAction;
    }
}