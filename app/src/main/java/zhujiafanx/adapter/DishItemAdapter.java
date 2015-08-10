package zhujiafanx.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import zhujiafanx.demo.R;
import zhujiafanx.rest.RestDishItem;

/**
 * Created by Administrator on 2015/5/27.
 */
public class DishItemAdapter extends ArrayAdapter<RestDishItem> implements AdapterView.OnItemClickListener{

    private int resourceId;

    private Context context;

    public DishItemAdapter(Context context, int resourceId, ArrayList<RestDishItem> objects) {
        super(context, resourceId, objects);

        this.resourceId = resourceId;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RestDishItem dishItem = getItem(position);

        if (convertView == null) {

            //以后改用viewholder模式
            LayoutInflater inflater = LayoutInflater.from(getContext());

            convertView = inflater.inflate(resourceId, null);
        }

        //find sub-views
        TextView title = (TextView) convertView.findViewById(R.id.tv_dish_title);
        GridView imageList = (GridView) convertView.findViewById(R.id.gv_image_list);
        TextView publisher = (TextView) convertView.findViewById(R.id.tv_publish_by);
        TextView commentCount = (TextView) convertView.findViewById(R.id.tv_comment_count);
        TextView publishTime = (TextView) convertView.findViewById(R.id.tv_publish_time);

        title.setText(dishItem.Title);

        publisher.setText(dishItem.From);
        commentCount.setText(Integer.toString(dishItem.CommentCount));

        if(dishItem.PublishedDate!=null) {
            DateFormat dateFormat = new SimpleDateFormat("yy/MM/dd HH:mm");
            publishTime.setText(dateFormat.format(dishItem.PublishedDate));
        }

        //set Images
        imageList.setAdapter(new ImageItemAdapter(context, dishItem.ImageList));
        imageList.setFocusable(false);
        //imageList.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        imageList.setClickable(false);
        //imageList.setFocusableInTouchMode(false);

        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ((View)view.getParent()).performClick();
    }
}
