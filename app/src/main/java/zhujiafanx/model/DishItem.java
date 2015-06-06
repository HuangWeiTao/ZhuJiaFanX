package zhujiafanx.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Administrator on 2015/5/25.
 */
public class DishItem {
    private String name;
    private String description;
    private ArrayList<DishCatagory> catagories;
    private ArrayList<String> images;
    private String publishBy;
    private int commentCount;
    private Date createdTime;

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublishBy() {
        return publishBy;
    }

    public void setPublishBy(String publishBy) {
        this.publishBy = publishBy;
    }

    public ArrayList<DishCatagory> getCatagories() {
        return catagories;
    }

    public void setCatagories(ArrayList<DishCatagory> catagories) {
        this.catagories = catagories;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
}
