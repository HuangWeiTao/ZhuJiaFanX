package zhujiafanx.rest;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/7/4.
 */
public class RestDishCategory implements Serializable{
    public String Id;

    public String Name;

    public String Description;

    @Override
    public String toString() {
        return this.Name;
    }
}
