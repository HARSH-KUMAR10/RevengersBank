package model;

public class UserSession
{
    private final String email;
    private final String sessionId;

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
