package zhujiafanx.model.contract;

/**
 * Created by Administrator on 2015/6/4.
 */
public class RegisterInfo {
    private String username;
    private String password;
    private String email;

    public RegisterInfo(String username, String password) {
        this(username, password, null);
    }

    public RegisterInfo(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
