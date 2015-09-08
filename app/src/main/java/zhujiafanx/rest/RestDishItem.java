package zhujiafanx.rest;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2015/6/15.
 */
public class RestDishItem implements Serializable{
    public UUID Id;
    public String Title;
    public String From;
    public UUID PublisherId;
    public RestDishCatagory Catagory;
    public int CommentCount;
    public List<String> ImageList;
    public Date PublishedDate;
    public String Description;
    public Date ExpiredDate;
    public int MaxShare;
    public int MinShare;
    public DineWay DineWay;
    public RestLocation Location = new RestLocation();
}
