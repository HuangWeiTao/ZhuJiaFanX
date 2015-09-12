package zhujiafanx.fragment.create;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Optional;
import zhujiafanx.app.App;
import zhujiafanx.common.BaseNestedFragment;
import zhujiafanx.common.ErrorMessage;
import zhujiafanx.common.IModelValidator;
import zhujiafanx.control.ImageSelectorFragment;
import zhujiafanx.control.RadioButtonList;
import zhujiafanx.demo.R;
import zhujiafanx.rest.RestDineWay;
import zhujiafanx.rest.RestDishCategory;
import zhujiafanx.rest.RestDishItem;
import zhujiafanx.rest.RestLocation;
import zhujiafanx.utils.DateTimeUtil;
import zhujiafanx.utils.ToastUtil;

/**
 * Created by Administrator on 2015/5/27.
 */
public class DishCreateFragment extends BaseNestedFragment implements IDishCreateView {

    public static final String TAG = DishCreateFragment.class.getSimpleName();

    @Optional
    @InjectView(R.id.et_edit_title)
    EditText titleEditText;

    @InjectView(R.id.et_edit_description)
    EditText descriptionEditText;

    ImageSelectorFragment previewListView;

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
    @InjectView(R.id.rg_dineWays)
    RadioButtonList<RestDineWay> rg_dineWays;

    @Inject
    IDishCreatePresenter dishCreatePresenter;

    AlertDialog datePickerDialog;

    AlertDialog timePickerDialog;

    public static DishCreateFragment newInstance() {

        DishCreateFragment fragment = new DishCreateFragment();

        return fragment;
    }

    public DishCreateFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create, container, false);
        ButterKnife.inject(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Init();
    }

    @Override
    public void setRetainInstance(boolean retain) {
        super.setRetainInstance(retain);
    }

    @Override
    public void onStop() {
        super.onStop();

        if (datePickerDialog != null) {
            datePickerDialog.dismiss();
        }

        if (timePickerDialog != null) {
            datePickerDialog.dismiss();
        }
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new DishCreateModule(this));
    }

    private void Init() {
        //初始化图片上传控件
        previewListView = (ImageSelectorFragment) getChildFragmentManager().findFragmentById(R.id.lv_preview);

        //初始化日期时间控件
        SetDateTime(new Date());

        //载入菜式目录
        dishCreatePresenter.LoadCategories();

        //载入就餐方式
        dishCreatePresenter.LoadingDineWays();
    }

    private Date GetDateTime() {

        return DateTimeUtil.ConvertFromString(tv_ordering_date.getText().toString(), tv_ordering_time.getText().toString());
    }

    private void SetDateTime(Date date) {
        tv_ordering_date.setText(DateTimeUtil.ConvertToDateString(date));
        tv_ordering_time.setText(DateTimeUtil.ConvertToTimeString(date));
    }

    private void ShowDatePicker(Calendar olderCalendar) {

        if (datePickerDialog == null) {
            datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar newCalendar = Calendar.getInstance();
                    newCalendar.set(year, monthOfYear, dayOfMonth);

                    tv_ordering_date.setText(DateTimeUtil.ConvertToDateString(newCalendar));
                }
            }, olderCalendar.get(Calendar.YEAR), olderCalendar.get(Calendar.MONTH), olderCalendar.get(Calendar.DAY_OF_MONTH));
        }

        datePickerDialog.show();
    }

    private void ShowTimePicker(Calendar olderCalendar) {

        if (timePickerDialog == null) {
            timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    Calendar newCalendar = Calendar.getInstance();
                    newCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    newCalendar.set(Calendar.MINUTE, minute);

                    tv_ordering_time.setText(DateTimeUtil.ConvertToTimeString(newCalendar));
                }
            }, olderCalendar.get(Calendar.HOUR_OF_DAY), olderCalendar.get(Calendar.MINUTE), true);
        }

        timePickerDialog.show();
    }

    private RestDishItem FulfillingDishItem()
    {
        RestDishItem restDishItem = new RestDishItem();

        if (catagorySpinner.getSelectedItem() != null) {
            RestDishCategory selectCategory = (RestDishCategory) catagorySpinner.getSelectedItem();
            restDishItem.Category.Id = selectCategory.Id;
        }

        restDishItem.Title = titleEditText.getText().toString();
        restDishItem.Description = descriptionEditText.getText().toString();
        restDishItem.ImageList.clear();
        restDishItem.ImageList.addAll(previewListView.getImagePathList());

        if(!TextUtils.isEmpty(et_minShareCount.getText())) {
            restDishItem.MinShare = Integer.valueOf(et_minShareCount.getText().toString());
        }

        if(!TextUtils.isEmpty(et_maxShareCount.getText())) {
            restDishItem.MaxShare = Integer.valueOf(et_maxShareCount.getText().toString());
        }

        restDishItem.DineWay = rg_dineWays.GetSelectItem();
        restDishItem.ExpiredDate = DateTimeUtil.ConvertFromString(tv_ordering_date.getText().toString(), tv_ordering_time.getText().toString());

        if (App.getItem(App.APP_KEY_LOCATION) != null) {
            restDishItem.Location = (RestLocation) App.getItem(App.APP_KEY_LOCATION);//未改
        }

        return restDishItem;
    }

    @Optional
    @OnClick(R.id.tv_ordering_time)
    public void onOrderingTimeClick(View v) {

        Date current = GetDateTime();
        ShowTimePicker(DateTimeUtil.ConvertToCalender(current));
    }

    @Optional
    @OnClick(R.id.tv_ordering_date)
    public void onOrderingDateClick(View v) {

        Date current = GetDateTime();
        ShowDatePicker(DateTimeUtil.ConvertToCalender(current));
    }

    @Optional
    @OnClick(R.id.ib_actionbar_cancel)
    public void onCancelButtonClick(View v) {

    }

    @Optional
    @OnClick(R.id.ib_actionbar_ok)
    public void onOKButtonClick(View v) {

        RestDishItem restDishItem = FulfillingDishItem();

        IModelValidator.ValidateResult result = restDishItem.Validate();
        if (!result.isSuccess()) {
            Toast.makeText(getActivity(), result.getErrorMessage(), Toast.LENGTH_SHORT).show();
        } else {
            dishCreatePresenter.CreateDishItem(restDishItem);
        }
    }

    @Override
    public void CreateDishItemFail(ErrorMessage error) {
        ToastUtil.Show(getActivity().getBaseContext(), error.getMessage());
    }

    @Override
    public void LoadingCategoriesSuccess(ArrayList<RestDishCategory> categories) {

        ArrayAdapter<RestDishCategory> adapter;

        if (catagorySpinner.getAdapter() == null) {
            adapter = new ArrayAdapter<>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1);
            catagorySpinner.setAdapter(adapter);
        } else {
            adapter = (ArrayAdapter<RestDishCategory>) catagorySpinner.getAdapter();
        }

        adapter.addAll(categories);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void LoadingCategoriesFail(ErrorMessage error) {
        ToastUtil.Show(getActivity().getBaseContext(), error.getMessage());
    }

    @Override
    public void CreateDishItemSuccess() {
        ToastUtil.Show(getActivity().getBaseContext(), getString(R.string.save_success_tip));
    }

    @Override
    public void LoadingDineWaysFail(ErrorMessage errorMessage) {
        ToastUtil.Show(getActivity().getBaseContext(), errorMessage.getMessage());
    }

    @Override
    public void LoadingDineWaysSuccess(ArrayList<RestDineWay> dineWays) {
        rg_dineWays.SetData(dineWays);
    }
}