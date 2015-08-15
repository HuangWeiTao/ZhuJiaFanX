package zhujiafanx.app;

import android.content.Context;

import com.orm.SugarApp;

import java.util.HashMap;

import dagger.ObjectGraph;

/**
 * Created by Administrator on 2015/6/5.
 */
public class App extends SugarApp {

    public static App Instance;

    private ObjectGraph objectGraph;

    private static HashMap<String, Object> Items = new HashMap<String, Object>();

    public static String APP_KEY_LOCATION;

    @Override
    public void onCreate() {
        super.onCreate();

        Instance = this;

        Injector.INSTANCE.init(new DefaultModule(this));

        objectGraph = ObjectGraph.create(new DefaultModule(this));
        //objectGraph.inject(this);
    }

    public static Context getContext() {
        return Instance.getApplicationContext();
    }

    public ObjectGraph createScopedGraph(Object... modules) {
        return objectGraph.plus(modules);
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
