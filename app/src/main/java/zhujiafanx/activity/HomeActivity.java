package zhujiafanx.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.RadioButton;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Optional;
import zhujiafanx.control.login.LoginRegisterActivity;
import zhujiafanx.demo.R;
import zhujiafanx.fragment.CreateFragment;
import zhujiafanx.fragment.DishFragment;
import zhujiafanx.fragment.FriendshipFragment;

public class HomeActivity extends ActionBarActivity {

    @Optional
    @InjectView(R.id.rb_create)
    RadioButton createButton;

    @Optional
    @InjectView(R.id.rb_friendship)
    RadioButton friendshipButton;

    @Optional
    @InjectView(R.id.rb_menu)
    RadioButton menuButton;

    @Optional
    @InjectView(R.id.rb_personal)
    RadioButton personalButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.inject(this);

        //getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        LoadDefaultFragment();
    }


    @Optional
    @OnClick(R.id.rb_personal)
    public void onPersonalButtonClick(View v) {

        Intent intent = new Intent(this, LoginRegisterActivity.class);
        startActivity(intent);
    }

    @Optional
    @OnClick(R.id.rb_menu)
    public void onMenuButtonClick(View v) {

        if(!IsFragmentNeedUpdate(getSupportFragmentManager(),R.id.rb_menu))
        {
            ReplaceWithFragment(DishFragment.newInstance());

            //reset action bar

        }
    }

    @Optional
    @OnClick(R.id.rb_friendship)
    public void onFriendshipButtonClick(View v) {
        if(!IsFragmentNeedUpdate(getSupportFragmentManager(),R.id.rb_friendship))
        {
            ReplaceWithFragment(FriendshipFragment.newInstance());
        }
    }

    @Optional
    @OnClick(R.id.rb_create)
    public void onCreateButtonClick(View v) {

        if(!IsFragmentNeedUpdate(getSupportFragmentManager(),R.id.rb_create))
        {
            ReplaceWithFragment(CreateFragment.newInstance());
        }
    }

    private Boolean IsFragmentNeedUpdate(FragmentManager manager, int fragmentId)
    {
        return manager.findFragmentById(fragmentId)!=null;
    }

    private void ReplaceWithFragment(Fragment newFragment)
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.frl_homeFragment, newFragment);

        transaction.commit();
    }



    private void LoadDefaultFragment() {

        DishFragment defaultFragment = DishFragment.newInstance();

        ReplaceWithFragment(defaultFragment);
    }
}
