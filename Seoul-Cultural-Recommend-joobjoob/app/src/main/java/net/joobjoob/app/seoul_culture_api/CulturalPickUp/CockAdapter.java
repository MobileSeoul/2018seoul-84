package net.joobjoob.app.seoul_culture_api.CulturalPickUp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.like.LikeButton;


import net.joobjoob.app.seoul_culture_api.Common.CulturalInfo;
import net.joobjoob.app.seoul_culture_api.CultureEvent.CulturalEventDetail;
import net.joobjoob.app.seoul_culture_api.R;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CockAdapter extends RecyclerView.Adapter<CockAdapter.ViewHolder> {
    public static ArrayList<CulturalInfo> pick_dataset_list;
    private Context mContext = null;
    public MyCockFragment myCockFragment;

//public CockAdapter(CulturalInfo searchDataSet) {
//        mDataset = searchDataSet;
//}

    public CockAdapter(Context context, ArrayList<CulturalInfo> pick_dataset_list) {
        this.mContext = context;
        this.pick_dataset_list = pick_dataset_list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cultural_event_list_item_type, parent, false);
        ViewHolder myHolder = new ViewHolder(view);
        return myHolder;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //mDataset = myCockFragment.getCockDataList();
        //final CulturalInfo dataModel = recyclerdata.get(position);
        final CulturalInfo dataModel = pick_dataset_list.get(position);
        Log.e("dataModel : -> ", String.valueOf(dataModel));
        Log.e("mDataset -> ", pick_dataset_list.toString());
        Log.e("제발 좀나와줘 제발: -> ", "asdfasdfasdfasdfasdf");
        Log.e("Test -> ", "Test2");
        // holder.dDay.setText(getDday(getItem(position).getSTRTDATE()) + "");

        holder.dDay.setText(getItem(position).getCODENAME());
        holder.title.setText(getItem(position).getTITLE());
        holder.date.setText(getItem(position).getSTRTDATE() + " - " + getItem(position).getEND_DATE());
        holder.gCode.setText(getItem(position).getGCODE());

        System.out.println("getItem잘나오나함보자" + getItem(position).getGCODE());

        String url = "";
        String[] mainImg = this.getItem(position).getMAIN_IMG().split("\\/");

        for (int i = 0; i < mainImg.length; ++i) {
            if (i != 0 && i != 1 && i != 2) {
                if (i == mainImg.length - 1) {
                    url = url + mainImg[i];
                } else {
                    url = url + mainImg[i] + "/";
                }
            } else {
                url = url + mainImg[i].toLowerCase() + "/";
            }
        }
        if (!getItem(position).getMAIN_IMG().equals("")) {
            //Glide.with(this.mContext).load(url).error(R.drawable.bg_bigimg).into(this.viewHolder.mainImg);
            // change: bg_bigimg를 인식못해서 일단 주석처리해놓았는데 여기 부분처리할 방법을 알아내야함
            Glide.with(mContext).load(url).into(holder.mainImg);

        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // perform your operations here
                //Intent intent = new Intent(mContext, CulturalEventDetail.class);

                //Log.e("testcode:", String.valueOf(dataModel));
                Intent intent = new Intent(v.getContext(), CulturalEventDetail.class);
//                intent.putExtra("CulturalInfo", (Serializable) dataModel);
                //intent.putExtra("CulturalInfo", (CulturalInfo) getAdapterPosition);
                Bundle bundle = new Bundle();
                bundle.putSerializable("CulturalInfo", (Serializable) dataModel);
                intent.putExtras(bundle);
                //bundle.putSerializable(holder.getAdapterPosition(position));
                //System.out.println(String.valueOf((CulturalInfo)recyclerdata.get(position)));
                //intent.putExtra("CulturalInfo",(CulturalInfo)holder.getAdapterPosition());
                //intent.putExtra("CulturalInfo", (CulturalInfo)Integer.parseInt(String.valueOf(position)));

                //intent.putExtra("CulturalInfo",(CulturalInfo)));

                mContext.startActivity(intent);

            }


        });
//
//    holder.mNumberView.setImageResource(mDataset.get(position).number_img);
//        holder.mTextView.setText(mDataset.get(position).text);
//        holder.mPrivateImage.setImageResource(mDataset.get(position).private_img);
//        holder.mTextView.setOnClickListener(new View.OnClickListener() {
//        }

    }

    @Override
    public int getItemCount() {
        return pick_dataset_list.size();
    }

    public CulturalInfo getItem(int position) {
        return pick_dataset_list.get(position);

    }
//@Override
//public int getItemCount() {
//        return mDataset.size();
//}

    class ViewHolder extends RecyclerView.ViewHolder {
        //    public ImageView mNumberView;
        //    public TextView mTextView;
        //    public ImageView mPrivateImage;

        public ImageView mainImg;
        public TextView dDay;
        public TextView title;
        public TextView date;
        public TextView gCode;
        public LikeButton likeButton;

        public ViewHolder(View view) {
            super(view);
            dDay = (TextView) itemView.findViewById(R.id.cultural_d_day);
            mainImg = (ImageView) itemView.findViewById(R.id.main_img);
            title = (TextView) itemView.findViewById(R.id.cultural_title);
            date = (TextView) itemView.findViewById(R.id.cultural_date);
            gCode = (TextView) itemView.findViewById(R.id.cultural_g_code);
            likeButton = (LikeButton) itemView.findViewById(R.id.like_button);
            //        mNumberView = (ImageView) view.findViewById(R.id.number_image);
            //        mTextView = (TextView) view.findViewById(R.id.search_text);
            //        mPrivateImage = (ImageView) view.findViewById(R.id.private_icon);
        }

    }

    // comment : GetDate func
    public String getDday(String startDate) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date beginDate = formatter.parse(startDate);
            Date currentDay = formatter.parse(formatter.format(new Date()));
            long diff = beginDate.getTime() - currentDay.getTime();
            long diffDays = diff / 86400000L;
            return diffDays <= 0L ? "진행중" : "D-" + diffDays;
        } catch (ParseException var9) {
            var9.printStackTrace();
            return "";
        }
    }


}