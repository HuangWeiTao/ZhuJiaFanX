package zhujiafanx.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import zhujiafanx.demo.R;

/**
 * Created by Administrator on 2015/5/28.
 */
public class ImageItemAdapter extends BaseAdapter{
    private Context context;
    private List<String> list;
    private AbsListView.LayoutParams imgSize;

    public ImageItemAdapter(Context context, List<String> list,AbsListView.LayoutParams imgSize) {
        this.context = context;
        this.list = list;
        this.imgSize=imgSize;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ImageView itemView = (ImageView) inflater.inflate(R.layout.listview_image_item, null);
        String info = list.get(position);

        ImageView imageView = (ImageView) itemView
                .findViewById(R.id.iv_image_item);
        imageView.setLayoutParams(new AbsListView.LayoutParams(imgSize.width,imgSize.height));

        //imageView.setImageURI(Uri.parse(info));

        //imageView.setClickable(false);
        //imageView.setActivated(false);
        //imageView.setFocusableInTouchMode(false);

        Picasso.with(context).load(info).resize(imgSize.width,imgSize.height).centerCrop().into(itemView);
        //Glide.with(context).load(info).centerCrop().into(imageView);

        return itemView;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
