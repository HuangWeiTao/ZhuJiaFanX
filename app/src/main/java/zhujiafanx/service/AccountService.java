package zhujiafanx.service;

/**
 * Created by Administrator on 2015/8/10.
 */
public interface AccountService {

    public class LoginRequest
    {
        private String username;

        private String password;

        public LoginRequest(String username, String password)
        {
            this.username=username;
            this.password=password;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }

    public class LoginResponse
    {
        private boolean result;

        private int errorCode;

        private String errorMessage;

        private String userName;

        private String userId;

        private String token;

        public boolean getResult() {
            return result;
        }

        public String getToken() {
            return token;
        }

        public String getUserId() {
            return userId;
        }

        public String getUserName() {
            return userName;
        }

        public int getErrorCode() {
            return errorCode;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public LoginResponse(int errorCode, String errorMessage)
        {
            this.result=false;
            this.errorCode=errorCode;
            this.errorMessage=errorMessage;
        }

        public LoginResponse(String userId, String userName, String token)
        {
            this.result=true;
            this.userId=userId;
            this.userName=userName;
            this.token=token;
        }
    }

    public LoginResponse Login(LoginRequest request);

    public class RegisterRequest
    {
        private String username;

        private String password;

        public RegisterRequest(String password, String username) {
            this.password = password;
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public String getUsername() {
            return username;
        }
    }

    public class RegisterResponse
    {
        private boolean result;

        private int errorCode;

        private String errorMessage;

        public RegisterResponse() {
            this.result = true;
        }

        public RegisterResponse(int errorCode, String errorMessage) {
            this.result = false;
            this.errorMessage = errorMessage;
            this.errorCode = errorCode;
        }

        public int getErrorCode() {
            return errorCode;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public boolean getResult() {
            return result;
        }
    }

    public RegisterResponse Register(RegisterRequest request);
}
