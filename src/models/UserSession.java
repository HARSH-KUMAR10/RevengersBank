package models;

public class UserSession
{
    private String email;
    private int sessionId;

    public String getEmail()
    {
        return email;
    }

    public int getSessionId()
    {
        return sessionId;
    }

    public UserSession(String email, int sessionId){
        this.email = email;
        this.sessionId = sessionId;
    }
}
