package zhujiafanx.model;

import java.util.ArrayList;

import zhujiafanx.demo.DataHelper;
import zhujiafanx.model.contract.IDishOperation;

/**
 * Created by Administrator on 2015/5/26.
 */
public class DishClient implements IDishOperation {


    public ArrayList<DishItem> GetDishItems(int page, int count) {

        return DataHelper.GetDishItems(page * count + 1, count);
    }

    @Override
    public boolean SaveDishItem(DishItem item) {

        DataHelper.SaveDishItem(item);

        return true;
    }
}



