package zhujiafanx.model;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.StringWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import zhujiafanx.model.contract.IOnRegisterCallback;
import zhujiafanx.model.contract.IUserClient;
import zhujiafanx.model.contract.LoginInfo;
import zhujiafanx.model.contract.LoginResult;
import zhujiafanx.model.contract.RegisterInfo;
import zhujiafanx.model.contract.RegisterResult;
import zhujiafanx.model.contract.User;
import zhujiafanx.utils.IOExtesion;

/**
 * Created by Administrator on 2015/7/4.
 */
public class RestUserClient implements IUserClient {

    private static String baseUri = "http://hweitao1990.xicp.net:3002";

    private IUserService userService;

    public RestUserClient()
    {
        GsonBuilder gson=new GsonBuilder();
        gson.registerTypeAdapter(LoginResult.class,new LoginResultDeserializer());

        userService = new RestAdapter.Builder().setEndpoint(baseUri).setConverter(new GsonConverter(gson.create())).build().create(IUserService.class);
    }


    @Override
    public void Register(RegisterInfo registerInfo, final IOnRegisterCallback registerCallback) {
            userService.Register(registerInfo, new Callback<Response>() {
                @Override
                public void failure(RetrofitError error)
                {
                    String errorMessage="";
                    try {
                        errorMessage= IOExtesion.StreamToString(error.getResponse().getBody().in());
                    }
                    catch (Exception e)
                    {
                        errorMessage="读取注册失败响应流时出错!";
                    }

                    RegisterResult result=new RegisterResult();
                    result.Success=false;
                    result.ErrorMessage=errorMessage;

                    onCallback(result);
                }

                @Override
                public void success(Response response, Response response2)
                {
                    RegisterResult result = new RegisterResult();

                    try {
                        StringWriter stringWriter=new StringWriter();
                        IOUtils.copy(response2.getBody().in(),stringWriter);

                        String responseMessage=stringWriter.toString();

                        result.Success=true;

                        List<String> registerProperties=parseJosnProperties(responseMessage,new ArrayList<String>(Arrays.asList("Id","UserName")));
                        result.UserId=registerProperties.get(0);
                        result.UserName=registerProperties.get(1);
                    }
                    catch (Exception e)
                    {
                        result.Success = false;
                        result.ErrorMessage = "读取注册成功响应流时出错!";
                    }

                    onCallback(result);
                }

                private void onCallback(RegisterResult result) {
                    if (registerCallback != null) {
                        registerCallback.OnFinishRegister(result);
                    }
                }

                private List<String> parseJosnProperties(String jsonLine, List<String> properties)
                {
                    ArrayList<String> result=new ArrayList<String>();

                    JsonElement jsonElement = new JsonParser().parse(jsonLine);
                    JsonObject jObject = jsonElement.getAsJsonObject();

                    for(String property: properties)
                    {
                        result.add(jObject.get(property).getAsString());
                    }

                    return result;
                }
            });
    }

    @Override
    public User Get(String userName) {
        return null;
    }

    @Override
    public User Get(long id) {
        return null;
    }

    @Override
    public void Update(User user) {

    }

    @Override
    public void Delete(long id) {

    }

    @Override
    public LoginResult Login(LoginInfo loginInfo) {
        LoginResult result = userService.Login(loginInfo.username, loginInfo.password, "password");

        return result;
    }

}

interface IUserService
{
    @POST("/api/Account/Register")
    void Register(@Body RegisterInfo info, Callback<Response> callback);

    @FormUrlEncoded
    @POST("/Token")
    LoginResult Login(@Field("username") String username, @Field("password") String password, @Field("grant_type") String grantType);
}

class LoginCallbackResult implements Callback<Response>
{

    public final Object Object=new Object();

    public LoginResult LoginResult = new LoginResult();

    @Override
    public void success(Response response, Response response2) {
        this.LoginResult.Success=true;

        try {
            String message=IOUtils.toString(response.getBody().in());
            this.LoginResult.Token= new JSONObject(message).getString("access_token");

        }
        catch (Exception ex)
        {
            this.LoginResult.Success=false;
            this.LoginResult.ErrorMessage=ex.toString();

            return;
        }
    }

    @Override
    public void failure(RetrofitError error) {
        this.LoginResult.Success=false;


    }
}

class LoginResultDeserializer implements JsonDeserializer<LoginResult> {
    @Override
    public LoginResult deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        LoginResult result = new LoginResult();

        String accessToken = json.getAsJsonObject().get("access_token").getAsString();
        String userName=json.getAsJsonObject().get("userName").getAsString();

        if (accessToken != null || !accessToken.isEmpty()) {
            result.Token = accessToken;
            result.UserName=userName;
            result.Success = true;
        } else {
            result.ErrorMessage = json.getAsString();
            result.Success = false;
        }

        return result;
    }
}
