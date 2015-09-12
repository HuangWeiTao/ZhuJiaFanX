package zhujiafanx.server.UserSystem;

import java.util.Date;
import java.util.UUID;

import zhujiafanx.server.BaseObject;

/**
 * Created by Administrator on 2015/6/7.
 */
public class User extends BaseObject<UUID> {
    public String Name;
    public String Email;
    public String hashedPwd;
    public Date RegisterDate;
    public boolean IsActive;
}
