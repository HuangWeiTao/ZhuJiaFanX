package zhujiafanx.model.contract;

import java.util.ArrayList;

import zhujiafanx.model.DishItem;

public interface IDishOperation
{
    public ArrayList<DishItem> GetDishItems(int page, int count);

    public boolean SaveDishItem(DishItem item);
}