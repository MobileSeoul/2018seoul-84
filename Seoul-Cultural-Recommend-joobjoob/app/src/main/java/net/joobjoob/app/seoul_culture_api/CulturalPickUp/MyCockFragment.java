package net.joobjoob.app.seoul_culture_api.CulturalPickUp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.joobjoob.app.seoul_culture_api.Common.CulturalInfo;
import net.joobjoob.app.seoul_culture_api.CultureEvent.CulturalListAdapter;
import net.joobjoob.app.seoul_culture_api.R;

import java.util.ArrayList;

public class MyCockFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private CockAdapter mAdapter;

    public static ArrayList<CulturalInfo> cockDataList = new ArrayList<>();

    public static Fragment newInstance(int pageNumber) {
        MyCockFragment fragment = new MyCockFragment();
        Bundle args = new Bundle();
        args.putInt("page", pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mycock, container, false);

        String str = "나의 콕!콕! 리스트";
        TextView tv = view.findViewById(R.id.count);

        SpannableStringBuilder ssb = new SpannableStringBuilder(str);
        ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#0092ff")), 0, 11, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(ssb);

        LinearLayoutManager mLayoutManager;
        mRecyclerView = (RecyclerView) view.findViewById(R.id.cultural_listview1);
        //mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //mRecyclerView.scrollToPosition(0);
        //mAdapter = new CockAdapter(getApplicationContext(), CulturalEventSearch.this.culturalInfoArrayList);
        mAdapter = new CockAdapter(this.getActivity(), getCockDataList());
        mRecyclerView.setAdapter(mAdapter);
        //mRecyclerView.setItemAnimator(new DefaultItemAnimator());


//        Log.d("onCreateView Call - > " , "Test");
//
//        String strtext = "";
//

//        try {
//            strtext = getArguments().getString("edttext");
//            if (strtext != null) {
//                Log.e("Test -> ",strtext);
//            }
//        } catch (Exception e) {
//            Log.e("Test -> ", e.toString());
//        }


        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        try {
            CulturalListAdapter culturalListAdapter = new CulturalListAdapter(getContext());
            cockDataList = culturalListAdapter.getPickData();
            if (cockDataList != null) {
                Log.e("test=>", cockDataList.toString());
                Log.e("Test1=>", cockDataList.get(0).getCODENAME());
                setCockDataList(cockDataList);


            } else {
                Log.e("Test1=>", cockDataList.toString());
            }
        } catch (Exception e) {
            Log.e("Test1=>", e.toString());
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initDataset();
    }

    private void initDataset() {
        //for Test


//        mMyData.add(new CulturalInfo(R.drawable.number1, "JOY MINI (48/50)", R.drawable.bg_bigimg));
//        mMyData.add(new CulturalInfo(R.drawable.number2, "RALLYIST (5/50)", R.drawable.bg_bigimg));
//        mMyData.add(new CulturalInfo(R.drawable.number3, "TEST (10/30)", R.drawable.bg_bigimg));
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    public void setCockDataList(ArrayList<CulturalInfo> cockDataList) {
        this.cockDataList = cockDataList;

    }

    public static ArrayList<CulturalInfo> getCockDataList() {
//        Log.e("Test1=>", pickDataList.get(0).getCODENAME());
        return cockDataList;
    }
}