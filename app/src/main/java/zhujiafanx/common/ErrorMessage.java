package zhujiafanx.common;

/**
 * Created by Administrator on 2015/9/7.
 */
public class ErrorMessage {
    private String code;
    private String message;

    public ErrorMessage(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
