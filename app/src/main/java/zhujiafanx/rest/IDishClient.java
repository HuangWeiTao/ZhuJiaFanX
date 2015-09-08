package zhujiafanx.rest;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/6/15.
 */
public interface IDishClient {
    public ArrayList<RestDishItem> GetDishItems(int page, int count);
    public ArrayList<RestDishCatagory>  GetDishCatagory();
    public void CreateDishItem(RestDishItem dishItem);
}
