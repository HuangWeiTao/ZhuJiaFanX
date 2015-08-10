package zhujiafanx.model.SugarImplementation;

import com.orm.query.Condition;
import com.orm.query.Select;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import javax.inject.Inject;

import zhujiafanx.model.contract.IOnRegisterCallback;
import zhujiafanx.model.contract.IUserClient;
import zhujiafanx.model.contract.LoginInfo;
import zhujiafanx.model.contract.LoginResult;
import zhujiafanx.model.contract.RegisterInfo;
import zhujiafanx.model.contract.User;

/**
 * Created by Administrator on 2015/6/4.
 */
public class SugarUserClient implements IUserClient {


    @Inject
    public SugarUserClient()
    {

    }

    @Override
    public User Get(String userName) {
        return null;
    }

    @Override
    public User Get(long id) {
        SugarUser sugarUser = SugarUser.findById(SugarUser.class, id);

        User user = new User();
        GetModelMapper().map(sugarUser,user);

        return user;
    }

    @Override
    public void Register(RegisterInfo registerInfo, IOnRegisterCallback registerCallback) {
        SugarUser sugarUser = new SugarUser(registerInfo.getEmail(), registerInfo.getPassword());

        sugarUser.save();
    }

    @Override
    public void Update(User user) {
        SugarUser sugarUser=SugarUser.findById(SugarUser.class, Long.parseLong(user.getId()));

        if(sugarUser!=null) {
            GetModelMapper().map(user, sugarUser);
        }
    }

    @Override
    public void Delete(long id) {
        SugarUser user = SugarUser.findById(SugarUser.class, id);

        if (user != null) {
            user.delete();
        }
    }

    @Override
    public LoginResult Login(LoginInfo loginInfo) {
        long count = Select.from(SugarUser.class).where(Condition.prop("name").eq(loginInfo.username), Condition.prop("hashed_pwd").eq(loginInfo.password)).count();

//        //build token
//        if (count > 0) {
//            return Long.toString(count);
//        } else {
//            return "";
//        }
        return null;
    }

    private ModelMapper GetModelMapper()
    {
        ModelMapper mapper=new ModelMapper();

        PropertyMap<SugarUser, User> sugarUserToUserMap=new PropertyMap<SugarUser, User>() {
            @Override
            protected void configure() {
                map().setEmail(source.email);
                map().setLastLogin(source.lastLogin);
                map().setSex(source.sex);
//                map().setName(source.name);
//                map().setHashedPwd(source.hashedPwd);
//                map().setId(source.getId().toString());
//                map().setRegisterDate(source.registerDate);
            }
        };

        PropertyMap<User,SugarUser> userToSugarUserMap=new PropertyMap<User, SugarUser>() {
            @Override
            protected void configure() {
                map(source.getEmail(), destination.email);
            }
        };

        mapper.addMappings(sugarUserToUserMap);
        mapper.addMappings(userToSugarUserMap);

        return mapper;
    }
}
