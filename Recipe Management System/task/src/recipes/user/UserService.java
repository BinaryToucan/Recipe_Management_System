package recipes.user;

public interface UserService {

    void registerUser(RegisterRequest request);

    User findByEmail(String email);

}
