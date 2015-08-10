package zhujiafanx.model.contract;

/**
 * Created by Administrator on 2015/6/4.
 */
public interface IUserClient {
    public User Get(String userName);

    public User Get(long id);

    public void Register(RegisterInfo registerInfo, IOnRegisterCallback callback);

    public void Update(User user);

    public void Delete(long id);

    public LoginResult Login(LoginInfo loginInfo);
}
