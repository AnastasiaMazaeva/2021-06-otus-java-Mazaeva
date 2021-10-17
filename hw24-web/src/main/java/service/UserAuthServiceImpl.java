package service;


import crm.model.Role;
import crm.service.DBServiceClient;

public class UserAuthServiceImpl implements UserAuthService {

    private final DBServiceClient serviceClient;

    public UserAuthServiceImpl(DBServiceClient serviceClient) {
        this.serviceClient = serviceClient;
    }

    @Override
    public boolean authenticate(String login, String password) {
        return serviceClient.findByLogin(login)
                .map(user -> user.getPassword().equals(password) && Role.ADMIN.equals(user.getRole()))
                .orElse(false);
    }

}
