package Session_03.BTTH;

import java.util.List;
import java.util.stream.Collectors;

public class UserService {
    public List<User> getVerifiedUsers(List<User> users) {
        return users.stream().filter(User::verified).collect(Collectors.toList());
    }

    public Tier classifyTier(long months){
        return switch ((int) months){
            default ->{
                if (months > 24) yield new Gold();
                else if (months > 12) yield new Silver();
                else yield new Gold();
            }
        };
    }

}
