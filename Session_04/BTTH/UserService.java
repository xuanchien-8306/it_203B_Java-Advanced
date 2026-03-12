package Session_04.BTTH;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    public List<User> users = new ArrayList<>();
    // yeeu cau 1: them moi
    public void addUser(User user){
        if(user.getUsername().isBlank()){
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        users.add(user);
    }

    // yeu cau 2: tim kiem
    public User findById(int id){
     return users.stream()
             .filter(u -> u.getId() == id)
             .findFirst()
             .orElse(null);
    }

    // yeu cau 3: kiem tra email
    public boolean isValidEmail(String email){
        String regexEmail = "[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,4}";
        return email.matches(regexEmail);
    }
}
