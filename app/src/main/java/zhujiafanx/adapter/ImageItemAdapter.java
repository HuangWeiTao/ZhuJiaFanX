package zhujiafanx.adapter;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

import zhujiafanx.demo.R;

/**
 * Created by Administrator on 2015/5/28.
 */
public class ImageItemAdapter extends BaseAdapter {
    private Activity context;
    private List<String> list;

    public ImageItemAdapter(Activity context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View itemView = inflater.inflate(R.layout.listview_image_item, null);
        String info = list.get(position);

        ImageView imageView = (ImageView) itemView
                .findViewById(R.id.iv_image_item);

        imageView.setImageURI(Uri.parse(info));
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
