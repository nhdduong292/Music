package duongnh.com.music;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Admin on 1/24/2018.
 */

public class SongAdapter extends ArrayAdapter<Song> {
    private Context mContext;
    private ArrayList<Song> mArrData;
    private LayoutInflater mInflater;
    private ArrayList<Song> mArr;
    private MainActivity main;

    public SongAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Song> objects) {
        super(context, resource, objects);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mArrData = objects;
        mArr = new ArrayList<>();
        mArr.addAll(mArrData);

    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_music, parent, false);
            holder.tvNumber = convertView.findViewById(R.id.tv_number);
            holder.tvName = convertView.findViewById(R.id.tv_name);
            holder.tvHour = convertView.findViewById(R.id.tv_hour);
            holder.ivMore = convertView.findViewById(R.id.iv_more);
            holder.ivClick = convertView.findViewById(R.id.iv_click);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Song s = mArrData.get(position);
        int index = position + 1;
        holder.tvNumber.setText(String.valueOf(index));
        holder.tvName.setText(s.getmName());
        holder.tvHour.setText(getDuration(s.getmDuration()));

        return convertView;
    }

    public String getDuration(int duration) {
        SimpleDateFormat date = new SimpleDateFormat("mm:ss");
        return date.format(new Date(duration));
    }



    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        mArrData.clear();
        if (charText.length() == 0) {
            mArrData.addAll(mArr);
        } else {
            for (Song i : mArr) {
                if (i.getmName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    mArrData.add(i);
                }


            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder {
        public TextView tvNumber, tvName, tvHour;
        public ImageView ivMore, ivClick;
    }
}
