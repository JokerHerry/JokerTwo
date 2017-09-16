package co.example.hzq.jokertwo.List;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import co.example.hzq.jokertwo.R;

/**
 * Created by Hzq on 2017/9/16.
 */

public class StuItemAdapter extends RecyclerView.Adapter<StuItemAdapter.ViewHolder>{
    private static final String TAG = "StuItemAdapter";
    private List<StuItem> stuItemList;

    public StuItemAdapter(List<StuItem> ItemList){
        this.stuItemList = ItemList;
    }

    static class ViewHolder extends  RecyclerView.ViewHolder{
        TextView id;
        TextView name;

        public View indexItemView;

        public ViewHolder(View itemView) {
            super(itemView);
            indexItemView = itemView;
            id = (TextView)itemView.findViewById(R.id.stu_item_id);
            name = (TextView)itemView.findViewById(R.id.stu_item_name);

        }
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.the_stu_list_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);

        holder.indexItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: " );
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        StuItem stuItem = stuItemList.get(position);
        holder.id.setText(String.valueOf(stuItem.getId()));
        holder.name.setText(stuItem.getName());
    }

    @Override
    public int getItemCount() {
        return stuItemList.size();
    }

}
