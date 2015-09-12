package zhujiafanx.control;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import zhujiafanx.utils.StringUtil;

/**
 * Created by Administrator on 2015/9/4.
 */
public class ImageSelectorAdapter extends ArrayAdapter {

    private Context context;
    private ImageSelectorFragment selector;
    private ArrayList<String> imagePaths;


    public ImageSelectorAdapter(ImageSelectorFragment selector)
    {
        super(selector.getActivity().getBaseContext(),selector.getDefaultAddResId(),selector.getImagePathList());

        this.context=selector.getActivity().getBaseContext();
        this.selector=selector;
        this.imagePaths=selector.getImagePathList();
    }

    @Override
    public int getCount() {
        return imagePaths.size();
    }

    @Override
    public Object getItem(int position) {
        return imagePaths.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("imageselector","getView");
        ImageView image=new ImageView(context);

        image.setLayoutParams(new AbsListView.LayoutParams(selector.getColumnWidth(), selector.getColumnHeight()));
        if(!StringUtil.isInt(imagePaths.get(position))) {

            Glide.with(context)
                    .load(imagePaths.get(position))
                    .centerCrop()
                    .crossFade()
                    .into(image);

            //image.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //image.setImageURI(Uri.parse(imagePaths.get(position)));
        }
        else
        {
            //用picasso时显示图片有问题
            //Picasso.with(context).load(Integer.valueOf(imagePaths.get(position))).resize(100, 100).centerCrop().into(image);
            image.setImageResource(Integer.valueOf(imagePaths.get(position)));
        }

        return image;
    }

}
