package zhujiafanx.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import zhujiafanx.control.stepControl.StepContainer;
import zhujiafanx.demo.R;

/**
 * Created by Administrator on 2015/5/27.
 */
public class FriendshipFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friendship,container,false);

        StepContainer registerContainer=new StepContainer(getActivity());

        return view;

    }

    public static FriendshipFragment newInstance()
    {
        return new FriendshipFragment();
    }
}
