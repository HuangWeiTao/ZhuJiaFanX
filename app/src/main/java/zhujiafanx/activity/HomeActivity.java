package zhujiafanx.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Optional;
import zhujiafanx.control.login.PhoneRegisterFragment;
import zhujiafanx.demo.R;
import zhujiafanx.fragment.CreateFragment;
import zhujiafanx.fragment.PageContainerFragment;
import zhujiafanx.fragment.PersonalFragment;

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

    @Optional
    @InjectView(R.id.main_toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.inject(this);

        Log.i("xxxx", "home activity onCreate.");

        //getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        setSupportActionBar(toolbar);

        LoadDefaultFragment();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Optional
    @OnClick(R.id.rb_personal)
    public void onPersonalButtonClick(View v) {

//        Intent intent = new Intent(this, LoginRegisterActivity.class);
//        startActivity(intent);
        if (!IsFragmentNeedUpdate(getSupportFragmentManager(), R.id.rb_personal)) {
            ReplaceWithFragment(PersonalFragment.newInstance(),"PersonalFragment");
        }
    }

    @Optional
    @OnClick(R.id.rb_menu)
    public void onMenuButtonClick(View v) {

        if (!IsFragmentNeedUpdate(getSupportFragmentManager(), R.id.rb_menu)) {
            ReplaceWithFragment(PageContainerFragment.newInstance(),PageContainerFragment.TAG);

            //reset action bar

        }
    }

    @Optional
    @OnClick(R.id.rb_friendship)
    public void onFriendshipButtonClick(View v) {
        if (!IsFragmentNeedUpdate(getSupportFragmentManager(), R.id.rb_friendship)) {
            ReplaceWithFragment(PhoneRegisterFragment.newInstance(), "PhoneRegisterFragment");
        }
    }

    @Optional
    @OnClick(R.id.rb_create)
    public void onCreateButtonClick(View v) {

        if (!IsFragmentNeedUpdate(getSupportFragmentManager(), R.id.rb_create)) {
            ReplaceWithFragment(CreateFragment.newInstance(),CreateFragment.TAG);
        }
    }

    private Boolean IsFragmentNeedUpdate(FragmentManager manager, int fragmentId) {
        return manager.findFragmentById(fragmentId) != null;
    }

    private void ReplaceWithFragment(Fragment newFragment, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.frl_homeFragment, newFragment, tag);

        transaction.commit();
    }


    private void LoadDefaultFragment() {

        Fragment defaultFragment = getSupportFragmentManager().findFragmentByTag(PageContainerFragment.TAG);

        if (defaultFragment != null && defaultFragment.isDetached()) {

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.attach(defaultFragment);
            transaction.commit();
        }
        else if (defaultFragment == null) {

            defaultFragment = PageContainerFragment.newInstance();

            ReplaceWithFragment(defaultFragment, PageContainerFragment.TAG);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        //Toast.makeText(this, "from home activity", Toast.LENGTH_LONG).show();

        super.onActivityResult(requestCode, resultCode, data);
    }
}
