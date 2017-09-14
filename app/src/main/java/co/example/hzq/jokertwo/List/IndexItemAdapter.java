package co.example.hzq.jokertwo.List;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import co.example.hzq.jokertwo.R;

/**
 * Created by Hzq on 2017/9/14.
 */

public class IndexItemAdapter extends RecyclerView.Adapter<IndexItemAdapter.ViewHolder>{

    private List<IndexItem> mItemList;

    static class ViewHolder extends  RecyclerView.ViewHolder{
        ImageView image;
        TextView clazz;
        TextView course;
        TextView time;


        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView)itemView.findViewById(R.id.item_imageView);
            clazz = (TextView)itemView.findViewById(R.id.item_text_class);
            course = (TextView)itemView.findViewById(R.id.item_text_course);
            time = (TextView)itemView.findViewById(R.id.item_text_time);
        }
    }

    public IndexItemAdapter(List<IndexItem> ItemList){
        this.mItemList = ItemList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.the_main_list_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        IndexItem indexItem = mItemList.get(position);
        holder.image.setImageResource(indexItem.getItem_imageView());
        holder.clazz.setText(indexItem.getItem_text_class());
        holder.course.setText(indexItem.getItem_text_course());
        holder.time.setText(indexItem.getItem_text_time());
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }
}























