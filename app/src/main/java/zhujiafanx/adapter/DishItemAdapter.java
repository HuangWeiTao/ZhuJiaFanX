package zhujiafanx.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import zhujiafanx.demo.R;
import zhujiafanx.model.DishItem;

/**
 * Created by Administrator on 2015/5/27.
 */
public class DishItemAdapter extends ArrayAdapter<DishItem>{

    private int resourceId;

    public DishItemAdapter(Context context, int resource, DishItem[] objects) {
        super(context, resource, objects);

        resourceId=resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DishItem dishItem = getItem(position);

        if(convertView==null) {

            //以后改用viewholder模式
            LayoutInflater inflater = LayoutInflater.from(getContext());

            convertView =inflater.inflate(resourceId,null);
        }

        //find sub-views
        TextView title=(TextView)convertView.findViewById(R.id.tv_dish_title);
        GridView imageList=(GridView) convertView.findViewById(R.id.gv_image_list);
        TextView publisher=(TextView) convertView.findViewById(R.id.tv_publish_by);
        TextView commentCount=(TextView) convertView.findViewById(R.id.tv_comment_count);
        TextView publishTime=(TextView) convertView.findViewById(R.id.tv_publish_time);

        title.setText(dishItem.getName());
        //imageList=
        publisher.setText(dishItem.getPublishBy());
        commentCount.setText(Integer.toString(dishItem.getCommentCount()));

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        publishTime.setText(dateFormat.format(dishItem.getCreatedTime()));

        return convertView;
    }
}
