package zhujiafanx.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Optional;
import zhujiafanx.adapter.ImageItemAdapter;
import zhujiafanx.app.App;
import zhujiafanx.app.Injector;
import zhujiafanx.control.ImageSelector;
import zhujiafanx.demo.R;
import zhujiafanx.rest.DineWay;
import zhujiafanx.rest.IDishClient;
import zhujiafanx.rest.RestDishCatagory;
import zhujiafanx.rest.RestDishItem;
import zhujiafanx.rest.RestLocation;
import zhujiafanx.utils.DateExtension;

/**
 * Created by Administrator on 2015/5/27.
 */
public class CreateFragment extends Fragment {

    public static final String TAG="CreateFragment";
    private static final int getImageReqeustCode=1;

    @Optional
    @InjectView(R.id.et_edit_title)
    EditText titleEditText;

    @InjectView(R.id.et_edit_description)
    EditText descriptionEditText;

    @Optional
    @InjectView(R.id.lv_preview)
    ImageSelector previewListView;

    @Optional
    @InjectView(R.id.sp_catagory)
    Spinner catagorySpinner;

    @Optional
    @InjectView(R.id.et_maxShareCount)
    EditText et_maxShareCount;

    @Optional
    @InjectView(R.id.et_minShareCount)
    EditText et_minShareCount;

    @Optional
    @InjectView(R.id.tv_ordering_time)
    TextView tv_ordering_time;

    @Optional
    @InjectView(R.id.tv_ordering_date)
    TextView tv_ordering_date;

    @Optional
    @InjectView(R.id.rg_repastWay)
    RadioGroup rg_repastWay;

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

        InitPictureSelector();

        InitDateTimeControl();

        return view;
    }

    private void InitPictureSelector()
    {
        previewListView.Init();
    }

    private void InitDateTimeControl()
    {
        Date date=new Date();
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat=new SimpleDateFormat("HH:mm");//HH为24小时方式,hh为12小时方式

        tv_ordering_date.setText(dateFormat.format(date));
        tv_ordering_time.setText(timeFormat.format(date));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


//        //load image list
//        imageItemAdapter = new ImageItemAdapter(getActivity(),imagePathList);
//        previewListView.setAdapter(imageItemAdapter);
//
//        ActionBar actionBar = ((ActionBarActivity)getActivity()).getSupportActionBar();
//        View actionBarLayout = setCustomActionBar(actionBar);
//        //ButterKnife.inject(getActivity(),actionBarLayout);
//
//        okButton=(ImageButton) actionBarLayout.findViewById(R.id.ib_actionbar_ok);
//        cancelButton = (ImageButton)actionBarLayout.findViewById(R.id.ib_actionbar_cancel);
//
//        okButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                onOKButtonClick(v);
//            }
//        });
//
//        cancelButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onCancelButtonClick(v);
//            }
//        });

        //get catagory
        catagoryAdapter = new ArrayAdapter<RestDishCatagory>(getActivity(),android.R.layout.simple_list_item_1);
        catagorySpinner.setAdapter(catagoryAdapter);

        new GetCatagoryTask().execute();
    }

    @Optional
    @OnClick(R.id.tv_ordering_time)
    public void onOrderingTimeClick(View v){

        Calendar current=Calendar.getInstance();

        new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                tv_ordering_time.setText(String.format("%d:%d",hourOfDay,minute));
            }
        },current.get(Calendar.HOUR_OF_DAY),current.get(Calendar.MINUTE),true).show();
    }

    @Optional
    @OnClick(R.id.tv_ordering_date)
    public void onOrderingDateClick(View v){
        Calendar current=Calendar.getInstance();
        new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                tv_ordering_date.setText(String.format("%d-%d-%d",year,monthOfYear,dayOfMonth));
            }
        },current.get(Calendar.YEAR),current.get(Calendar.MONTH),current.get(Calendar.DAY_OF_MONTH)).show();
    }

    @Optional
    @OnClick(R.id.ib_actionbar_cancel)
    public void onCancelButtonClick(View v) {

    }

    @Optional
    @OnClick(R.id.ib_actionbar_ok)
    public void onOKButtonClick(View v) {

        imagePathList=previewListView.getImagePath();

        RestDishCatagory selectCatagory = (RestDishCatagory) catagorySpinner.getSelectedItem();
        RestDishItem restDishItem = new RestDishItem();
        restDishItem.Title = titleEditText.getText().toString();
        restDishItem.Description = descriptionEditText.getText().toString();
        restDishItem.ImageList = imagePathList;
        restDishItem.Catagory = selectCatagory;
        restDishItem.MinShare=Integer.valueOf(et_minShareCount.getText().toString());
        restDishItem.MaxShare=Integer.valueOf(et_maxShareCount.getText().toString());
        restDishItem.DineWay=((RadioButton)rg_repastWay.getChildAt(0)).isChecked()?DineWay.Home:DineWay.Takeout;

        restDishItem.ExpiredDate= DateExtension.ConvertFromString(tv_ordering_date.getText().toString() + " " + tv_ordering_time.getText().toString());
        if(restDishItem.ExpiredDate==null)
        {
            restDishItem.ExpiredDate=new Date();
        }

        if(App.getItem(App.APP_KEY_LOCATION)!=null) {
            restDishItem.Location = (RestLocation) App.getItem(App.APP_KEY_LOCATION);
        }

        boolean success=true;
        try {
            dishClient.CreateDishItem(restDishItem);
        }
        catch (Exception e)
        {
            success=false;
        }
        if(success) {
            Toast.makeText(getActivity(), getString(R.string.save_success_tip), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        if(resultCode== Activity.RESULT_OK)
//        {
//            if(requestCode==getImageReqeustCode) {
//
//                String imagePath  =(String)data.getExtras().get(GetImageActivity.ImagePathConstant);
//
//                //add to the current list
//                imagePathList.add(imagePath);
//
//                imageItemAdapter.notifyDataSetInvalidated();
//            }
//        }

        //Toast.makeText(getActivity(),"from create fragment",Toast.LENGTH_LONG).show();
        if(resultCode== Activity.RESULT_OK)
        {
            if(requestCode==ImageSelector.galleryRequestCode) {



                Uri selectedImage=data.getData();

                String newImage = get_gallery_image(selectedImage);
                previewListView.AddNewItem(newImage);
            }
        }
    }

    private String get_gallery_image(Uri imageUri)
    {
        String[] filePathColumn={MediaStore.Images.Media.DATA};

        Cursor cursor = getActivity().getContentResolver().query(imageUri, filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex=cursor.getColumnIndex(filePathColumn[0]);
        String filePath=cursor.getString(columnIndex);

        cursor.close();

        return filePath;
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

            try {
                catagories = dishClient.GetDishCatagory();
            }catch (Exception e)
            {

            }

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
