package zhujiafanx.app;

import android.content.Context;

import com.orm.SugarApp;

import java.util.HashMap;

/**
 * Created by Administrator on 2015/6/5.
 */
public class App extends SugarApp {

    private static App Instance;

    private static HashMap<String, Object> Items = new HashMap<String, Object>();

    public static String APP_KEY_LOCATION;

    @Override
    public void onCreate() {
        super.onCreate();

        Instance = this;

        Injector.INSTANCE.init(new DefaultModule(this));
    }

    public static Context getContext() {
        return Instance.getApplicationContext();
    }

    public static Object getItem(String key) {
        if (Items.containsKey(key)) {
            return Items.get(key);
        } else {
            return null;
        }
    }

    public static void setItem(String key, Object value) {
        Items.put(key, value);
    }
}
