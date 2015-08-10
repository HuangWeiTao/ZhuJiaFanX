package zhujiafanx.model.contract;

/**
 * Created by Administrator on 2015/6/4.
 */
public class RegisterInfo implements IVerify {

    private String username;
    private String confirmPassword;
    private String password;

    private String errorMessage = "";

    public RegisterInfo(String email, String password, String confirmPassword) {
        this.confirmPassword = confirmPassword;
        this.password = password;
        this.username = email;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getEmail() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String GetErrorMessage() {
        return errorMessage;
    }

    @Override
    public boolean IsValid()
    {
        if (username == null || username.isEmpty()) {
            errorMessage = "用户名不能为空!";
            return false;
        }

        if (password == null) {
            errorMessage = "密码不能为空!";
            return false;
        }

        if (password.length() < 6) {
            errorMessage = "密码不能少于6个字符!";
            return false;
        }

        if (password != confirmPassword) {
            errorMessage = "两次密码输入不一致!";
            return false;
        }

        return true;
    }
}
