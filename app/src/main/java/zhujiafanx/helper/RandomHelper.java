package zhujiafanx.helper;

/**
 * Created by Administrator on 2015/6/15.
 */
public class RandomHelper {
    public static int GetRandomInt(int min, int max) {
        return (int) (min + Math.random() * (max - min));
    }
}
