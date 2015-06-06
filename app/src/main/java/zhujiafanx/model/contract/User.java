package zhujiafanx.model.contract;

import java.util.Date;

/**
 * Created by Administrator on 2015/6/4.
 */
public class User {
    //required fields
    private String id;
    private String name;
    private String hashedPwd;
    private Date registerDate;

    //optional fields
    private Date lastLogin;
    private String email;
    private boolean sex;

//    public User(String name, String hashedPwd, Date registerDate) {
//        this.name = name;
//        this.hashedPwd = hashedPwd;
//        this.registerDate = registerDate;
//    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHashedPwd() {
        return hashedPwd;
    }

    public void setHashedPwd(String hashedPwd) {
        this.hashedPwd = hashedPwd;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public String getId()
    {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public String getEmail() {
        return email;
    }

    public boolean isSex() {
        return sex;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }
}
