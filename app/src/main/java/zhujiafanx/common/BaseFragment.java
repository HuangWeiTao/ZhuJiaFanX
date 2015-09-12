package zhujiafanx.common;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.List;

import dagger.ObjectGraph;
import zhujiafanx.app.App;

/**
 * Created by Administrator on 2015/8/13.
 */
public abstract class BaseFragment extends Fragment {

    private ObjectGraph objectGraph;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getModules() != null) {
            objectGraph = App.Instance.createScopedGraph(getModules().toArray());
            objectGraph.inject(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        objectGraph = null;
    }

    protected abstract List<Object> getModules();
}
