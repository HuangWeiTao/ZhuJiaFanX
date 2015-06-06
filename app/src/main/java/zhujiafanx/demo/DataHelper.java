package zhujiafanx.demo;

import java.util.ArrayList;

import zhujiafanx.model.DishItem;

/**
 * Created by Administrator on 2015/5/25.
 */
public class DataHelper {

    private static ArrayList<DishItem> dishItems;

    static
    {
        ArrayList<DishItem> dishes=new ArrayList<DishItem>();

//        for(int i=0;i<;i++)
//        {
//            DishItem item = new DishItem();
//            item.setName("This is dissh "+i);
//            item.setDescription("Dish "+i+" is delicious.");
//            item.setPublishBy("author "+i);
//            item.setCommentCount(i);
//            item.setCreatedTime(new Date());
//
//            dishes.add(item);
//        }
    }


    public static ArrayList<DishItem> GetDishItems(int index, int count)
    {
        return new ArrayList<DishItem>( dishItems.subList(index,count));
    }

    public static void SaveDishItem(DishItem item)
    {
        dishItems.add(0,item);
    }
}
