package zhujiafanx.dish;

/**
 * Created by Administrator on 2015/8/22.
 */
public interface IDishBrowsePresenter {
    void LoadingMoreNewerItems(int page, int count);
    void LoadingMoreOlderItems(int page, int count);
}
