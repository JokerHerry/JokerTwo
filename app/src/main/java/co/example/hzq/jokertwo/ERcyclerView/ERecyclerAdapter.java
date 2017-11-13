package co.example.hzq.jokertwo.ERcyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Hzq on 2017/11/10.
 */

public abstract class ERecyclerAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    List<T> fruits;
    Context context;
    int mLayoutId;
    OnItemClickListener mOnItemClickListener;
    OnItemLongClickListener mOnItemLongClickListener;

    public ERecyclerAdapter(List<T> datas,int mLayoutId,Context context){
        this.fruits = datas;
        this.context = context;
        this.mLayoutId = mLayoutId;
    }

    /**
     * 插入对应的item的View
     *
     * 设定holder
     * */
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        final BaseViewHolder holder = BaseViewHolder.get(context,parent,mLayoutId);
        return holder;
    }

    /**
     * 数据传值给View赋值
     */
    @Override
    public void onBindViewHolder(final BaseViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClickListener!=null){
                    mOnItemClickListener.onItemClick(v,holder,position);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(mOnItemLongClickListener!=null){
                    mOnItemLongClickListener.onItemLongClick(v,holder,position);
                }
                return false;
            }
        });

        setParams(holder,  fruits.get(position),position);
    }

    protected abstract void setParams(BaseViewHolder holder, T t,int position);

    /**
     *  返回item条数
     */
    @Override
    public int getItemCount() {
        return fruits.size();
    }


    public interface OnItemClickListener{
        void onItemClick(View view, BaseViewHolder holder, int position);
    }

    public interface OnItemLongClickListener{
        void onItemLongClick(View view, BaseViewHolder holder, int position);
    }

    public  void setOnClickListener(OnItemClickListener onClickListener){
        this.mOnItemClickListener = onClickListener;
    }

    public void setOnLongClickListener(OnItemLongClickListener onLongClickListener){
        this.mOnItemLongClickListener = onLongClickListener;
    }

}
