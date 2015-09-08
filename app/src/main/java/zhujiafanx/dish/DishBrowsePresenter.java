package zhujiafanx.dish;

import android.os.AsyncTask;

import java.util.ArrayList;

import zhujiafanx.rest.IDishClient;
import zhujiafanx.rest.RestDishItem;

/**
 * Created by Administrator on 2015/8/31.
 */
public class DishBrowsePresenter implements IDishBrowsePresenter {

    private IDishBrowseView browseView;

    private IDishClient dishClient;

    public DishBrowsePresenter(IDishBrowseView browseView, IDishClient dishClient) {
        this.browseView = browseView;
        this.dishClient = dishClient;
    }


    @Override
    public void LoadingMoreNewerItems(int page, int count) {
        this.browseView.StartRotateButton();
        this.browseView.ShowLoadingHeader();
        new DishItemsRequestTask().execute(new DishItemsRequest(page, count));
    }

    @Override
    public void LoadingMoreOlderItems(int page, int count) {
        this.browseView.StartRotateButton();
        this.browseView.HideLoadingFooter();
        new DishItemsRequestTask().execute(new DishItemsRequest(page, count));
    }


    private void LoadingError() {
        //if error is caused by network
        this.browseView.StopRotateButton();
        this.browseView.HideLoadingHeader();
        this.browseView.HideLoadingFooter();
        this.browseView.ShowNetworkError();
    }

    private void LoadingNewerSuccess(ArrayList<RestDishItem> items) {
        this.browseView.StopRotateButton();
        this.browseView.HideLoadingHeader();
        this.browseView.AddItemsToTop(items);
    }

    private void LoadingOlderSuccess(ArrayList<RestDishItem> items) {
        this.browseView.StopRotateButton();
        this.browseView.HideLoadingFooter();
        this.browseView.AddItemsToEnd(items);
    }

    class DishItemsRequestTask extends AsyncTask<DishItemsRequest, Void, Void> {
        int page;
        int count;
        boolean error;
        ArrayList<RestDishItem> dishItems;

        @Override
        protected Void doInBackground(DishItemsRequest... params) {

            try {
                Thread.sleep(2500);
            }
            catch (Exception ex)
            {

            }

            page = params[0].getPage();
            count = params[0].getCount();

            try {
                dishItems = dishClient.GetDishItems(page, count);
                error = false;
            } catch (Exception ex) {
                error = true;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (error) {
                LoadingError();
            } else {
                if (page == 0) {
                    LoadingNewerSuccess(dishItems);
                } else {
                    LoadingOlderSuccess(dishItems);
                }
            }
        }
    }

    static class DishItemsRequest {
        private int page;
        private int count;

        public DishItemsRequest(int page, int count) {
            this.count = count;
            this.page = page;
        }

        public int getPage() {
            return page;
        }

        public int getCount() {
            return count;
        }
    }
}
