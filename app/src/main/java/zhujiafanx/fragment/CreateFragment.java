package zhujiafanx.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;
import zhujiafanx.activity.GetImageActivity;
import zhujiafanx.adapter.ImageItemAdapter;
import zhujiafanx.demo.R;
import zhujiafanx.model.DishClient;
import zhujiafanx.model.DishItem;
import zhujiafanx.model.contract.IDishOperation;

/**
 * Created by Administrator on 2015/5/27.
 */
public class CreateFragment extends Fragment {

    private static final int getImageReqeustCode=1;

    @Optional
    @InjectView(R.id.et_edit_title)
    EditText titleEditText;

    //@InjectView(R.id.et_edit_description)
    //EditText descriptionEditText;

    @Optional
    @InjectView(R.id.btn_get_image)
    ImageButton imageButton;

    @Optional
    @InjectView(R.id.lv_preview)
    ListView previewListView;

    @Optional
    @InjectView(R.id.ib_actionbar_ok)
    ImageButton okButton;

    @Optional
    @InjectView(R.id.ib_actionbar_cancel)
    ImageButton cancelButton;

    private ArrayList<String> imagePathList = new ArrayList<String>();

    private ImageItemAdapter adapter;

    public static CreateFragment newInstance() {

        CreateFragment fragment = new CreateFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    public CreateFragment() {
    }


//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.menu_confirm,menu);
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create,container,false);

        ButterKnife.inject(this, view);


        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //load image list
        adapter = new ImageItemAdapter(getActivity(),imagePathList);
        previewListView.setAdapter(adapter);

        ActionBar actionBar = ((ActionBarActivity)getActivity()).getSupportActionBar();
        View actionBarLayout = setCustomActionBar(actionBar);
        ButterKnife.inject(getActivity(),actionBarLayout);
    }

    //@OnClick(R.id.ib_actionbar_cancel)
    public void onCancelButtonClick(View v) {

    }

    //@OnClick(R.id.ib_actionbar_ok)
    public void onOKButtonClick(View v) {

        //collection all info about dish item and save it
        String dishName=titleEditText.getText().toString();

        //String description = descriptionEditText.getText().toString();

        DishItem dishItem= new DishItem();
        dishItem.setCreatedTime(new Date());
        dishItem.setName(dishName);
        dishItem.setImages(imagePathList);
        dishItem.setPublishBy("anonymous");

        //save it
        IDishOperation dishClient=new DishClient();
        dishClient.SaveDishItem(dishItem);
    }

    //@OnClick(R.id.btn_get_image)
    public void onGetImageButtonClick(View v) {

        Intent intent = new Intent(getActivity(), GetImageActivity.class);
        startActivityForResult(intent, getImageReqeustCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode== Activity.RESULT_OK)
        {
            if(requestCode==getImageReqeustCode) {

                String imagePath  =(String)data.getExtras().get(GetImageActivity.ImagePathConstant);

                //add to the current list
                imagePathList.add(imagePath);

                adapter.notifyDataSetInvalidated();
            }
        }
    }


    private View setCustomActionBar(ActionBar actionBar)
    {
        final ViewGroup actionBarLayout = (ViewGroup) getActivity().getLayoutInflater().inflate(
                R.layout.actionbar_confirm,
                null);

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(actionBarLayout);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.actionbar_bg)));

        return actionBarLayout;
    }
}
