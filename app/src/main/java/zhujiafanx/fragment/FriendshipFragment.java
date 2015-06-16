package zhujiafanx.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

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
        ImageView imageView= (ImageView) view.findViewById(R.id.demo_imageView);

        String filePath="file:///android_asset/dish_images/3.jpg";
        //imageView.setImageURI(Uri.parse("file:///android_asset/dish_images/3.jpg"));
        //imageView.setImageResource(R.drawable.ic_action_username);
        Picasso.with(getActivity()).load(filePath).into(imageView);

        return view;

    }

    public static FriendshipFragment newInstance()
    {
        return new FriendshipFragment();
    }
}
