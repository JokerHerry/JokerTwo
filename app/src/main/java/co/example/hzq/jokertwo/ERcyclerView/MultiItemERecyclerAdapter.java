package co.example.hzq.jokertwo.ERcyclerView;

import android.content.Context;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Hzq on 2017/11/11.
 */

public abstract class MultiItemERecyclerAdapter<T> extends ERecyclerAdapter<T>{

    MultiItemTypeSupport<T> multiItemTypeSupport;

    public MultiItemERecyclerAdapter(Context context,List<T> datas,MultiItemTypeSupport<T> multiItemTypeSupport){
        super(datas,-1,context);
        this.multiItemTypeSupport = multiItemTypeSupport;
    }

    @Override
    public int getItemViewType(int position) {
        return multiItemTypeSupport.getItemViewType(position,fruits.get(position));
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = multiItemTypeSupport.getLayoutId(viewType);
        BaseViewHolder holder = BaseViewHolder.get(context,parent,layoutId);
        return holder;
    }
}
