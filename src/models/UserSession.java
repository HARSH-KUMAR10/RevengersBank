package models;

public class UserSession
{
    private String email;
    private String sessionId;

    public String getEmail()
    {
        return email;
    }

    public String getSessionId()
    {
        return sessionId;
    }

    public UserSession(String email, String sessionId){
        this.email = email;
        this.sessionId = sessionId;
    }
}
