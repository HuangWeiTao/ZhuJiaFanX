package zhujiafanx.app;

import com.orm.SugarApp;

/**
 * Created by Administrator on 2015/6/5.
 */
public class App extends SugarApp {

    @Override
    public void onCreate() {
        super.onCreate();

        Injector.INSTANCE.init(new DefaultModule());
    }
}
