package zhujiafanx.server.DishSystem;

import java.util.Date;
import java.util.UUID;

import zhujiafanx.server.BaseObject;

/**
 * Created by Administrator on 2015/6/7.
 */
public class DishItem extends BaseObject<UUID> {
    public String Name;
    public String Description;
    public UUID PublisherId;
    public Date PublishDate;
    public boolean IsDeleted;
}
