package co.example.hzq.jokertwo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;

/**
 * Created by Hzq on 2017/7/25.
 */
public class stuAdapter extends BaseAdapter {

    private LinkedList<student> mData;
    private Context mContext;

    public stuAdapter(LinkedList<student> mData,Context mContext){
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);
        ImageView img_icon = (ImageView)convertView.findViewById(R.id.imgtou);
        TextView txt_aName = (TextView)convertView.findViewById(R.id.name);
        TextView txt_aSpeak = (TextView)convertView.findViewById(R.id.says);

        img_icon.setBackgroundResource(mData.get(position).getaIcon());
        txt_aName.setText(mData.get(position).getName());
        txt_aSpeak.setText(mData.get(position).getaSpeak());

        return convertView;
    }
}
