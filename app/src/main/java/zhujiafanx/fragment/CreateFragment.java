package zhujiafanx.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Optional;
import zhujiafanx.activity.GetImageActivity;
import zhujiafanx.adapter.ImageItemAdapter;
import zhujiafanx.app.App;
import zhujiafanx.app.Injector;
import zhujiafanx.demo.R;
import zhujiafanx.model.DishItem;
import zhujiafanx.rest.IDishClient;
import zhujiafanx.rest.RestDishCatagory;
import zhujiafanx.rest.RestDishItem;
import zhujiafanx.rest.RestLocation;

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

    //@Optional
    //@InjectView(R.id.ib_actionbar_ok)
    ImageButton okButton;

    //@Optional
    //@InjectView(R.id.ib_actionbar_cancel)
    ImageButton cancelButton;

    @Optional
    @InjectView(R.id.sp_catagory)
    Spinner catagorySpinner;

    @Inject
    IDishClient dishClient;


    private ArrayList<String> imagePathList = new ArrayList<String>();

    private ImageItemAdapter imageItemAdapter;

    private ArrayAdapter<RestDishCatagory> catagoryAdapter;

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

        Injector.INSTANCE.inject(this);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //load image list
        imageItemAdapter = new ImageItemAdapter(getActivity(),imagePathList);
        previewListView.setAdapter(imageItemAdapter);

        ActionBar actionBar = ((ActionBarActivity)getActivity()).getSupportActionBar();
        View actionBarLayout = setCustomActionBar(actionBar);
        //ButterKnife.inject(getActivity(),actionBarLayout);

        okButton=(ImageButton) actionBarLayout.findViewById(R.id.ib_actionbar_ok);
        cancelButton = (ImageButton)actionBarLayout.findViewById(R.id.ib_actionbar_cancel);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onOKButtonClick(v);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCancelButtonClick(v);
            }
        });

        //get catagory
        catagoryAdapter = new ArrayAdapter<RestDishCatagory>(getActivity(),android.R.layout.simple_list_item_1);
        catagorySpinner.setAdapter(catagoryAdapter);

        new GetCatagoryTask().execute();
    }

    //@Optional
    //@OnClick(R.id.ib_actionbar_cancel)
    public void onCancelButtonClick(View v) {

    }

    //@Optional
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

        RestDishCatagory selectCatagory=(RestDishCatagory) catagorySpinner.getSelectedItem();

        //save it

        RestDishItem restDishItem = new RestDishItem();
        restDishItem.Title=dishItem.getName();
        restDishItem.ImageList=dishItem.getImages();
        restDishItem.Catagory=new RestDishCatagory();
        //restDishItem.Catagory.Id= UUID.fromString("F504BE1D-9814-4102-ACE2-E7C5E6A27615");
        restDishItem.Catagory.Id=selectCatagory.Id;
        //restDishItem.Catagory.Name="海鲜";
        restDishItem.Location= (RestLocation)App.getItem(App.APP_KEY_LOCATION);

        dishClient.CreateDishItem(restDishItem);

        Toast.makeText(getActivity(),"菜单创建成功!",Toast.LENGTH_SHORT).show();
    }

    @Optional
    @OnClick(R.id.btn_get_image)
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

                imageItemAdapter.notifyDataSetInvalidated();
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

    class GetCatagoryTask extends AsyncTask<Void,Void,Void>
    {
        private ArrayList<RestDishCatagory> catagories;

        @Override
        protected Void doInBackground(Void... params) {

           catagories = dishClient.GetDishCatagory();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //super.onPostExecute(aVoid);

            for(RestDishCatagory catagory : catagories)
            {
                catagoryAdapter.add(catagory);
            }

            catagoryAdapter.notifyDataSetChanged();
        }
    }
}
