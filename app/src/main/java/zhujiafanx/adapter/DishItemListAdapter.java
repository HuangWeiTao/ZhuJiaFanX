package zhujiafanx.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import zhujiafanx.app.App;
import zhujiafanx.demo.R;
import zhujiafanx.rest.RestDishItem;
import zhujiafanx.utils.DateTimeUtil;

/**
 * Created by Administrator on 2015/8/22.
 */
public class DishItemListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.iv_publisherAvatar)
        ImageView iv_publisherAvatar;

        @InjectView(R.id.tv_dishName)
        TextView tv_dishName;

        @InjectView(R.id.tv_publishTime)
        TextView tv_publishTime;

        @InjectView(R.id.hsv_imageList)
        HorizontalScrollView hsv_imageList;

        @InjectView(R.id.ll_imageList)
        LinearLayout ll_imageList;

        public ItemViewHolder(View v) {
            super(v);

            ButterKnife.inject(this, v);
        }
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View v) {
            super(v);
        }
    }

    public static class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View v) {
            super(v);
        }
    }

    private final int Item_VIEW = 0;

    private final int Header_VIEW = 1;

    private final int Footer_VIEW = 2;

    private ArrayList<RestDishItem> dataSet;
    private IItemRowSelectLisenter callback;

    public DishItemListAdapter(IItemRowSelectLisenter callback, ArrayList<RestDishItem> dataSet) {
        this.dataSet = dataSet;
        this.callback = callback;
    }

    @Override
    public int getItemViewType(int position) {
        return dataSet.get(position) != null ? Item_VIEW : Header_VIEW;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        RecyclerView.ViewHolder vh;

        if (viewType == Item_VIEW) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.dish_item_detail, parent, false);

            vh = new ItemViewHolder(v);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RecyclerView listView = ((RecyclerView) v.getParent());
                    int position = listView.getChildAdapterPosition(v);
                    callback.onItemSelect(dataSet.get(position),position);
                }
            });

        } else if (viewType == Header_VIEW) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.loading_header, parent, false);

            vh = new HeaderViewHolder(v);

        } else if (viewType == Footer_VIEW) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.loading_footer, parent, false);

            vh = new FooterViewHolder(v);
        } else {
            throw new UnsupportedOperationException("viewType " + viewType + " is not supported.");
        }

        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemHolder = (ItemViewHolder) holder;

            itemHolder.tv_dishName.setText(dataSet.get(position).Title);
            itemHolder.tv_publishTime.setText(DateTimeUtil.ConvertToDateAndTimeString(dataSet.get(position).PublishedDate));

            itemHolder.ll_imageList.removeAllViews();
            for (String uri : dataSet.get(position).ImageList) {
                itemHolder.ll_imageList.addView(getImageView(uri));
            }
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    private View getImageView(String uri) {

        ImageView imageView = new ImageView(App.getContext());
        LinearLayout.LayoutParams layout=new LinearLayout.LayoutParams(200, 200);
        layout.setMargins(5,0,0,0);
        imageView.setLayoutParams(layout);

        Glide.with(App.getContext()).load(uri).centerCrop().into(imageView);

        return imageView;
    }

    public interface IItemRowSelectLisenter
    {
        void onItemSelect(RestDishItem dishItem, int position);
    }
}
