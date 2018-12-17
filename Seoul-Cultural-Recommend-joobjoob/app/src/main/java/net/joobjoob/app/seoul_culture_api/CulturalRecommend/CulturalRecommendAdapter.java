//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package net.joobjoob.app.seoul_culture_api.CulturalRecommend;

import net.joobjoob.app.seoul_culture_api.Common.CulturalInfo;
import net.joobjoob.app.seoul_culture_api.CulturalPickUp.CockAdapter;
import net.joobjoob.app.seoul_culture_api.CultureEvent.CulturalEventDetail;
import net.joobjoob.app.seoul_culture_api.R.id;
import net.joobjoob.app.seoul_culture_api.R.layout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static net.joobjoob.app.seoul_culture_api.CulturalPickUp.CockAdapter.pick_dataset_list;


// comment: 문화정보 리스트 Adapter
public class CulturalRecommendAdapter extends RecyclerView.Adapter<CulturalRecommendAdapter.ViewHolder> {
    private CulturalRecommendAdapter.ViewHolder viewHolder = null;
    private LayoutInflater inflater = null;
    private Context mContext = null;
    ArrayList<CulturalInfo> recyclerdata;
    CockAdapter cockAdapter;
    //PhotoViewAttacher mAttacher;
    // footer

    private Class<?> classTypeHeader;
    protected Class<?> classTypeBody;
    private Class<?> classTypeFooter;

    public CulturalRecommendAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public CulturalRecommendAdapter(Context mContext, ArrayList<CulturalInfo> recyclerdata) {
        this.mContext = mContext;
        this.recyclerdata = recyclerdata;
        setRecommendList1(recyclerdata);

        // 여기서 값 제대로 받았어 이제 Fragment로 넘겨주자
//        Log.e("이번엔 제대로 가즈아1", String.valueOf(recyclerdata.size()));
//        for (int i = 0; i < recyclerdata.size(); i++) {
//            Log.e("이번엔 제대로 가즈아2", recyclerdata.get(i).getTITLE());
//        }

        Log.e("여기는 레코멘드 1", String.valueOf(recyclerdata));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(layout.cultural_event_list_item_recommend, parent, false);
        ViewHolder myHolder = new ViewHolder(view);
        Log.e("#############", "test");
        Log.e("여기는 레코멘드 2", String.valueOf(recyclerdata));
        return myHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Log.e("여기는 레코멘드 3", String.valueOf(recyclerdata));
        Log.e("testcode", String.valueOf(recyclerdata.size()));
        final CulturalInfo dataModel = recyclerdata.get(position);

        holder.likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                //comment Setting Like Button True
                pick_dataset_list.add(dataModel);
                Log.e("likeButton status 2=>", String.valueOf(holder.likeButton.isLiked()));
                // holder.likeButton.setLiked(true);

                Log.e("likeButton status 3=>", String.valueOf(holder.likeButton.isLiked()));

                Log.e("getCULTCODE LIKE: -> : ", dataModel.getCULTCODE());
                //if(dataModel.getCULTCODE())

                SharedToGSON_Like(dataModel, dataModel.getCULTCODE());
                Log.e("likeButton status 4=>", String.valueOf(holder.likeButton.isLiked()));
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                //comment Like Button FALSE
                //holder.likeButton.setLiked(false);
                Log.e("likeButton status 5=>", String.valueOf(holder.likeButton.isLiked()));

                Log.e("getCULTCODE UNLIKE:>", dataModel.getCULTCODE());
                SharedToGSON_Unlike(dataModel, dataModel.getCULTCODE());

            }
        });

        holder.dDay.setText(getItem(position).getCODENAME());
        System.out.println("getItem(position) : ->" + getItem(position).getSTRTDATE());
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
            Glide.with(this.mContext).load(url).into(holder.mainImg);

        }

        //mAttacher = new PhotoViewAttacher(holder.mainImg);

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // perform your operations here
                //Intent intent = new Intent(mContext, CulturalEventDetail.class);

                Log.e("testcode:", String.valueOf(dataModel));
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
    }

    public CulturalInfo getItem(int position) {
        Log.e("여기는 레코멘드 4", String.valueOf(recyclerdata));
        return recyclerdata.get(position);
    }

    @Override
    public int getItemCount() {
        Log.e("여기는 레코멘드 5", String.valueOf(recyclerdata));
        return recyclerdata.size();

    }

//
//    public int getCount() {
//        return getCount();
//    }

//    public CulturalInfo getItem(int position) {
//        return getItem(position);
////    }
//    public CulturalInfo getItem(int position) {
//        return (CulturalInfo)super.getItem(position);
//    }
//
//    public long getItemId(int position) {
//        return super.getItemId(position);
//    }
//
//    @Override
//    public int getItemCount() {
//        return recyclerdata.size();
//    }

    //    // comment: getView func
