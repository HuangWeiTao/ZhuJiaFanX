package zhujiafanx.server.CommentSystem;

import java.util.UUID;

import zhujiafanx.server.BaseObject;

/**
 * Created by Administrator on 2015/6/7.
 */
public class Comment extends BaseObject<UUID> {
    public String Title;
    public String Description;
    public UUID PublisherId;
}
