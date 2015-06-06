package zhujiafanx.model.contract;

/**
 * Created by Administrator on 2015/6/4.
 */
public interface IUserClient {
    public User Get(String userName);
    public User Get(long id);
    public void Create(RegisterInfo registerInfo);
    public void Update(User user);
    public void Delete(long id);
    public String Login(LoginInfo loginInfo);
}
