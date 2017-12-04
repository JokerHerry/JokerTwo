package co.example.hzq.jokertwo.List;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import co.example.hzq.jokertwo.MyMVP.DetailActivity.DetailPageActivity;
import co.example.hzq.jokertwo.R;

/**
 * Created by Hzq on 2017/9/14.
 */



public class IndexItemAdapter extends RecyclerView.Adapter<IndexItemAdapter.ViewHolder> {
    private static final String TAG = "IndexItemAdapter";

    private List<ClassItem> mItemList;

    static class ViewHolder extends  RecyclerView.ViewHolder{
        ImageView image;
        TextView clazz;
        TextView course;
        TextView time;

        public View indexItemView;

        public ViewHolder(View itemView) {
            super(itemView);
            indexItemView = itemView;
            image = (ImageView)itemView.findViewById(R.id.item_imageView);
            clazz = (TextView)itemView.findViewById(R.id.item_text_class);
            course = (TextView)itemView.findViewById(R.id.item_text_course);
            time = (TextView)itemView.findViewById(R.id.item_text_time);
        }
    }

    public IndexItemAdapter(List<ClassItem> ItemList){
        this.mItemList = ItemList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.the_main_list_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);

        holder.indexItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                ClassItem indexitem = mItemList.get(position);
                Toast.makeText(v.getContext(),"oj8k    "+indexitem.getItem_text_class(),Toast.LENGTH_LONG).show();

                Intent intent = new Intent(v.getContext(), DetailPageActivity.class );
                intent.putExtra("image",String.valueOf(indexitem.getItem_imageView()));
                intent.putExtra("class",indexitem.getItem_text_class());
                intent.putExtra("course",indexitem.getItem_text_course());
                intent.putExtra("time",indexitem.getItem_text_time());

                v.getContext().startActivity(intent,
                        ActivityOptions.makeSceneTransitionAnimation((Activity) v.getContext(), holder.image,"image").toBundle());
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ClassItem classItem = mItemList.get(position);
        holder.image.setImageResource(classItem.getItem_imageView());
        holder.clazz.setText(classItem.getItem_text_class());
        holder.course.setText(classItem.getItem_text_course());
        holder.time.setText(classItem.getItem_text_time());

    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }
}























