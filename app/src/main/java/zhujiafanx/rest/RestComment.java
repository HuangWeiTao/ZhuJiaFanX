package zhujiafanx.rest;

import java.util.UUID;

/**
 * Created by Administrator on 2015/6/15.
 */
public class RestComment {
    public UUID Id;
    public String Title;
    public String Content;
    public Attitude Attitude;
}

enum Attitude
{
    No,
    One,
    Two,
    Three,
    Four,
    Five
}
