package zhujiafanx.dish;

import java.util.ArrayList;

import zhujiafanx.rest.RestDishItem;

/**
 * Created by Administrator on 2015/8/22.
 */
public interface IDishBrowseView {

    void ShowNetworkError();

    void StartRotateButton();

    void StopRotateButton();

    void ShowLoadingHeader();

    void HideLoadingHeader();

    void ShowLoadingFooter();

    void HideLoadingFooter();

    void AddItemsToEnd(ArrayList<RestDishItem> items);

    void AddItemsToTop(ArrayList<RestDishItem> items);
}
