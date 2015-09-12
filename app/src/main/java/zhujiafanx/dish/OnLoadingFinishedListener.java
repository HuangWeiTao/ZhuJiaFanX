package zhujiafanx.dish;

import java.util.ArrayList;

import zhujiafanx.rest.RestDishItem;

/**
 * Created by Administrator on 2015/8/31.
 */
public interface OnLoadingFinishedListener {
    public void onSuccess(ArrayList<RestDishItem>items);

    public void onError();
}
