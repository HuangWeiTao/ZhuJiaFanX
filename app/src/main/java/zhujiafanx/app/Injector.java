package zhujiafanx.app;

import dagger.ObjectGraph;

/**
 * Created by Administrator on 2015/6/5.
 */
public enum Injector {
    INSTANCE;

    private ObjectGraph objectGraph = null;

    public void init(final Object rootModule)
    {

        if(objectGraph == null)
        {
            objectGraph = ObjectGraph.create(rootModule);
        }
        else
        {
            objectGraph = objectGraph.plus(rootModule);
        }

        // Inject statics
        //objectGraph.injectStatics();

    }

    public void init(final Object rootModule, final Object target)
    {
        init(rootModule);
        inject(target);
    }



    public void inject(final Object target)
    {
        objectGraph.inject(target);
    }

    public <T> T resolve(Class<T> type)
    {
        return objectGraph.get(type);
    }
}
