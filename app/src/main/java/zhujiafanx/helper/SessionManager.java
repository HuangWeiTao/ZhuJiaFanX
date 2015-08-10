package zhujiafanx.helper;

import android.content.Context;
import android.content.SharedPreferences;

import zhujiafanx.model.contract.LoginResult;

/**
 * Created by Administrator on 2015/6/3.
 */
public class SessionManager {
    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();

    private SharedPreferences pref;

    private SharedPreferences.Editor editor;
    private Context context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "ZhuJiaFanLogin";

    private static final String KEY_IS_LOGGEDIN = "LoginStatus";

    private static final String KEY_User_Token="UserToken";

    private static final String KEY_User_Name="UserName";

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin(LoginResult loginResult) {

        editor.putBoolean(KEY_IS_LOGGEDIN, loginResult.Success);

        if(loginResult.Success) {
            editor.putString(KEY_User_Token, loginResult.Token);
            editor.putString(KEY_User_Name, loginResult.UserName);
        }
        else
        {
            editor.remove(KEY_User_Token);
            editor.remove(KEY_User_Name);
        }

        editor.commit();
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }

    public String getUserName()
    {
        return pref.getString(KEY_User_Name, "");
    }

    public String getToken()
    {
        return pref.getString(KEY_User_Token,"");
    }
}
