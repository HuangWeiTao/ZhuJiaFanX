package zhujiafanx.model;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by Administrator on 2015/5/25.
 */
public class User extends SugarRecord<User> {
    //required fields
    private String name;
    private String hashedPwd;
    private Date registerDate;

    //optional fields
    private Date lastLogin;
    private String email;
    private boolean sex;

    public User(String name, String hashedPwd, Date registerDate) {
        this.name = name;
        this.hashedPwd = hashedPwd;
        this.registerDate = registerDate;
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

    public void setId(Long id)
    {
        //throw new IllegalAccessException("Id couldn't be set!");
    }
}
