package zhujiafanx.rest;

import zhujiafanx.app.App;
import zhujiafanx.demo.R;

/**
 * Created by Administrator on 2015/9/5.
 */
public enum RestDineWay {
    Home,
    Takeout;

    @Override
    public String toString() {
        if (this == Home) {
            return App.getContext().getString(R.string.at_home_text);
        } else if (this == Takeout) {
            return App.getContext().getString(R.string.delivery_text);
        } else {
            return super.toString();
        }
    }
}
