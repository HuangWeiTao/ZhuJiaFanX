package zhujiafanx.control;

import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

import zhujiafanx.demo.R;
import zhujiafanx.fragment.CreateFragment;

/**
 * Created by Administrator on 2015/9/4.
 */
public class ImageSelector extends GridView implements View.OnClickListener {

    public final static int galleryRequestCode=3;

    private int defaultAddResId= R.drawable.button_plus_blue;

    private ArrayList<String> imagePath=new ArrayList<>();
    private ImageSelectorAdapter adapter=new ImageSelectorAdapter(this);

    private ImageView plusView;

    public ImageSelector(Context context) {
        super(context);
    }

    public ImageSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageSelector(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public int getDefaultAddResId() {
        return defaultAddResId;
    }

    public void setDefaultAddResId(int defaultAddResId) {
        this.defaultAddResId = defaultAddResId;
    }

    public ArrayList<String> getImagePath() {
        return imagePath;
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        //临时写法
        Fragment createFragment=((AppCompatActivity)getContext()).getSupportFragmentManager().findFragmentByTag(CreateFragment.TAG);
                createFragment.startActivityForResult(intent, galleryRequestCode);
    }

    public void Init() {
        setAdapter(adapter);

        adapter.add(Integer.toString(defaultAddResId));
        adapter.notifyDataSetChanged();

        setOnHierarchyChangeListener(new OnHierarchyChangeListener() {
            @Override
            public void onChildViewAdded(View parent, View child) {

                plusView=(ImageView)ImageSelector.this.getChildAt(ImageSelector.this.getChildCount()-1);
                plusView.setOnClickListener(ImageSelector.this);

                //test:每次添加新子视图时，之前已经添加过的视图会被重新创建（因为每次都是在最前面插入，导致每次一个新的插入，已有的所有子视图都因为位置改变而被重建了？，如果是加入到最后，还会变吗？）
                StringBuilder builder=new StringBuilder();
                for(int i=0;i<ImageSelector.this.getChildCount();i++)
                {
                    View v = ImageSelector.this.getChildAt(i);
                    builder.append(v.hashCode()+" ");
                }
                Log.d("imageselector",builder.toString());
                //endtest
            }

            @Override
            public void onChildViewRemoved(View parent, View child) {

            }
        });
    }

    public void AddNewItem(String path)
    {
        adapter.insert(path,0);
        adapter.notifyDataSetChanged();
    }
}
