package zhujiafanx.rest;

/**
 * Created by Administrator on 2015/6/16.
 */
public enum DishCatagory {
    Tang("靓汤"),
    TianDian("甜点"),
    YinLiao("饮料"),
    XiaoChao("小炒"),
    ShuCai("蔬菜"),
    Zhou("粥"),
    MiFan("米饭"),
    YuChi("鱼翅");

    private String title;

    DishCatagory(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return this.title;
    }
}