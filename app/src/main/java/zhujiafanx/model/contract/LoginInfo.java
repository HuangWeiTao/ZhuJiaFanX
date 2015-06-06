package zhujiafanx.model.contract;

/**
 * Created by Administrator on 2015/6/3.
 */
public class LoginInfo {
    private String username;
    private String password;

    public LoginInfo(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
