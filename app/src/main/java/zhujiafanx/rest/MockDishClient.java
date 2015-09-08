package zhujiafanx.rest;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

import zhujiafanx.demo.R;
import zhujiafanx.helper.RandomHelper;

/**
 * Created by Administrator on 2015/6/15.
 */
public class MockDishClient implements IDishClient {

    private Resources res;

    public MockDishClient(Resources res) {
        this.res = res;
    }

    @Override
    public ArrayList<RestDishItem> GetDishItems(int page, int pageSize) {
        ArrayList<RestDishItem> list = new ArrayList<RestDishItem>();

        for (int i = (page - 1) * pageSize + 1; i <= page * pageSize; i++) {
            RestDishItem item = new RestDishItem();
            item.CommentCount = RandomHelper.GetRandomInt(20, 400);
            item.From = GetRandomPublisher();
            item.Id = UUID.randomUUID();
            item.ImageList = GetRandomImageList();
            item.PublishedDate = GetRandomDate(-7, 0);
            item.Title = i + ". " + GetRandomDishItemName();

            list.add(item);
        }

        Collections.reverse(list);
        return list;
    }

    @Override
    public ArrayList<RestDishCatagory> GetDishCatagory() {
//        ArrayList<DishCatagory> catagories = new ArrayList<DishCatagory>();
//
//        for(DishCatagory catagory : DishCatagory.values())
//        {
//            catagories.add(catagory);
//        }
//
//        return catagories;

        return null;
    }


    @Override
    public void CreateDishItem(RestDishItem dishItem) {

    }

    private String GetRandomPublisher() {
        String[] publisherList = res.getStringArray(R.array.publishers);

        return publisherList[RandomHelper.GetRandomInt(0, publisherList.length - 1)];
    }

    private String GetRandomDishItemName() {
        String[] dishNameList = res.getStringArray(R.array.dishNames);

        return dishNameList[RandomHelper.GetRandomInt(0, dishNameList.length - 1)];
    }

    private ArrayList<String> GetRandomImageList() {
        final int maxImageIndex = 30;
        final String dir = "file:///android_asset/dish_images/";

        int imageCount = RandomHelper.GetRandomInt(2, 6);
        ArrayList<String> fileList = new ArrayList<>(imageCount);


        for (int i = 0; i < imageCount; i++) {
            String file = Integer.toString(RandomHelper.GetRandomInt(1, maxImageIndex)) + ".jpg";
            fileList.add(dir + file);
        }

        return fileList;
    }

    private Date GetRandomDate(int minousDays, int addDays) {
        Calendar calendar = Calendar.getInstance();

        int offset = RandomHelper.GetRandomInt(-minousDays, addDays);

        calendar.add(Calendar.DATE, offset);

        return calendar.getTime();
    }
}

