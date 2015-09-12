package zhujiafanx.control.stepControl;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/9/8.
 */
public class StepContainer extends LinearLayout implements IStepNotify{

    private final String headerTextTag="headerTextTag";

    private ArrayList<StepPart> stepList;
    private int currentStep=-1;
    private boolean initialized=false;

    private LinearLayout headerLayout;
    private FrameLayout contentLayout;

    private int headerItemRes;
    private int selectedHeaderColor;
    private int unselectedHeaderColor;
    private int titleRes;

    public StepContainer(Context context){
        super(context);
        InitLayout();
    }

    public StepContainer(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        InitLayout();
    }

    public StepContainer(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        InitLayout();
    }

    private void InitLayout()
    {
        this.setOrientation(VERTICAL);

        InitHeaderLayout();
        InitContentLayout();
    }

    private void InitHeaderLayout()
    {
        headerLayout=new LinearLayout(getContext());
        LayoutParams params=new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.setMargins(0,15,0,0);
        headerLayout.setLayoutParams(params);
        headerLayout.setOrientation(HORIZONTAL);

        this.addView(headerLayout);
    }

    private void InitContentLayout()
    {
        contentLayout = new FrameLayout(getContext());
        contentLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));

        this.addView(contentLayout);
    }

    public void SetHeaderItem(int headerItemRes) {
        this.headerItemRes = headerItemRes;
    }

    public void SetSelectedHeaderItem(int color)
    {
        this.selectedHeaderColor=color;
    }

    public void SetUnSelectHeaderItem(int color)
    {
        this.unselectedHeaderColor=color;
    }


    public void Add(StepPart step)
    {
        if(initialized)
        {
            throw new IllegalStateException("Add() couldn't be called after Show() is called.");
        }

        if(stepList==null) {
            stepList = new ArrayList<>();
        }

        stepList.add(step);
    }

    public void Build() {
        if (stepList == null || stepList.size() == 0) {
            throw new IllegalStateException("One step should be added at least.");
        }

        for(StepPart step : stepList) {
            headerLayout.addView(BuildStepPartHeader(step.GetIconRes(), step.GetTitle()));
        }

        initialized = true;
        currentStep = -1;

        //onNext();
    }

    private View BuildStepPartHeader(int iconRes, String title) {
        LinearLayout layout = new LinearLayout(getContext());
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        layout.setLayoutParams(params);
        layout.setOrientation(HORIZONTAL);

        if (iconRes != 0) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View icon = inflater.inflate(iconRes, null);
            layout.addView(icon);
        }

        TextView text = new TextView(getContext());
        text.setText(title);
        text.setPadding(0, 0, 10, 0);
        text.setTag(headerTextTag);

        layout.addView(text);

        return layout;
    }

    @Override
    public void onNext(Object data) {
        if (currentStep + 1 < stepList.size()) {
            currentStep++;

            //暂时hard-code title为TextView
            TextView currentTitle = (TextView) headerLayout.getChildAt(currentStep).findViewWithTag(headerTextTag);
            currentTitle.setTextColor(selectedHeaderColor);

            int preStep = currentStep - 1;
            if (preStep >= 0) {
                TextView preTitle = (TextView) headerLayout.getChildAt(preStep).findViewWithTag(headerTextTag);
                preTitle.setTextColor(unselectedHeaderColor);
            }

            contentLayout.removeAllViews();
            StepPart stepPart=stepList.get(currentStep);
            contentLayout.addView(stepPart.GetContentView());

        } else {

            Finish();
        }
    }

    @Override
    public void onPre(Object data) {

    }

    private void Finish()
    {

    }
}
