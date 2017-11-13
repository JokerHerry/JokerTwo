package co.example.hzq.jokertwo.ERcyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Hzq on 2017/11/10.
 */


/**
 *  找到view的ID 防止每次都findviewbyid
 */

public class BaseViewHolder extends RecyclerView.ViewHolder{

    TextView name ;
    ImageView id;

    SparseArray<View> mViews;
    View inflateView;
    Context context;


    public BaseViewHolder(Context context, View itemView, int redID) {
        super(itemView);

        this.context = context;
        this.inflateView = itemView;
        this.mViews = new SparseArray<View>();
    }

    public static BaseViewHolder get(Context context,ViewGroup parent,int layoutID){

        View inflate = LayoutInflater.from(context).inflate(layoutID, parent,false);
        BaseViewHolder holder = new BaseViewHolder(context,inflate,layoutID);
        return holder;
    }

    public <T extends View> T getView(int viewID){
        View view = mViews.get(viewID);
        if(view == null){
            view = inflateView.findViewById(viewID);
            mViews.put(viewID,view);
        }
        return (T) view;
    }

    /**
     * 辅助方法
     */
    public BaseViewHolder setText(int viewID,String str){
        TextView tv = getView(viewID);
        tv.setText(str);
        return this;
    }

    public BaseViewHolder setImageResource(int viewID,int resID){
        ImageView view = getView(viewID);
        view.setImageResource(resID);
        return this;
    }


}
