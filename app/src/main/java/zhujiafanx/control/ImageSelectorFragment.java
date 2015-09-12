package zhujiafanx.control;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

import zhujiafanx.demo.R;
import zhujiafanx.utils.ImageUtil;

/**
 * Created by Administrator on 2015/9/4.
 */
public class ImageSelectorFragment extends Fragment implements View.OnClickListener {

    private final int galleryRequestCode=3;

    private int defaultAddResId= R.drawable.button_plus_blue;

    private final ArrayList<String> imagePathList =new ArrayList<>();

    private GridView contentView;

    private ImageSelectorAdapter adapter;

    private ImageView plusView;

    private int numColumns=4;
    private int background=R.drawable.white_border;

    public ImageSelectorFragment()
    {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter=new ImageSelectorAdapter(this);
        contentView.setAdapter(adapter);

        adapter.add(Integer.toString(defaultAddResId));
        adapter.notifyDataSetChanged();

        contentView.setOnHierarchyChangeListener(new ViewGroup.OnHierarchyChangeListener() {
            @Override
            public void onChildViewAdded(View parent, View child) {

                plusView = (ImageView) contentView.getChildAt(contentView.getChildCount() - 1);
                plusView.setOnClickListener(ImageSelectorFragment.this);

                //test:每次添加新子视图时，之前已经添加过的视图会被重新创建（因为每次都是在最前面插入，导致每次一个新的插入，已有的所有子视图都因为位置改变而被重建了？，如果是加入到最后，还会变吗？）
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < contentView.getChildCount(); i++) {
                    View v = contentView.getChildAt(i);
                    builder.append(v.hashCode() + " ");
                }
                Log.d("imageselector", builder.toString());
                //endtest
            }

            @Override
            public void onChildViewRemoved(View parent, View child) {

            }
        });
    }

    public void Init(Context context) {

        contentView = new GridView(context);
        Init();
    }

    public void Init(Context context, AttributeSet attrs) {

        contentView = new GridView(context, attrs);
        Init();
    }

    public void Init(Context context, AttributeSet attrs, int defStyleAttr) {
        contentView = new GridView(context, attrs, defStyleAttr);
        Init();
    }

    public int getDefaultAddResId() {
        return defaultAddResId;
    }

    public void setDefaultAddResId(int defaultAddResId) {
        this.defaultAddResId = defaultAddResId;
    }

    public ArrayList<String> getImagePathList() {
        return imagePathList;
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(intent, galleryRequestCode);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Init(inflater.getContext());

        return contentView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode== Activity.RESULT_OK)
        {
            if(requestCode==galleryRequestCode)
            {
                Uri imageUri = data.getData();

                String imageFilePath = ImageUtil.GetFilePath(getActivity().getBaseContext(), imageUri);

                adapter.insert(imageFilePath,0);
                adapter.notifyDataSetChanged();
            }
        }
    }

    private void Init() {

        contentView.setNumColumns(numColumns);
        contentView.setBackground(contentView.getContext().getResources().getDrawable(background));

    }

//    public void AddNewItem(String path)
//    {
//        adapter.insert(path,0);
//        adapter.notifyDataSetChanged();
//    }

    public ArrayList<String> GetImagePathList()
    {
        return this.imagePathList;
    }

    public int getColumnWidth()
    {
        return contentView.getColumnWidth();
    }

    public int getColumnHeight()
    {
        //如何得到列的高度?
        return contentView.getColumnWidth();
    }

    interface OnImageChangeListener
    {
        void OnImageAdded(String path);
        void OnImageRemoved(String path);
    }
}
