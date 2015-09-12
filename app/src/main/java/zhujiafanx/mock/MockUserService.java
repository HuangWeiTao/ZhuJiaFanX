package zhujiafanx.mock;

import android.text.TextUtils;

import zhujiafanx.service.AccountService;

/**
 * Created by Administrator on 2015/8/10.
 */
public class MockUserService implements AccountService {

    @Override
    public LoginResponse Login(LoginRequest request) {

        try {
            Thread.sleep(1000);
        }
        catch (Exception e)
        {

        }

        //test data
        if(TextUtils.isEmpty(request.getUsername()))
        {
            return new LoginResponse(1,"用户名不能为空");
        }
        else if(TextUtils.isEmpty(request.getPassword()))
        {
            return new LoginResponse(1,"密码不能为空");
        }
        else if(!request.getUsername().equals(request.getPassword()))
        {
            return new LoginResponse(1,"用户名或密码错误");
        }
        else
        {
            return new LoginResponse("123456",request.getUsername(),"cscisicdi213xfvvs");
        }
    }

    @Override
    public RegisterResponse Register(RegisterRequest request)
    {
        try {
            Thread.sleep(5000);
        }
        catch (Exception e)
        {

        }

        //test data
        if(TextUtils.isEmpty(request.getUsername()))
        {
            return new RegisterResponse(1,"用户名不能为空");
        }
        else if(TextUtils.isEmpty(request.getPassword()))
        {
            return new RegisterResponse(1,"密码不能为空");
        }
        else if(!request.getUsername().equals(request.getPassword()))
        {
            return new RegisterResponse(1,"用户名或密码错误");
        }
        else
        {
            return new RegisterResponse();
        }
    }
}
