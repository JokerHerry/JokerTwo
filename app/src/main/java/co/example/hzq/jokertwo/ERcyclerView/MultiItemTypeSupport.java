package co.example.hzq.jokertwo.ERcyclerView;

/**
 * Created by Hzq on 2017/11/11.
 */

public interface MultiItemTypeSupport<T> {
    int getLayoutId(int itemType);
    int getItemViewType(int position, T t);
}
