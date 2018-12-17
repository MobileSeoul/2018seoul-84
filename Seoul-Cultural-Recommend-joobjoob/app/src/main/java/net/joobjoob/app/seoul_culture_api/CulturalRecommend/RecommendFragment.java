package net.joobjoob.app.seoul_culture_api.CulturalRecommend;

import android.arch.lifecycle.ViewModelStore;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.shehabic.droppy.DroppyClickCallbackInterface;
import com.shehabic.droppy.DroppyMenuCustomItem;
import com.shehabic.droppy.DroppyMenuItem;
import com.shehabic.droppy.DroppyMenuPopup;
import com.shehabic.droppy.animations.DroppyFadeInAnimation;
import com.shehabic.droppy.views.DroppyMenuItemTitleView;

import net.joobjoob.app.seoul_culture_api.Common.CulturalInfo;
import net.joobjoob.app.seoul_culture_api.Common.KeywordAddActivity;
import net.joobjoob.app.seoul_culture_api.CulturalPickUp.CockAdapter;
import net.joobjoob.app.seoul_culture_api.CultureEvent.CulturalEventSearch;
import net.joobjoob.app.seoul_culture_api.CultureEvent.CulturalListAdapter;
import net.joobjoob.app.seoul_culture_api.Kakao.KaKaoLoginActivity;
import net.joobjoob.app.seoul_culture_api.R;

import java.util.ArrayList;
import java.util.StringTokenizer;


public class RecommendFragment extends Fragment {
    CheckBox keyword1;
    ImageButton remove1;
    CheckBox keyword2;
    ImageButton remove2;
    CheckBox keyword3;
    ImageButton remove3;
    CheckBox keyword4;
    ImageButton remove4;
    CheckBox keyword5;
    ImageButton remove5;
    CheckBox keyword6;
    ImageButton remove6;
    CheckBox keyword7;
    ImageButton remove7;
    CheckBox keyword8;
    ImageButton remove8;
    CheckBox keyword9;
    ImageButton remove9;
    CheckBox keyword10;
    ImageButton remove10;
    CheckBox keyword11;
    ImageButton remove11;
    CheckBox keyword12;
    ImageButton remove12;
    CheckBox keyword13;
    ImageButton remove13;
    CheckBox keyword14;
    ImageButton remove14;
    CheckBox keyword15;
    ImageButton remove15;
    CheckBox keyword16;
    ImageButton remove16;
    CheckBox keyword17;
    ImageButton remove17;
    CheckBox keyword18;
    ImageButton remove18;

    DroppyMenuPopup droppyMenu;

    // comment 장르선택 이벤트 처리 변수
    private Button category_btn;

    private RecyclerView mRecyclerView;
    private CulturalRecommendAdapter mAdapter;
    public String Keyword;
    public ArrayList<CulturalInfo> recommendList = new ArrayList<>();
    CulturalEventSearch ecs;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ecs = new CulturalEventSearch();