//    public View getView(int position, View convertview, ViewGroup parent) {
//        View v = convertview;
//        if(convertview == null) {
//            this.viewHolder = new CulturalListAdapter.ViewHolder();
//            v = this.inflater.inflate(layout.cultural_event_list_item_type, (ViewGroup)null);
//            this.viewHolder.dDay = (TextView)v.findViewById(id.cultural_d_day);
//            this.viewHolder.mainImg = (ImageView)v.findViewById(id.main_img);
//            this.viewHolder.title = (TextView)v.findViewById(id.cultural_title);
//            this.viewHolder.date = (TextView)v.findViewById(id.cultural_date);
//            this.viewHolder.gCode = (TextView)v.findViewById(id.cultural_g_code);
//            v.setTag(this.viewHolder);
//        } else {
//            this.viewHolder = (CulturalListAdapter.ViewHolder)convertview.getTag();
//        }
//
//        this.viewHolder.dDay.setText(this.getDday(this.getItem(position).getSTRTDATE()) + "");
//        this.viewHolder.title.setText(this.getItem(position).getTITLE());
//        this.viewHolder.date.setText(this.getItem(position).getSTRTDATE() + "~" + this.getItem(position).getEND_DATE());
//        this.viewHolder.gCode.setText(this.getItem(position).getGCODE());
//        String url = "";
//        String[] mainImg = this.getItem(position).getMAIN_IMG().split("\\/");
//
//        for(int i = 0; i < mainImg.length; ++i) {
//            if(i != 0 && i != 1 && i != 2) {
//                if(i == mainImg.length - 1) {
//                    url = url + mainImg[i];
//                } else {
//                    url = url + mainImg[i] + "/";
//                }
//            } else {
//                url = url + mainImg[i].toLowerCase() + "/";
//            }
//        }
//
//        if(!this.getItem(position).getMAIN_IMG().equals("")) {
//             //Glide.with(this.mContext).load(url).error(R.drawable.bg_bigimg).into(this.viewHolder.mainImg);
//            // change: bg_bigimg를 인식못해서 일단 주석처리해놓았는데 여기 부분처리할 방법을 알아내야함
//
//            Glide.with(this.mContext).load(url).into(this.viewHolder.mainImg);
//
//        }
//
//        FontUtils.getInstance(this.mContext).setGlobalFont(parent);
//        return v;
//    }
    // comment : GetDate func
    public String getDday(String startDate) {
        try {
            Log.e("여기는 레코멘드 6", String.valueOf(recyclerdata));
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

    public void setFooterView(LinearLayout btnNameLoadMore) {
    }


    // comment: Viewholder Class model
    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mainImg;
        public TextView dDay;
        public TextView title;
        public TextView date;
        public TextView gCode;
        public LikeButton likeButton;

        ViewHolder(View itemView) {
            super(itemView);
            dDay = (TextView) itemView.findViewById(id.cultural_d_day);
            mainImg = (ImageView) itemView.findViewById(id.main_img);
            title = (TextView) itemView.findViewById(id.cultural_title);
            date = (TextView) itemView.findViewById(id.cultural_date);
            gCode = (TextView) itemView.findViewById(id.cultural_g_code);
            likeButton = (LikeButton) itemView.findViewById(id.like_button);
        }
    }

    private void SharedToGSON_Like(CulturalInfo dataModel, String position) {
        //comment Test Code
//        System.out.println("Like_arrList like -> : " + position);
        // 1. 객체를 제이슨으로 변경


        //add - kgh GSON
        Gson gson = new Gson();
        String json = gson.toJson(dataModel);
        System.out.println("######### 1 #########: " + json);


        // add - kgh Shared
//        pref = mContext.getSharedPreferences("pref", MODE_PRIVATE);
//        SharedPreferences.Editor editor = pref.edit();
//        editor.putString(position, String.valueOf(pick_arrlist));
//
//        editor.commit();

//
//        intent = new Intent (mContext, CulturalPickListModel.class);
//        mContext.startActivity(intent);
//            System.out.println("########### 3 #############:" + pick_arrlist.get(0));
//            System.out.println("########### 4 #############:" + pick_arrlist.get(1));


//        System.out.println(pref.getString(position, ""));

//        pick_model_list.add(position, dataModel);
        // comment: SharedPreferences


    }

    private void SharedToGSON_Unlike(CulturalInfo dataModel, String position) {
        //comment Test Code

//        pick_arrlist.set(Integer.parseInt(position),"");

//        System.out.println(pref.getString("Pick_List_Object", ""));
//
//        System.out.println("여기는 언라이크 쉐어드 !  -> : ");
//        System.out.println(pref.getString("Pick_List_Object", ""));

    }


    // add footer

    public void setHeaderView(Class<?> classTypeHeader) {
        this.classTypeHeader = classTypeHeader;
        this.notifyDataSetChanged();
    }

    public void removeHeaderView() {
        this.classTypeHeader = null;
        this.notifyDataSetChanged();
    }

    public void setFooterView(Class<?> classTypeFooter) {
        this.classTypeFooter = classTypeFooter;
        this.notifyDataSetChanged();
    }

    public void removeFooterView(LinearLayout btnNameLoadMore) {
        this.classTypeFooter = null;
        this.notifyDataSetChanged();
    }

    private boolean hasHeader() {
        return classTypeHeader != null;
    }

    private boolean hasFooter() {
        return classTypeFooter != null;
    }

    public void setRecommendList1(ArrayList<CulturalInfo> recommendList) {
        Log.e("여기는 레코멘드 7", String.valueOf(this.recyclerdata));
        this.recyclerdata = recommendList;

    }

    public ArrayList<CulturalInfo> getRecommendList1() {
        Log.e("여기는 레코멘드 8", String.valueOf(recyclerdata));
//        Log.e("Test1=>", pickDataList.get(0).getCODENAME());
        return recyclerdata;
    }


}