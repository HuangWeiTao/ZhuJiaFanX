package zhujiafanx.rest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import zhujiafanx.app.App;
import zhujiafanx.common.IModelValidator;
import zhujiafanx.demo.R;

/**
 * Created by Administrator on 2015/6/15.
 */
public class RestDishItem implements Serializable, IModelValidator {
    public UUID Id;
    public String Title;
    public String From;
    public UUID PublisherId;
    public final RestDishCategory Category = new RestDishCategory();
    public int CommentCount;
    public final List<String> ImageList = new ArrayList<>();
    public Date PublishedDate;
    public String Description;
    public Date ExpiredDate;
    public int MaxShare;
    public int MinShare;
    public RestDineWay DineWay;
    public RestLocation Location;

    @Override
    public ValidateResult Validate() {

        if (Category.Id == null) {
            return new ValidateResult("Category.Id", App.getContext().getString(R.string.dish_category_not_select_tip));
        }

        int min_share = 1;
        int max_share = 100;
        if ((MinShare < min_share || MaxShare > max_share) || MinShare > MaxShare) {
            return new ValidateResult("MinShare & MaxShare", String.format(App.getContext().getString(R.string.valid_share_range_tip), min_share, max_share));
        }

        if(Location==null)
        {
            return new ValidateResult("Location",App.getContext().getString(R.string.address_not_null_tip));
        }

        return ValidateResult.Success;
    }
}