        try {
            CulturalRecommendAdapter culturalListAdapter = new CulturalRecommendAdapter(getContext());
            recommendList = culturalListAdapter.getRecommendList1();
            if (recommendList != null) {
                Log.e("RecommendFragment1 -> ", recommendList.toString());
                Log.e("RecommendFragment2 -> ", recommendList.get(0).getCODENAME());

                setRecommendList1(recommendList);
            } else {
                Log.e("RecommendFragment3", recommendList.toString());
            }
        } catch (Exception e) {
            Log.e("RecommendFragment4", e.toString());
        }

    }

    public String getKeyword() {
        return Keyword;
    }

    public void setKeyword(String keyword) {
        Keyword = keyword;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_recommend, container, false);

        Log.e("RecommendFragment5-> ", recommendList.toString());


        keyword1 = rootView.findViewById(R.id.keyword1);
        remove1 = rootView.findViewById(R.id.remove1);

        keyword2 = rootView.findViewById(R.id.keyword2);
        remove2 = rootView.findViewById(R.id.remove2);

        keyword3 = rootView.findViewById(R.id.keyword3);
        remove3 = rootView.findViewById(R.id.remove3);

        keyword4 = rootView.findViewById(R.id.keyword4);
        remove4 = rootView.findViewById(R.id.remove4);

        keyword5 = rootView.findViewById(R.id.keyword5);
        remove5 = rootView.findViewById(R.id.remove5);

        keyword6 = rootView.findViewById(R.id.keyword6);
        remove6 = rootView.findViewById(R.id.remove6);

        keyword7 = rootView.findViewById(R.id.keyword7);
        remove7 = rootView.findViewById(R.id.remove7);

        keyword8 = rootView.findViewById(R.id.keyword8);
        remove8 = rootView.findViewById(R.id.remove8);

        keyword9 = rootView.findViewById(R.id.keyword9);
        remove9 = rootView.findViewById(R.id.remove9);

        keyword10 = rootView.findViewById(R.id.keyword10);
        remove10 = rootView.findViewById(R.id.remove10);

        keyword11 = rootView.findViewById(R.id.keyword11);
        remove11 = rootView.findViewById(R.id.remove11);

        keyword12 = rootView.findViewById(R.id.keyword12);
        remove12 = rootView.findViewById(R.id.remove12);

        keyword13 = rootView.findViewById(R.id.keyword13);
        remove13 = rootView.findViewById(R.id.remove13);

        keyword14 = rootView.findViewById(R.id.keyword14);
        remove14 = rootView.findViewById(R.id.remove14);

        keyword15 = rootView.findViewById(R.id.keyword15);
        remove15 = rootView.findViewById(R.id.remove15);

        keyword16 = rootView.findViewById(R.id.keyword16);
        remove16 = rootView.findViewById(R.id.remove16);

        keyword17 = rootView.findViewById(R.id.keyword17);
        remove17 = rootView.findViewById(R.id.remove17);

        keyword18 = rootView.findViewById(R.id.keyword18);
        remove18 = rootView.findViewById(R.id.remove18);


        SharedPreferences preferences = this.getActivity().getSharedPreferences("PREF", Context.MODE_PRIVATE);

        String str = preferences.getString("name", "");
        String str2 = str.substring(1, str.length() - 1);
        String[] keyword_list = new String[18];
        keyword_list = str2.split(", ");

        Log.e("TESTEST => ", str2);

        int keyword_size = keyword_list.length;

        for (int i = 0; i < keyword_size; i++) {
            if (keyword_list[i].equals("keyword1")) {
                keyword1.setVisibility(View.VISIBLE);
                remove1.setVisibility(View.VISIBLE);
            }
            if (keyword_list[i].equals("keyword2")) {
                keyword2.setVisibility(View.VISIBLE);
                remove2.setVisibility(View.VISIBLE);
            }
            if (keyword_list[i].equals("keyword3")) {
                keyword3.setVisibility(View.VISIBLE);
                remove3.setVisibility(View.VISIBLE);
            }
            if (keyword_list[i].equals("keyword4")) {
                keyword4.setVisibility(View.VISIBLE);
                remove4.setVisibility(View.VISIBLE);
            }
            if (keyword_list[i].equals("keyword5")) {
                keyword5.setVisibility(View.VISIBLE);
                remove5.setVisibility(View.VISIBLE);
            }
            if (keyword_list[i].equals("keyword6")) {
                keyword6.setVisibility(View.VISIBLE);
                remove6.setVisibility(View.VISIBLE);
            }
            if (keyword_list[i].equals("keyword7")) {
                keyword7.setVisibility(View.VISIBLE);
                remove7.setVisibility(View.VISIBLE);
            }
            if (keyword_list[i].equals("keyword8")) {
                keyword8.setVisibility(View.VISIBLE);
                remove8.setVisibility(View.VISIBLE);
            }
            if (keyword_list[i].equals("keyword9")) {
                keyword9.setVisibility(View.VISIBLE);
                remove9.setVisibility(View.VISIBLE);
            }
            if (keyword_list[i].equals("keyword10")) {
                keyword10.setVisibility(View.VISIBLE);
                remove10.setVisibility(View.VISIBLE);
            }
            if (keyword_list[i].equals("keyword11")) {
                keyword11.setVisibility(View.VISIBLE);
                remove11.setVisibility(View.VISIBLE);
            }
            if (keyword_list[i].equals("keyword12")) {
                keyword12.setVisibility(View.VISIBLE);
                remove12.setVisibility(View.VISIBLE);
            }
            if (keyword_list[i].equals("keyword13")) {
                keyword13.setVisibility(View.VISIBLE);
                remove13.setVisibility(View.VISIBLE);
            }
            if (keyword_list[i].equals("keyword14")) {
                keyword14.setVisibility(View.VISIBLE);
                remove14.setVisibility(View.VISIBLE);
            }
            if (keyword_list[i].equals("keyword15")) {
                keyword15.setVisibility(View.VISIBLE);
                remove15.setVisibility(View.VISIBLE);
            }
            if (keyword_list[i].equals("keyword16")) {
                keyword16.setVisibility(View.VISIBLE);
                remove16.setVisibility(View.VISIBLE);
            }
            if (keyword_list[i].equals("keyword17")) {
                keyword17.setVisibility(View.VISIBLE);
                remove17.setVisibility(View.VISIBLE);
            }
            if (keyword_list[i].equals("keyword18")) {
                keyword18.setVisibility(View.VISIBLE);
                remove18.setVisibility(View.VISIBLE);
            }
        }

        //comment # 1장르선택 액티비티로

        category_btn = rootView.findViewById(R.id.Category_btn);

        category_btn.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {

                initDroppyMenu(category_btn);
                //showDroppyMenu();


            }

            private void initDroppyMenu(final Button category_btn) {

                //category_btn.setTextColor(getResources().getColor(R.color.pressed));

                //new DroppyMenuItemTitleView(category_btn.getContext(),category_btn);
                DroppyMenuPopup.Builder droppyBuilder = new DroppyMenuPopup.Builder(category_btn.getContext(), category_btn);

                droppyBuilder.addMenuItem(new DroppyMenuItem("장르선택"))

                        .addMenuItem(new DroppyMenuItem("공연행사"))
                        .addMenuItem(new DroppyMenuItem("문화"))
                        .addMenuItem(new DroppyMenuItem("행사"))
                        .addMenuItem(new DroppyMenuItem("국악"))
                        .addMenuItem(new DroppyMenuItem("독주"))
                        .addMenuItem(new DroppyMenuItem("독창회"))
                        .addMenuItem(new DroppyMenuItem("문화교양"))
                        .addMenuItem(new DroppyMenuItem("강좌"))
                        .addMenuItem(new DroppyMenuItem("뮤지컬"))
                        .addMenuItem(new DroppyMenuItem("오페라"))
                        .addMenuItem(new DroppyMenuItem("연극"))
                        .addMenuItem(new DroppyMenuItem("영화"))
                        .addMenuItem(new DroppyMenuItem("전시"))
                        .addMenuItem(new DroppyMenuItem("미술"))
                        .addMenuItem(new DroppyMenuItem("축제"))
                        .addMenuItem(new DroppyMenuItem("콘서트"))
                        .addMenuItem(new DroppyMenuItem("클래식"))
                        .addSeparator()
                        //.setPopupAnimation(new DroppyFadeInAnimation())
                        .triggerOnAnchorClick(false);
                droppyBuilder.setOnClick(new DroppyClickCallbackInterface() {
                    @Override
                    public void call(View v, int id) {


                        if (String.valueOf(id).equals("0")) {
                            category_btn.setText("장르선택");


                        }
                        if (String.valueOf(id).equals("1")) {
                            category_btn.setText("공연행사");
                            Keyword = (String) category_btn.getText();
                            Log.e("키워디키워드 ###: ", Keyword);
                            setKeyword(Keyword);
                            Intent in = new Intent(getContext(), CulturalEventSearch.class);
                            in.putExtra("Keyword", Keyword);
                            startActivity(in);

                            //se.RecommendName();

                        }
                        if (String.valueOf(id).equals("2")) {
                            Keyword = (String) category_btn.getText();
                            Log.e("키워디키워드 ###: ", Keyword);
                            setKeyword(Keyword);
                            Intent in = new Intent(getContext(), CulturalEventSearch.class);
                            in.putExtra("Keyword", "문화");
                            startActivity(in);
                        }
                        if (String.valueOf(id).equals("3")) {
                            category_btn.setText("행사");
                            Keyword = (String) category_btn.getText();
                            Log.e("키워디키워드 ###: ", Keyword);
                            setKeyword(Keyword);
                            Intent in = new Intent(getContext(), CulturalEventSearch.class);
                            in.putExtra("Keyword", Keyword);
                            startActivity(in);
                        }
                        if (String.valueOf(id).equals("4")) {
                            category_btn.setText("국악");
                            Keyword = (String) category_btn.getText();
                            Log.e("키워디키워드 ###: ", Keyword);
                            setKeyword(Keyword);
                            Intent in = new Intent(getContext(), CulturalEventSearch.class);
                            in.putExtra("Keyword", Keyword);
                            startActivity(in);
                        }
                        if (String.valueOf(id).equals("5")) {
                            category_btn.setText("독주");
                            Keyword = (String) category_btn.getText();
                            Log.e("키워디키워드 ###: ", Keyword);
                            setKeyword(Keyword);
                            Intent in = new Intent(getContext(), CulturalEventSearch.class);
                            in.putExtra("Keyword", Keyword);
                            startActivity(in);
                        }
                        if (String.valueOf(id).equals("6")) {
                            category_btn.setText("독창회");
                            Keyword = (String) category_btn.getText();
                            Log.e("키워디키워드 ###: ", Keyword);
                            setKeyword(Keyword);
                            Intent in = new Intent(getContext(), CulturalEventSearch.class);
                            in.putExtra("Keyword", Keyword);
                            startActivity(in);
                        }
                        if (String.valueOf(id).equals("7")) {
                            category_btn.setText("문화교양");
                            Keyword = (String) category_btn.getText();
                            Log.e("키워디키워드 ###: ", Keyword);
                            setKeyword(Keyword);
                            Intent in = new Intent(getContext(), CulturalEventSearch.class);
                            in.putExtra("Keyword", Keyword);
                            startActivity(in);
                        }
                        if (String.valueOf(id).equals("8")) {
                            category_btn.setText("강좌");
                            Keyword = (String) category_btn.getText();
                            Log.e("키워디키워드 ###: ", Keyword);
                            setKeyword(Keyword);
                            Intent in = new Intent(getContext(), CulturalEventSearch.class);
                            in.putExtra("Keyword", Keyword);
                            startActivity(in);
                        }
                        if (String.valueOf(id).equals("9")) {
                            category_btn.setText("뮤지컬");
                            Keyword = (String) category_btn.getText();
                            Log.e("키워디키워드 ###: ", Keyword);
                            setKeyword(Keyword);
                            Intent in = new Intent(getContext(), CulturalEventSearch.class);
                            in.putExtra("Keyword", Keyword);
                            startActivity(in);
                        }
                        if (String.valueOf(id).equals("10")) {
                            category_btn.setText("오페라");
                            Keyword = (String) category_btn.getText();
                            Log.e("키워디키워드 ###: ", Keyword);
                            setKeyword(Keyword);
                            Intent in = new Intent(getContext(), CulturalEventSearch.class);
                            in.putExtra("Keyword", Keyword);
                            startActivity(in);
                        }
                        if (String.valueOf(id).equals("11")) {
                            category_btn.setText("연극");
                            Keyword = (String) category_btn.getText();
                            Log.e("키워디키워드 ###: ", Keyword);
                            setKeyword(Keyword);
                            Intent in = new Intent(getContext(), CulturalEventSearch.class);
                            in.putExtra("Keyword", Keyword);
                            startActivity(in);
                        }
                        if (String.valueOf(id).equals("12")) {
                            category_btn.setText("영화");
                            Keyword = (String) category_btn.getText();
                            Log.e("키워디키워드 ###: ", Keyword);
                            setKeyword(Keyword);
                            Intent in = new Intent(getContext(), CulturalEventSearch.class);
                            in.putExtra("Keyword", Keyword);
                            startActivity(in);
                        }
                        if (String.valueOf(id).equals("13")) {
                            category_btn.setText("전시");
                            Keyword = (String) category_btn.getText();
                            Log.e("키워디키워드 ###: ", Keyword);
                            setKeyword(Keyword);
                            Intent in = new Intent(getContext(), CulturalEventSearch.class);
                            in.putExtra("Keyword", Keyword);
                            startActivity(in);
                        }
                        if (String.valueOf(id).equals("14")) {
                            category_btn.setText("미술");
                            Keyword = (String) category_btn.getText();
                            Log.e("키워디키워드 ###: ", Keyword);
                            setKeyword(Keyword);
                            Intent in = new Intent(getContext(), CulturalEventSearch.class);
                            in.putExtra("Keyword", Keyword);
                            startActivity(in);
                        }
                        if (String.valueOf(id).equals("15")) {
                            category_btn.setText("축제");
                            Keyword = (String) category_btn.getText();
                            Log.e("키워디키워드 ###: ", Keyword);
                            setKeyword(Keyword);
                            Intent in = new Intent(getContext(), CulturalEventSearch.class);
                            in.putExtra("Keyword", Keyword);
                            startActivity(in);
                        }
                        if (String.valueOf(id).equals("16")) {
                            category_btn.setText("콘서트");
                            Keyword = (String) category_btn.getText();
                            Log.e("키워디키워드 ###: ", Keyword);
                            setKeyword(Keyword);
                            Intent in = new Intent(getContext(), CulturalEventSearch.class);
                            in.putExtra("Keyword", Keyword);
                            startActivity(in);
                        }
                        if (String.valueOf(id).equals("17")) {
                            category_btn.setText("클래식");
                            Keyword = (String) category_btn.getText();
                            Log.e("키워디키워드 ###: ", Keyword);
                            setKeyword(Keyword);
                            Intent in = new Intent(getContext(), CulturalEventSearch.class);
                            in.putExtra("Keyword", Keyword);
                            startActivity(in);
                        }


//                        ces.Viewpager_Setting();
                        //ecs.Reload_fragment();
//                        Keyword = "콘서트";

//                        se.RecommendName();
                        Log.d("Clicked on ", String.valueOf(id));

                    }
                });


                droppyMenu = droppyBuilder.build();
                droppyMenu.show();
            }
        });

        final ImageButton addKeyword = rootView.findViewById(R.id.addKeyword);

        addKeyword.setOnClickListener(
                new ImageButton.OnClickListener() {
                    public void onClick(View v) {
                        final Intent intent = new Intent(getActivity(), KeywordAddActivity.class);
                        startActivity(intent);
                    }
                }
        );

        remove1.setOnClickListener(
                new TextView.OnClickListener() {
                    public void onClick(View v) {
                        keyword1.setVisibility(View.GONE);
                        remove1.setVisibility(View.GONE);

                        LinearLayout.LayoutParams mLayoutParams = (LinearLayout.LayoutParams) keyword2.getLayoutParams();
                        mLayoutParams.leftMargin = 0;
                        keyword2.setLayoutParams(mLayoutParams);
                    }
                }
        );

        remove2.setOnClickListener(
                new TextView.OnClickListener() {
                    public void onClick(View v) {
                        keyword2.setVisibility(View.GONE);
                        remove2.setVisibility(View.GONE);

                        LinearLayout.LayoutParams mLayoutParams = (LinearLayout.LayoutParams) keyword3.getLayoutParams();
                        mLayoutParams.leftMargin = 0;
                        keyword3.setLayoutParams(mLayoutParams);
                    }
                }
        );

        remove3.setOnClickListener(
                new TextView.OnClickListener() {
                    public void onClick(View v) {
                        keyword3.setVisibility(View.GONE);
                        remove3.setVisibility(View.GONE);

                        LinearLayout.LayoutParams mLayoutParams = (LinearLayout.LayoutParams) keyword4.getLayoutParams();
                        mLayoutParams.leftMargin = 0;
                        keyword4.setLayoutParams(mLayoutParams);
                    }
                }
        );

        remove4.setOnClickListener(
                new TextView.OnClickListener() {
                    public void onClick(View v) {
                        keyword4.setVisibility(View.GONE);
                        remove4.setVisibility(View.GONE);

                        LinearLayout.LayoutParams mLayoutParams = (LinearLayout.LayoutParams) keyword5.getLayoutParams();
                        mLayoutParams.leftMargin = 0;
                        keyword5.setLayoutParams(mLayoutParams);
                    }
                }
        );

        remove5.setOnClickListener(
                new TextView.OnClickListener() {
                    public void onClick(View v) {
                        keyword5.setVisibility(View.GONE);
                        remove5.setVisibility(View.GONE);

                        LinearLayout.LayoutParams mLayoutParams = (LinearLayout.LayoutParams) keyword6.getLayoutParams();
                        mLayoutParams.leftMargin = 0;
                        keyword6.setLayoutParams(mLayoutParams);
                    }
                }
        );

        remove6.setOnClickListener(
                new TextView.OnClickListener() {
                    public void onClick(View v) {
                        keyword6.setVisibility(View.GONE);
                        remove6.setVisibility(View.GONE);

                        LinearLayout.LayoutParams mLayoutParams = (LinearLayout.LayoutParams) keyword7.getLayoutParams();
                        mLayoutParams.leftMargin = 0;
                        keyword7.setLayoutParams(mLayoutParams);
                    }
                }
        );

        remove7.setOnClickListener(
                new TextView.OnClickListener() {
                    public void onClick(View v) {
                        keyword7.setVisibility(View.GONE);
                        remove7.setVisibility(View.GONE);

                        LinearLayout.LayoutParams mLayoutParams = (LinearLayout.LayoutParams) keyword8.getLayoutParams();
                        mLayoutParams.leftMargin = 0;
                        keyword8.setLayoutParams(mLayoutParams);
                    }
                }
        );

        remove8.setOnClickListener(
                new TextView.OnClickListener() {
                    public void onClick(View v) {
                        keyword8.setVisibility(View.GONE);
                        remove8.setVisibility(View.GONE);

                        LinearLayout.LayoutParams mLayoutParams = (LinearLayout.LayoutParams) keyword9.getLayoutParams();
                        mLayoutParams.leftMargin = 0;
                        keyword9.setLayoutParams(mLayoutParams);
                    }
                }
        );

        remove9.setOnClickListener(
                new TextView.OnClickListener() {
                    public void onClick(View v) {
                        keyword9.setVisibility(View.GONE);
                        remove9.setVisibility(View.GONE);

                        LinearLayout.LayoutParams mLayoutParams = (LinearLayout.LayoutParams) keyword10.getLayoutParams();
                        mLayoutParams.leftMargin = 0;
                        keyword10.setLayoutParams(mLayoutParams);
                    }
                }
        );

        remove10.setOnClickListener(
                new TextView.OnClickListener() {
                    public void onClick(View v) {
                        keyword10.setVisibility(View.GONE);
                        remove10.setVisibility(View.GONE);

                        LinearLayout.LayoutParams mLayoutParams = (LinearLayout.LayoutParams) keyword11.getLayoutParams();
                        mLayoutParams.leftMargin = 0;
                        keyword11.setLayoutParams(mLayoutParams);
                    }
                }
        );

        remove11.setOnClickListener(
                new TextView.OnClickListener() {
                    public void onClick(View v) {
                        keyword11.setVisibility(View.GONE);
                        remove11.setVisibility(View.GONE);

                        LinearLayout.LayoutParams mLayoutParams = (LinearLayout.LayoutParams) keyword12.getLayoutParams();
                        mLayoutParams.leftMargin = 0;
                        keyword12.setLayoutParams(mLayoutParams);
                    }
                }
        );

        remove12.setOnClickListener(
                new TextView.OnClickListener() {
                    public void onClick(View v) {
                        keyword12.setVisibility(View.GONE);
                        remove12.setVisibility(View.GONE);

                        LinearLayout.LayoutParams mLayoutParams = (LinearLayout.LayoutParams) keyword13.getLayoutParams();
                        mLayoutParams.leftMargin = 0;
                        keyword13.setLayoutParams(mLayoutParams);
                    }
                }
        );

        remove13.setOnClickListener(
                new TextView.OnClickListener() {
                    public void onClick(View v) {
                        keyword13.setVisibility(View.GONE);
                        remove13.setVisibility(View.GONE);

                        LinearLayout.LayoutParams mLayoutParams = (LinearLayout.LayoutParams) keyword14.getLayoutParams();
                        mLayoutParams.leftMargin = 0;
                        keyword14.setLayoutParams(mLayoutParams);
                    }
                }
        );

        remove14.setOnClickListener(
                new TextView.OnClickListener() {
                    public void onClick(View v) {
                        keyword14.setVisibility(View.GONE);
                        remove14.setVisibility(View.GONE);

                        LinearLayout.LayoutParams mLayoutParams = (LinearLayout.LayoutParams) keyword15.getLayoutParams();
                        mLayoutParams.leftMargin = 0;
                        keyword15.setLayoutParams(mLayoutParams);
                    }
                }
        );

        remove15.setOnClickListener(
                new TextView.OnClickListener() {
                    public void onClick(View v) {
                        keyword15.setVisibility(View.GONE);
                        remove15.setVisibility(View.GONE);

                        LinearLayout.LayoutParams mLayoutParams = (LinearLayout.LayoutParams) keyword16.getLayoutParams();
                        mLayoutParams.leftMargin = 0;
                        keyword16.setLayoutParams(mLayoutParams);
                    }
                }
        );

        remove16.setOnClickListener(
                new TextView.OnClickListener() {
                    public void onClick(View v) {
                        keyword16.setVisibility(View.GONE);
                        remove16.setVisibility(View.GONE);

                        LinearLayout.LayoutParams mLayoutParams = (LinearLayout.LayoutParams) keyword17.getLayoutParams();
                        mLayoutParams.leftMargin = 0;
                        keyword17.setLayoutParams(mLayoutParams);
                    }
                }
        );

        remove17.setOnClickListener(
                new TextView.OnClickListener() {
                    public void onClick(View v) {
                        keyword17.setVisibility(View.GONE);
                        remove17.setVisibility(View.GONE);

                        LinearLayout.LayoutParams mLayoutParams = (LinearLayout.LayoutParams) keyword18.getLayoutParams();
                        mLayoutParams.leftMargin = 0;
                        keyword18.setLayoutParams(mLayoutParams);
                    }
                }
        );

        remove18.setOnClickListener(
                new TextView.OnClickListener() {
                    public void onClick(View v) {
                        keyword18.setVisibility(View.GONE);
                        remove18.setVisibility(View.GONE);
                    }
                }
        );

        return rootView;
    }

    public void setRecommendList1(ArrayList<CulturalInfo> recommendList) {
        this.recommendList = recommendList;

    }

    public ArrayList<CulturalInfo> getRecommendList1() {
//        Log.e("Test1=>", pickDataList.get(0).getCODENAME());
        return recommendList;
    }

}
