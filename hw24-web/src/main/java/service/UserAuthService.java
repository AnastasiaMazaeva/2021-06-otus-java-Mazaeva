package service;

public interface UserAuthService {
    boolean authenticate(String login, String password);
}
