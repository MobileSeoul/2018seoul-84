//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package net.joobjoob.app.seoul_culture_api.CultureEvent;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.joobjoob.app.seoul_culture_api.Common.BottomNavigationViewHelper;
import net.joobjoob.app.seoul_culture_api.Common.ConsertSubjectCatalogInfo;
import net.joobjoob.app.seoul_culture_api.Common.CulturalInfo;
import net.joobjoob.app.seoul_culture_api.Common.CustomProgressDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.joobjoob.app.seoul_culture_api.Common.FontUtils;

import net.joobjoob.app.seoul_culture_api.Common.TabPagerAdapter;
import net.joobjoob.app.seoul_culture_api.CulturalRecommend.CulturalRecommendAdapter;
import net.joobjoob.app.seoul_culture_api.CulturalRecommend.RecommendFragment;
import net.joobjoob.app.seoul_culture_api.R;
import net.joobjoob.app.seoul_culture_api.R.id;
import net.joobjoob.app.seoul_culture_api.R.layout;


// comment : 실질적으로 지금 현재 메인 호출하는부분 search 화면

public class CulturalEventSearch extends AppCompatActivity {
    private String[] categoryName = new String[]{"명칭", "기간", "장르"};
    private ConsertSubjectCatalogInfo[] concertSubjectCatalogList;
    private String[] concertSubjectCatalogNameList;
    private AlertDialog alert;
    private int selectedCategory = 0;
    private int selectedSubCodeCatalog = 0;
    private RecyclerView culturalListview;
    private RecyclerView culturalListview1;
    private LinearLayout hotKeyword;
    private LinearLayout btnNameLoadMore;
    private LinearLayout btnPeriodLoadMore;
    private LinearLayout btnSubCodeCatalogLoadMore;
    private ArrayList<CulturalInfo> culturalInfoArrayList = new ArrayList();
    private ArrayList<CulturalInfo> culturalRecommendList = new ArrayList();
    private CulturalListAdapter culturalListAdapter;
    private TextView event1_text;
    private TextView event2_text;
    private TextView event3_text;
    private TextView event4_text;
    private TextView event5_text;
    private String keyword = "";
    private CulturalRecommendAdapter culturalRecommendAdapter;
    private int startNo = -1;
    private int selectCategory = -1;
    private int selectCatalog = -1;
    private String searchKey;
    private String startDate;
    private String endDate;
    private CustomProgressDialog dialogLoading;
    private String openAPIKey = "767043704c6b676838366665534854";

    // 뷰페이저
    ViewPager pager;
    MenuItem prevMenuItem;
    Context mcontext;
    RecommendFragment rf;
    RecyclerView.LayoutManager mLayoutManager;

    public CulturalEventSearch() {
    }

    public void Reload_fragment() {
        Fragment frg = null;
        frg = getSupportFragmentManager().getFragments().get(0);
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.detach(frg);
        ft.attach(frg);
        ft.commit();
    }

    // comment : openApi call
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(layout.fragment_main);
        mcontext = getApplicationContext();
        rf = new RecommendFragment();
        // add Recommend Function
        //(new CulturalEventSearch.ProcessJoobServerThread()).execute();

        openAPIKey = getResources().getString(R.string.get_open_api_key);

        final BottomNavigationView navigationView = findViewById(R.id.a_main_bnv);
        navigationView.setOnNavigationItemSelectedListener(navigationListener);

        BottomNavigationViewHelper.removeShiftMode(navigationView);

        pager = findViewById(R.id.viewPager_tab);

        //this.hideSoftKeyboard();

        pager.setAdapter(new TabPagerAdapter(getSupportFragmentManager(), 4));

        pager.setOffscreenPageLimit(4); // 안보이는 페이지 로딩해 놓을 갯수

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);

                } else {
                    navigationView.getMenu().getItem(0).setChecked(false);
                }
//
//                if(position == 0){
//                    Fragment frg = null;
//                    frg = getSupportFragmentManager().getFragments().get(0);
//                    final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                    ft.detach(frg);
//                    ft.attach(frg);
//                    ft.commit();
//
////                    showProgressDialog();
////
////                    new Handler().postDelayed(new Runnable() {
////                        @Override
////                        public void run() {
////
////                        }
////                    }, 15000);
//
//                    navigationView.getMenu().getItem(position).setChecked(true);
//                    prevMenuItem = navigationView.getMenu().getItem(position);
//
//                }
                if (position == 2) {
                    Fragment frg = null;
                    frg = getSupportFragmentManager().getFragments().get(2);
                    final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.detach(frg);
                    ft.attach(frg);
                    ft.commit();

//                    showProgressDialog();
//
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//
//                        }
//                    }, 15000);

                    navigationView.getMenu().getItem(position).setChecked(true);
                    prevMenuItem = navigationView.getMenu().getItem(position);

                    //cancelProgressDialog();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        RecommendName();

        // search Event Listenr Func Call


//        if(this.getIntent() != null && this.getIntent().getStringExtra("OpenAPIKey") != null) {
//            this.openAPIKey = this.getIntent().getStringExtra("OpenAPIKey");
//        }

//        final ImageButton addKeyword = findViewById(R.id.addKeyword);
//
//        addKeyword.setOnClickListener(
//                new ImageButton.OnClickListener() {
//                    public void onClick(View v) {
//                        final Intent intent = new Intent(getApplicationContext(), KeywordAddActivity.class);
//                        startActivity(intent);
//                        finish();
//                    }
//                }
//        );
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_1:
                    pager.setCurrentItem(0, false);
                    return true;
                case R.id.navigation_2:
                    pager.setCurrentItem(1, false);
                    return true;
                case R.id.navigation_3:
                    pager.setCurrentItem(2, false);
                    return true;
                case R.id.navigation_4:
                    pager.setCurrentItem(3, false);
                    return true;
            }
            return false;
        }
    };

    // comment : 맨위로 올리는부분 TOP func
    public void goTop(View view) {
        this.culturalListview.smoothScrollToPosition(0);
    }


    // comment : progress dialog show
    public void showProgressDialog() {
        if (this.dialogLoading == null && !this.isFinishing()) {
            this.dialogLoading = CustomProgressDialog.show(this, "", "");
            this.dialogLoading.setCancelable(false);
        }

        if (this.dialogLoading != null && !this.dialogLoading.isShowing()) {
            this.dialogLoading.show();
        }

    }
    // comment : cancel progress dialog func

    public void cancelProgressDialog() {
        if (this.dialogLoading != null && this.dialogLoading.isShowing()) {
            this.dialogLoading.cancel();
        }

    }

    public void RecommendName() {


//        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.cultural_listview1);
//        //mRecyclerView.setHasFixedSize(true);
//        mLayoutManager = new LinearLayoutManager(this.getActivity());
//        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        mRecyclerView.setLayoutManager(mLayoutManager);
//        mAdapter = new CulturalRecommendAdapter(this.getActivity(),getRecommendList1());
//        mRecyclerView.setAdapter(mAdapter);
//
//
//

        //add

//        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
//        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        culturalListview.setLayoutManager(layoutManager);
//
//        culturalListAdapter = new CulturalListAdapter(getApplicationContext(), CulturalEventSearch.this.culturalInfoArrayList);
        culturalListAdapter = new CulturalListAdapter(this.getApplicationContext());
//        culturalListview.setAdapter(culturalListAdapter);
////
//        culturalListview1 = (RecyclerView) findViewById(id.cultural_listview1);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
//        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        culturalListview1.setLayoutManager(layoutManager);
//        culturalListAdapter1 = new CulturalListAdapter(getApplicationContext(), CulturalEventSearch.this.culturalRecommendList);
//        culturalListview1.setAdapter(culturalListAdapter1);
//        this.showProgressDialog();


        (new CulturalEventSearch.ProcessJoobServerThread()).execute();


    }

    // comment : Search name func
    public void searchName(View view) {

        hotKeyword = (LinearLayout) findViewById(id.hotkeyword);
        hotKeyword.setVisibility(LinearLayout.GONE);
        culturalListview = (RecyclerView) findViewById(id.cultural_listview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        culturalListview.setLayoutManager(layoutManager);

        culturalListAdapter = new CulturalListAdapter(getApplicationContext(), CulturalEventSearch.this.culturalInfoArrayList);
//        culturalListAdapter = new CulturalListAdapter(this.getApplicationContext());
        culturalListview.setAdapter(culturalListAdapter);


        //CulturalEventSearch.this.findViewById(id.layout_category_0).setVisibility(Integer.parseInt("0"));
        this.searchKey = ((EditText) this.findViewById(id.search_concert_name)).getText().toString();
        if (this.searchKey.length() != 0 && !this.searchKey.contains(" ")) {
            this.culturalInfoArrayList.clear();
            this.culturalListAdapter.notifyDataSetChanged();
            this.startNo = 1;
            this.showProgressDialog();
            (new CulturalEventSearch.ProcessNetworkSearchConcertNameThread()).execute(new String[]{this.searchKey});
            if (this.btnNameLoadMore != null) {
                culturalListAdapter.removeFooterView(this.btnNameLoadMore);
                //add this.culturalListview.removeFooterView(this.btnNameLoadMore);
                this.btnNameLoadMore = null;
            }
//
//            if(this.btnNameLoadMore == null) {
//                LayoutInflater li = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                this.btnNameLoadMore = (LinearLayout) li.inflate(btn_load_more, (ViewGroup)null);
//                FontUtils.getInstance(this).setGlobalFont(this.btnNameLoadMore);
//                this.btnNameLoadMore.setOnClickListener(new OnClickListener() {
//                    public void onClick(View view) {
//                        if(CulturalEventSearch.this.culturalListview.getChildCount() > 0) {
//                            CulturalEventSearch.this.showProgressDialog();
//                            (CulturalEventSearch.this.new ProcessNetworkSearchConcertNameThread()).execute(new String[]{CulturalEventSearch.this.searchKey});
//                        }
//
//                    }
//                });
//            }
            //culturalListAdapter.setFooterView(this.btnNameLoadMore);
            //add this.culturalListview.addFooterView(this.btnNameLoadMore);
        } else {
            Builder alert_confirm = new Builder(this, 3);
            alert_confirm.setMessage("공백을 포함하지 않는\n검색어를 입력해 주십시오.").setNegativeButton("확인", new android.content.DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            this.alert = alert_confirm.create();
            this.alert.setOnShowListener(new OnShowListener() {
                public void onShow(DialogInterface dialogInterface) {
                    CulturalEventSearch.this.alert.getButton(-2).setTypeface(FontUtils.getInstance(CulturalEventSearch.this).getmTypeface());
                }
            });
            this.alert.show();
        }

        //searchName2();
    }

    // comment : Search name func
    public void searchName2() {
        culturalListview = findViewById(id.cultural_listview_2);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        culturalListview.setLayoutManager(layoutManager);
        culturalRecommendAdapter = new CulturalRecommendAdapter(getApplicationContext(), CulturalEventSearch.this.culturalInfoArrayList);
        culturalListview.setAdapter(culturalRecommendAdapter);

        if (this.searchKey.length() != 0 && !this.searchKey.contains(" ")) {
            this.culturalInfoArrayList.clear();
            this.culturalRecommendAdapter.notifyDataSetChanged();
            this.startNo = 1;
            this.showProgressDialog();
            (new CulturalEventSearch.ProcessNetworkSearchConcertNameThread()).execute(new String[]{"호"});

        }
//
//            if(this.btnNameLoadMore == null) {
//                LayoutInflater li = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                this.btnNameLoadMore = (LinearLayout) li.inflate(btn_load_more, (ViewGroup)null);
//                FontUtils.getInstance(this).setGlobalFont(this.btnNameLoadMore);
//                this.btnNameLoadMore.setOnClickListener(new OnClickListener() {
//                    public void onClick(View view) {
//                        if(CulturalEventSearch.this.culturalListview.getChildCount() > 0) {
//                            CulturalEventSearch.this.showProgressDialog();
//                            (CulturalEventSearch.this.new ProcessNetworkSearchConcertNameThread()).execute(new String[]{CulturalEventSearch.this.searchKey});
//                        }
//
//                    }
//                });
//            }
        //culturalListAdapter.setFooterView(this.btnNameLoadMore);
        //add this.culturalListview.addFooterView(this.btnNameLoadMore);
//        } else {
//            Builder alert_confirm = new Builder(this, 3);
//            alert_confirm.setMessage("공백을 포함하지 않는\n검색어를 입력해 주십시오.").setNegativeButton("확인", new android.content.DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    dialogInterface.dismiss();
//                }
//            });
//            this.alert = alert_confirm.create();
//            this.alert.setOnShowListener(new OnShowListener() {
//                public void onShow(DialogInterface dialogInterface) {
//                    CulturalEventSearch.this.alert.getButton(-2).setTypeface(FontUtils.getInstance(CulturalEventSearch.this).getmTypeface());
//                }
//            });
//            this.alert.show();
//        }

    }

    // comment : ShowDatePickerDialog

    public void showDatePickerDialog(final View view) {
        GregorianCalendar calendar = new GregorianCalendar();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        (new DatePickerDialog(this, new OnDateSetListener() {
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                String msg = String.format("%d%d%d", new Object[]{Integer.valueOf(year), Integer.valueOf(monthOfYear + 1), Integer.valueOf(dayOfMonth)});
                ((TextView) view).setText(msg);
            }
        }, year, month, day)).show();
    }

    //    // comment : SearchPeriod
//    public void searchPeriod(View view) {
//        this.startDate = ((TextView)this.findViewById(id.start_date)).getText().toString();
//        this.endDate = ((TextView)this.findViewById(id.end_date)).getText().toString();
//        if(this.startDate.length() != 0 && this.endDate.length() != 0) {
//            this.culturalInfoArrayList.clear();
//            this.culturalListAdapter.notifyDataSetChanged();
//            this.startNo = 1;
//            this.showProgressDialog();
//            (new CulturalEventSearch.ProcessNetworkSearchConcertPeriodThread()).execute(new String[]{this.startDate, this.endDate});
//            if(this.btnPeriodLoadMore != null) {
//
//                culturalListAdapter.removeFooterView(this.btnPeriodLoadMore);
//                this.btnPeriodLoadMore = null;
//            }
//
//            if(this.btnPeriodLoadMore == null) {
//                LayoutInflater li = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                this.btnPeriodLoadMore = (LinearLayout) li.inflate(R.layout.btn_load_more, (ViewGroup) null);
//                FontUtils.getInstance(this).setGlobalFont(this.btnPeriodLoadMore);
//                this.btnPeriodLoadMore.setOnClickListener(new OnClickListener() {
//                    public void onClick(View view) {
//                        if(culturalListAdapter.getItemCount() > 0) {
//                            CulturalEventSearch.this.showProgressDialog();
//                            (CulturalEventSearch.this.new ProcessNetworkSearchConcertPeriodThread()).execute(new String[]{CulturalEventSearch.this.startDate, CulturalEventSearch.this.endDate});
//                        }
//
//                    }
//                });
//            }
//            culturalListAdapter.setFooterView(this.btnNameLoadMore);
//            //add this.culturalListview.addFooterView(this.btnPeriodLoadMore);
//        } else {
//            Builder alert_confirm = new Builder(this, 3);
//            alert_confirm.setMessage("검색기간을 입력해 주십시오.").setNegativeButton("확인", new android.content.DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    dialogInterface.dismiss();
//                }
//            });
//            this.alert = alert_confirm.create();
//            this.alert.setOnShowListener(new OnShowListener() {
//                public void onShow(DialogInterface dialogInterface) {
//                    CulturalEventSearch.this.alert.getButton(-2).setTypeface(FontUtils.getInstance(CulturalEventSearch.this).getmTypeface());
//                }
//            });
//            this.alert.show();
//        }
//
//    }
    // comment : Culture show more
    public void showSubCodeCatalog(View view) {
        Builder alert_confirm = new Builder(this, 3);
        //((ImageView) this.findViewById(id.category_subcode_selector)).setSelected(true);
        alert_confirm.setSingleChoiceItems(this.concertSubjectCatalogNameList, this.selectedSubCodeCatalog, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                CulturalEventSearch.this.selectedSubCodeCatalog = whichButton;
            }
        });
        alert_confirm.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (CulturalEventSearch.this.culturalInfoArrayList != null && CulturalEventSearch.this.selectedSubCodeCatalog != CulturalEventSearch.this.selectCatalog) {
                    if (CulturalEventSearch.this.btnSubCodeCatalogLoadMore != null) {
                        culturalListAdapter.removeFooterView(CulturalEventSearch.this.btnSubCodeCatalogLoadMore);
                        //add CulturalEventSearch.this.culturalListview.removeItemDecoration(CulturalEventSearch.this.btnSubCodeCatalogLoadMore);
                        CulturalEventSearch.this.btnSubCodeCatalogLoadMore = null;
                    }

                    CulturalEventSearch.this.culturalInfoArrayList.clear();
                    culturalListAdapter.notifyDataSetChanged();
                    //((TextView) CulturalEventSearch.this.findViewById(id.category_subcode_name)).setText(CulturalEventSearch.this.concertSubjectCatalogNameList[CulturalEventSearch.this.selectedSubCodeCatalog]);
                    CulturalEventSearch.this.startNo = 1;
                    CulturalEventSearch.this.selectCatalog = CulturalEventSearch.this.selectedSubCodeCatalog;
                    CulturalEventSearch.this.showProgressDialog();
                    (CulturalEventSearch.this.new ProcessNetworkSearchPerformanceBySubjectThread()).execute(new String[]{CulturalEventSearch.this.concertSubjectCatalogList[CulturalEventSearch.this.selectedSubCodeCatalog].getCODE()});


                    if (CulturalEventSearch.this.btnSubCodeCatalogLoadMore == null) {
                        LayoutInflater li = (LayoutInflater) CulturalEventSearch.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        //CulturalEventSearch.this.btnSubCodeCatalogLoadMore = (LinearLayout) li.inflate(layout.btn_load_more, (ViewGroup) null);
                        FontUtils.getInstance(CulturalEventSearch.this).setGlobalFont(CulturalEventSearch.this.btnSubCodeCatalogLoadMore);
                        CulturalEventSearch.this.btnSubCodeCatalogLoadMore.setOnClickListener(new OnClickListener() {
                            public void onClick(View view) {
                                if (culturalListAdapter.getItemCount() > 0) {
                                    CulturalEventSearch.this.showProgressDialog();
                                    (CulturalEventSearch.this.new ProcessNetworkSearchPerformanceBySubjectThread()).execute(new String[]{CulturalEventSearch.this.concertSubjectCatalogList[CulturalEventSearch.this.selectCatalog].getCODE()});
                                }

                            }
                        });
                    }

                    culturalListAdapter.setFooterView(CulturalEventSearch.this.btnSubCodeCatalogLoadMore);
                }

            }
        });
        alert_confirm.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        this.alert = alert_confirm.create();
        this.alert.setOnShowListener(new OnShowListener() {
            public void onShow(DialogInterface dialogInterface) {
                FontUtils.getInstance(CulturalEventSearch.this).setGlobalFont(CulturalEventSearch.this.alert.getListView());
                CulturalEventSearch.this.alert.getButton(-2).setTypeface(FontUtils.getInstance(CulturalEventSearch.this).getmTypeface());
                CulturalEventSearch.this.alert.getButton(-1).setTypeface(FontUtils.getInstance(CulturalEventSearch.this).getmTypeface());
            }
        });
        this.alert.setOnDismissListener(new OnDismissListener() {
            public void onDismiss(DialogInterface dialogInterface) {
                //((ImageView) CulturalEventSearch.this.findViewById(id.category_subcode_selector)).setSelected(false);
            }
        });
        this.alert.show();
    }

    //    // comment : category Show
//    public void showCategory(View view) {
//        Builder alert_confirm = new Builder(this, 3);
//        ((ImageView)this.findViewById(id.category_selector)).setSelected(true);
//        alert_confirm.setSingleChoiceItems(this.categoryName, this.selectedCategory, new android.content.DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//                CulturalEventSearch.this.selectedCategory = whichButton;
//            }
//        }).setPositiveButton("확인", new android.content.DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//                if(CulturalEventSearch.this.selectCategory != CulturalEventSearch.this.selectedCategory) {
//                    CulturalEventSearch.this.initCondition();
//                    ((TextView)CulturalEventSearch.this.findViewById(id.category)).setText(CulturalEventSearch.this.categoryName[CulturalEventSearch.this.selectedCategory]);
//                    CulturalEventSearch.this.findViewById(id.layout_category_0).setVisibility(Integer.parseInt("8"));
//                    CulturalEventSearch.this.findViewById(id.layout_category_1).setVisibility(Integer.parseInt("8"));
//                    CulturalEventSearch.this.findViewById(id.layout_category_2).setVisibility(Integer.parseInt("8"));
//
//                    // comment category View
//                    switch(CulturalEventSearch.this.selectedCategory) {
//                        case 0:
//                            CulturalEventSearch.this.findViewById(id.layout_category_0).setVisibility(Integer.parseInt("0"));
//                            break;
//                        case 1:
//                            CulturalEventSearch.this.findViewById(id.layout_category_1).setVisibility(Integer.parseInt("0"));
//                            break;
//                        case 2:
//                            CulturalEventSearch.this.findViewById(id.layout_category_2).setVisibility(Integer.parseInt("0"));
//                            CulturalEventSearch.this.showProgressDialog();
//                            (CulturalEventSearch.this.new ProcessNetworkSearchConcertSubjectCatalogThread()).execute(new String[0]);
//                    }
//
//                    CulturalEventSearch.this.selectCategory = CulturalEventSearch.this.selectedCategory;
//                }
//
//            }
//        }).setNegativeButton("취소", new android.content.DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//            }
//        });
//        this.alert = alert_confirm.create();
//        this.alert.setOnShowListener(new OnShowListener() {
//            public void onShow(DialogInterface dialogInterface) {
//                FontUtils.getInstance(CulturalEventSearch.this).setGlobalFont(CulturalEventSearch.this.alert.getListView());
//                CulturalEventSearch.this.alert.getButton(-2).setTypeface(FontUtils.getInstance(CulturalEventSearch.this).getmTypeface());
//                CulturalEventSearch.this.alert.getButton(-1).setTypeface(FontUtils.getInstance(CulturalEventSearch.this).getmTypeface());
//            }
//        });
//        this.alert.setOnDismissListener(new OnDismissListener() {
//            public void onDismiss(DialogInterface dialogInterface) {
//                ((ImageView)CulturalEventSearch.this.findViewById(id.category_selector)).setSelected(false);
//            }
//        });
//        this.alert.show();
//        this.hideSoftKeyboard();
//    }
//    // comment: Culture Category (장르 리스트 문화 예술 )
//    private void initCondition() {
//        this.startNo = 1;
//        this.selectCatalog = -1;
//        ((TextView)this.findViewById(id.category_subcode_name)).setText("장르를 선택해 주세요.");
//        if(this.culturalInfoArrayList != null) {
//            this.culturalInfoArrayList.clear();
//        }
//
//        if(this.culturalListAdapter != null) {
//            this.culturalListAdapter.notifyDataSetChanged();
//        }
//
//        if(this.btnNameLoadMore != null) {
//            culturalListAdapter.removeFooterView(this.btnNameLoadMore);
//            this.btnNameLoadMore = null;
//        }
//
//        if(this.btnPeriodLoadMore != null) {
//            culturalListAdapter.removeFooterView(this.btnPeriodLoadMore);
//            this.btnPeriodLoadMore = null;
//        }
//
//        if(this.btnSubCodeCatalogLoadMore != null) {
//            culturalListAdapter.removeFooterView(this.btnSubCodeCatalogLoadMore);
//            this.btnSubCodeCatalogLoadMore = null;
//        }
//
//    }
    // comment :App softeKeyBoard func
    private void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(((EditText) this.findViewById(id.search_concert_name)).getWindowToken(), 0);
    }

    // comment 문화 정보 검색시 세부사항 가져오는 AsyncTask
    public class ProcessNetworkSearchConcertDetailThread extends AsyncTask<String, Void, String> {
        public ProcessNetworkSearchConcertDetailThread() {
        }

        protected String doInBackground(String... strings) {
            String content = this.executeClient(strings);
            return content;
        }

        // comment : 서울 문화 포털에서 JSON -> HTTP (HTTP URL : http://culture.seoul.go.kr/data)
        protected void onPostExecute(String result) {
            String main = result.toString();

            try {
                JSONObject jsonMain = new JSONObject(main);
                JSONObject jsonObject = jsonMain.getJSONObject("SearchConcertDetailService");
                JSONArray row = jsonObject.getJSONArray("row");
                if (row.length() > 0) {
                    String title = row.getJSONObject(0).getString("TITLE");
                    title = title.replaceAll("<", "&lt;");
                    title = title.replaceAll(">", "&gt;");
                    CulturalEventSearch.this.culturalInfoArrayList.add(new CulturalInfo(row.getJSONObject(0).getString("CULTCODE"), row.getJSONObject(0).getString("SUBJCODE"), row.getJSONObject(0).getString("CODENAME"), Html.fromHtml(title).toString(), row.getJSONObject(0).getString("STRTDATE"), row.getJSONObject(0).getString("END_DATE"), row.getJSONObject(0).getString("TIME"), row.getJSONObject(0).getString("PLACE"), row.getJSONObject(0).getString("ORG_LINK"), row.getJSONObject(0).getString("MAIN_IMG"), row.getJSONObject(0).getString("HOMEPAGE"), row.getJSONObject(0).getString("USE_TRGT"), row.getJSONObject(0).getString("USE_FEE"), row.getJSONObject(0).getString("SPONSOR"), row.getJSONObject(0).getString("INQUIRY"), row.getJSONObject(0).getString("SUPPORT"), row.getJSONObject(0).getString("ETC_DESC"), row.getJSONObject(0).getString("AGELIMIT"), row.getJSONObject(0).getString("IS_FREE"), row.getJSONObject(0).getString("TICKET"), row.getJSONObject(0).getString("PROGRAM"), row.getJSONObject(0).getString("PLAYER"), row.getJSONObject(0).getString("CONTENTS"), row.getJSONObject(0).getString("GCODE")));
//                    System.out.println("************************************************************************"+row.getJSONObject(0).getString("CULTCODE") + row.getJSONObject(0).getString("MAIN_IMG"));
//                    Log.e("testcode1=>", String.valueOf(culturalInfoArrayList.size()));
//                    for(int i = 0; i < culturalInfoArrayList.size(); i ++) {
//                        Log.e("testcode1=>", culturalInfoArrayList.get(i).getTITLE());
//                    }

                }

                culturalListAdapter.notifyDataSetChanged();
            } catch (JSONException var7) {
                var7.printStackTrace();
            }

        }

        public String executeClient(String[] strings) {
            HttpResponse response = null;
            new ArrayList();
            HttpClient client = new DefaultHttpClient();
            HttpParams params = client.getParams();
            HttpConnectionParams.setConnectionTimeout(params, 30000);
            HttpConnectionParams.setSoTimeout(params, 30000);
            HttpGet httpGet = new HttpGet("http://openAPI.seoul.go.kr:8088/" + CulturalEventSearch.this.openAPIKey + "/json/SearchConcertDetailService/1/1/" + strings[0]);

            try {
                response = client.execute(httpGet);
                String resultJson = EntityUtils.toString(response.getEntity(), "UTF-8");
                return resultJson;
            } catch (ClientProtocolException var8) {
                var8.printStackTrace();
                return "";
            } catch (IOException var9) {
                var9.printStackTrace();
                return "";
            }
        }
    }

    // comment : 문화정보 검색시 Performance담당하는 AsyncTask
    public class ProcessNetworkSearchPerformanceBySubjectThread extends AsyncTask<String, Void, String> {
        public ProcessNetworkSearchPerformanceBySubjectThread() {
        }

        protected String doInBackground(String... strings) {
            String content = this.executeClient(strings);
            return content;
        }

        protected void onPostExecute(String result) {
            String main = result.toString();

            try {
                JSONObject jsonMain = new JSONObject(main);
                if (jsonMain.has("SearchPerformanceBySubjectService")) {
                    JSONObject jsonObject = jsonMain.getJSONObject("SearchPerformanceBySubjectService");
                    String listTotalCount = jsonObject.getString("list_total_count");
                    JSONArray row = jsonObject.getJSONArray("row");
                    if (row.length() > 0) {
                        for (int i = 0; i < row.length(); ++i) {
                            (CulturalEventSearch.this.new ProcessNetworkSearchConcertDetailThread()).execute(new String[]{row.getJSONObject(i).getString("CULTCODE")});
                        }
                    }

                    CulturalEventSearch.this.startNo = CulturalEventSearch.this.startNo + 20;
                } else {
                    CulturalEventSearch.this.startNo = -1;
                    CulturalEventSearch.this.cancelProgressDialog();
                    CulturalEventSearch.this.btnSubCodeCatalogLoadMore.setOnClickListener((OnClickListener) null);
                }
            } catch (JSONException var11) {
                var11.printStackTrace();
            } finally {
                (new Handler()).postDelayed(new Runnable() {
                    public void run() {
                        CulturalEventSearch.this.cancelProgressDialog();
                    }
                }, 3000L);
            }

        }

        public String executeClient(String[] strings) {
            HttpResponse response = null;
            new ArrayList();
            HttpClient client = new DefaultHttpClient();
            HttpParams params = client.getParams();
            HttpConnectionParams.setConnectionTimeout(params, 30000);
            HttpConnectionParams.setSoTimeout(params, 30000);
            HttpGet httpGet = new HttpGet("http://openAPI.seoul.go.kr:8088/" + CulturalEventSearch.this.openAPIKey + "/json/SearchPerformanceBySubjectService/" + CulturalEventSearch.this.startNo + "/" + (CulturalEventSearch.this.startNo + 19) + "/" + strings[0]);

            try {
                response = client.execute(httpGet);
                String resultJson = EntityUtils.toString(response.getEntity(), "UTF-8");
                return resultJson;
            } catch (ClientProtocolException var8) {
                var8.printStackTrace();
                return "";
            } catch (IOException var9) {
                var9.printStackTrace();
                return "";
            }
        }
    }

//    // comment: AsyncTask Concert 문화정보 카테고리 작업 처리
//    public class ProcessNetworkSearchConcertSubjectCatalogThread extends AsyncTask<String, Void, String> {
//        public ProcessNetworkSearchConcertSubjectCatalogThread() {
//        }
//
//        protected String doInBackground(String... strings) {
//            String content = this.executeClient(strings);
//            return content;
//        }
//
//        protected void onPostExecute(String result) {
//            String main = result.toString();
//
//            try {
//                JSONObject jsonMain = new JSONObject(main);
//                JSONObject jsonObject = jsonMain.getJSONObject("SearchConcertSubjectCatalogService");
//                String listTotalCount = jsonObject.getString("list_total_count");
//                CulturalEventSearch.this.concertSubjectCatalogList = new ConsertSubjectCatalogInfo[Integer.parseInt(listTotalCount)];
//                CulturalEventSearch.this.concertSubjectCatalogNameList = new String[Integer.parseInt(listTotalCount)];
//                JSONArray row = jsonObject.getJSONArray("row");
//
//                for(int i = 0; i < row.length(); ++i) {
//                    CulturalEventSearch.this.concertSubjectCatalogList[i] = new ConsertSubjectCatalogInfo(row.getJSONObject(i).getString("CODE"), row.getJSONObject(i).getString("CODENAME"));
//                    CulturalEventSearch.this.concertSubjectCatalogNameList[i] = row.getJSONObject(i).getString("CODENAME");
//                }
//            } catch (JSONException var11) {
//                var11.printStackTrace();
//            } finally {
//                CulturalEventSearch.this.cancelProgressDialog();
//            }
//
//        }
//
//        public String executeClient(String[] strings) {
//            HttpResponse response = null;
//            new ArrayList();
//            HttpClient client = new DefaultHttpClient();
//            HttpParams params = client.getParams();
//            HttpConnectionParams.setConnectionTimeout(params, 30000);
//            HttpConnectionParams.setSoTimeout(params, 30000);
//            HttpGet httpGet = new HttpGet("http://openAPI.seoul.go.kr:8088/" + CulturalEventSearch.this.openAPIKey + "/json/SearchConcertSubjectCatalogService/1/999");
//
//            try {
//                response = client.execute(httpGet);
//                String resultJson = EntityUtils.toString(response.getEntity(), "UTF-8");
//                return resultJson;
//            } catch (ClientProtocolException var8) {
//                var8.printStackTrace();
//                return "";
//            } catch (IOException var9) {
//                var9.printStackTrace();
//                return "";
//            }
//        }
//    }
//    // comment : AsyncTask 문화정보 기간 확인할때사용하는 Thread
//    public class ProcessNetworkSearchConcertPeriodThread extends AsyncTask<String, Void, String> {
//        public ProcessNetworkSearchConcertPeriodThread() {
//        }
//
//        protected String doInBackground(String... strings) {
//            String content = this.executeClient(strings);
//
//            return content;
//        }
//
//        protected void onPostExecute(String result) {
//            String main = result.toString();
//
//            try {
//                JSONObject jsonMain = new JSONObject(main);
//                if(jsonMain.has("SearchConcertPeriodService")) {
//                    JSONObject jsonObject = jsonMain.getJSONObject("SearchConcertPeriodService");
//                    String listTotalCount = jsonObject.getString("list_total_count");
//                    JSONArray row = jsonObject.getJSONArray("row");
//                    if(row.length() > 0) {
//                        for(int i = 0; i < row.length(); ++i) {
//                            (CulturalEventSearch.this.new ProcessNetworkSearchConcertDetailThread()).execute(new String[]{row.getJSONObject(i).getString("CULTCODE")});
//                        }
//                    }
//
//                    CulturalEventSearch.this.startNo = CulturalEventSearch.this.startNo + 20;
//                } else {
//                    CulturalEventSearch.this.startNo = -1;
//                    CulturalEventSearch.this.cancelProgressDialog();
//                    CulturalEventSearch.this.btnPeriodLoadMore.setOnClickListener((OnClickListener)null);
//                }
//            } catch (JSONException var11) {
//                var11.printStackTrace();
//            } finally {
//                (new Handler()).postDelayed(new Runnable() {
//                    public void run() {
//                        CulturalEventSearch.this.cancelProgressDialog();
//                    }
//                }, 3000L);
//            }
//
//        }
//
//        public String executeClient(String[] strings) {
//            HttpResponse response = null;
//            new ArrayList();
//            HttpClient client = new DefaultHttpClient();
//            HttpParams params = client.getParams();
//            HttpConnectionParams.setConnectionTimeout(params, 30000);
//            HttpConnectionParams.setSoTimeout(params, 30000);
//            HttpGet httpGet = new HttpGet("http://openAPI.seoul.go.kr:8088/" + CulturalEventSearch.this.openAPIKey + "/json/SearchConcertPeriodService/" + CulturalEventSearch.this.startNo + "/" + (CulturalEventSearch.this.startNo + 19) + "/" + strings[0] + "/" + strings[1]);
//
//            try {
//                response = client.execute(httpGet);
//                String resultJson = EntityUtils.toString(response.getEntity(), "UTF-8");
//                return resultJson;
//            } catch (ClientProtocolException var8) {
//                var8.printStackTrace();
//                return "";
//            } catch (IOException var9) {
//                var9.printStackTrace();
//                return "";
//            }
//        }
//    }


    // comment : AsyncTask 문화정보 찾을때쓰레드 작업
    public class ProcessNetworkSearchConcertNameThread extends AsyncTask<String, Void, String> {
        public ProcessNetworkSearchConcertNameThread() {
        }

        protected String doInBackground(String... strings) {

            String content = this.executeClient(strings);
            return content;
        }

        protected void onPostExecute(String result) {
            String main = result.toString();

            try {
                JSONObject jsonMain = new JSONObject(main);
                if (jsonMain.has("SearchConcertNameService")) {
                    JSONObject jsonObject = jsonMain.getJSONObject("SearchConcertNameService");
                    String listTotalCount = jsonObject.getString("list_total_count");
                    JSONArray row = jsonObject.getJSONArray("row");
                    if (row.length() > 0) {
                        for (int i = 0; i < row.length(); ++i) {
                            (CulturalEventSearch.this.new ProcessNetworkSearchConcertDetailThread()).execute(new String[]{row.getJSONObject(i).getString("CULTCODE")});
                        }
                    }

                    CulturalEventSearch.this.startNo = CulturalEventSearch.this.startNo + 20;
                } else {
                    CulturalEventSearch.this.startNo = -1;
                    CulturalEventSearch.this.cancelProgressDialog();
                    //CulturalEventSearch.this.btnNameLoadMore.setOnClickListener((OnClickListener) null);
                }
            } catch (JSONException var11) {
                var11.printStackTrace();
            } finally {
                (new Handler()).postDelayed(new Runnable() {
                    public void run() {
                        CulturalEventSearch.this.cancelProgressDialog();
                    }
                }, 3000L);
            }

        }

        // comment:  실행전 파라미터 받아서 로딩이미지 스레드작업 하는곳
        public String executeClient(String[] strings) {
            HttpResponse response = null;
            new ArrayList();
            HttpClient client = new DefaultHttpClient();
            HttpParams params = client.getParams();
            HttpConnectionParams.setConnectionTimeout(params, 30000);
            HttpConnectionParams.setSoTimeout(params, 30000);
            HttpGet httpGet = new HttpGet("http://openAPI.seoul.go.kr:8088/" + CulturalEventSearch.this.openAPIKey + "/json/SearchConcertNameService/" + CulturalEventSearch.this.startNo + "/" + (CulturalEventSearch.this.startNo + 19) + "/" + strings[0] + "/");

            try {
                response = client.execute(httpGet);
                String resultJson = EntityUtils.toString(response.getEntity(), "UTF-8");
                return resultJson;
            } catch (ClientProtocolException var8) {
                var8.printStackTrace();
                return "";
            } catch (IOException var9) {
                var9.printStackTrace();
                return "";
            }
        }
    }


    //TODO: get joob-server
    public class ProcessJoobServerThread extends AsyncTask<String, Void, String> {

        public ProcessJoobServerThread() {
        }

        protected String doInBackground(String... strings) {

            String content = this.executeClient(strings);
            return content;
        }

        protected void onPostExecute(String result) {
            String main = result.toString();

            try {
                JSONObject jsonMain = new JSONObject(main);
//                JSONArray row = jsonMain.getJSONArray("data");
//                String title = row.getJSONObject(0).getString("title");
//                title = title.replaceAll("<", "&lt;");
//                title = title.replaceAll(">", "&gt;");
//

//                Log.e("test1=>", jsonMain.toString());
//                Log.e("test2=>", jsonMain.toString());
//                Log.e("test3=>", String.valueOf(jsonMain.getJSONObject(0)));
//                Log.e("test4=>", String.valueOf(jsonMain.getJSONObject(0).getString("cultcode")));

                //(new CulturalEventSearch.ProcessNetworkSearchConcertNameThread()).execute("cultcode");
//                if (row.length() > 0) {
//                    for (int i = 0; i < row.length(); ++i) {
//                        (CulturalEventSearch.this.new ProcessNetworkSearchPerformanceBySubjectThread()).execute(new String[]{row.getJSONObject(i).getString("culcode")});
//                    }
//                }


                culturalListview1 = (RecyclerView) findViewById(R.id.cultural_listview_2);

                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                culturalListview1.setLayoutManager(layoutManager);

                culturalRecommendAdapter = new CulturalRecommendAdapter(getApplicationContext(), CulturalEventSearch.this.culturalRecommendList);
//        culturalListAdapter = new CulturalListAdapter(this.getApplicationContext());
                culturalListview1.setAdapter(culturalRecommendAdapter);

                CulturalEventSearch.this.startNo = 1;
                //comment 조건체크 하기위해서 한번더 선언해줬다.
                if (jsonMain.has("data")) {
                    // comment 데이터에 있는값들을 가져오기
                    JSONArray row = jsonMain.getJSONArray("data");


                    //comment 타이틀에 이상한값 껴있을 바꿔주기

                    Log.e("test1=>", jsonMain.toString());
                    Log.e("test2=>", row.toString());
                    Log.e("test3=>", String.valueOf(row.getJSONObject(0)));
                    Log.e("test4=>", String.valueOf(row.getJSONObject(0).getString("cultcode")));
//                   culturalRecommendAdapter = new CulturalRecommendAdapter(getApplicationContext(),CulturalEventSearch.this.culturalRecommendList);
//                   CulturalEventSearch.this.startNo = 1;
                    if (row.length() > 0) {
                        HotKeyword_Setting(row);
                        for (int i = 0; i < row.length(); ++i) {
                            String title = row.getJSONObject(i).getString("title");
                            title = title.replaceAll("<", "&lt;");
                            title = title.replaceAll(">", "&gt;");
                            CulturalEventSearch.this.culturalRecommendList.add(new CulturalInfo(row.getJSONObject(i).getString("cultcode"), row.getJSONObject(i).getString("subjcode"), row.getJSONObject(i).getString("codename"), Html.fromHtml(title).toString(), row.getJSONObject(i).getString("strtdate"), row.getJSONObject(i).getString("end_date"), row.getJSONObject(i).getString("time"), row.getJSONObject(i).getString("place"), row.getJSONObject(i).getString("org_link"), row.getJSONObject(i).getString("main_img"), row.getJSONObject(i).getString("homepage"), row.getJSONObject(i).getString("use_trgt"), row.getJSONObject(i).getString("use_fee"), row.getJSONObject(i).getString("sponsor"), row.getJSONObject(i).getString("inquiry"), row.getJSONObject(i).getString("support"), row.getJSONObject(i).getString("etc_desc"), row.getJSONObject(i).getString("agelimit"), row.getJSONObject(i).getString("is_free"), row.getJSONObject(i).getString("gcode")));
//                           CulturalRecommendAdapter = new CulturalRecommendAdapter(getApplicationContext(),culturalRecommendList);


                        }
                        culturalRecommendAdapter = new CulturalRecommendAdapter(getApplicationContext(), CulturalEventSearch.this.culturalRecommendList);

                        CulturalEventSearch.this.startNo = CulturalEventSearch.this.startNo + 5;
                    }
                    // add 중요한부분

//                   LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
//                   mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//                   culturalListview1 = (RecyclerView) findViewById(id.cultural_listview1);
//
//                   culturalListview1.setAdapter(culturalRecommendAdapter);


                    Log.e("1231235151341345432 :->", String.valueOf(culturalRecommendList));


                    //culturalRecommendList.setAdapter(culturalRecommendAdapter);
                    //setRecommendList(culturalRecommendList);
                } else {
                    CulturalEventSearch.this.startNo = -1;
                    CulturalEventSearch.this.cancelProgressDialog();
                    //CulturalEventSearch.this.btnNameLoadMore.setOnClickListener((OnClickListener) null);
                }


            } catch (JSONException var11) {
                var11.printStackTrace();
            } finally {
                (new Handler()).postDelayed(new Runnable() {
                    public void run() {
                        CulturalEventSearch.this.cancelProgressDialog();
                    }
                }, 3000L);
            }

        }

        // comment:  실행전 파라미터 받아서 로딩이미지 스레드작업 하는곳
        public String executeClient(String[] strings) {
            HttpResponse response = null;
            new ArrayList();
            HttpClient client = new DefaultHttpClient();
            HttpParams params = client.getParams();
            HttpConnectionParams.setConnectionTimeout(params, 30000);
            HttpConnectionParams.setSoTimeout(params, 30000);
            //http://210.89.190.28:5000/recommend/영화,콘서트
            Button bt = (Button) findViewById(id.Category_btn);

            Random random = new Random();
            int random_value = random.nextInt(19) - 1;

            //Log.e("나중 키워드 : ", keyword);

            if (keyword.contains("")) {
                String[] keyword_arr = {"공연행사", "문화", "행사", "국악", "독주", "독창회", "무용", "문화교양", "강좌",
                        "뮤지컬", "오페라", "연극", "영화", "전시", "미술", "축제", "콘서트", "클래식"};
                keyword = keyword_arr[random_value];
            } else {

                //keyword = rf.getKeyword();
            }
            //keyword = "영화";
            HttpGet httpGet = new HttpGet("http://210.89.190.28:5000/recommend/" + keyword);

            try {
                response = client.execute(httpGet);
                String resultJson = EntityUtils.toString(response.getEntity(), "EUC-KR");
                return resultJson;
            } catch (ClientProtocolException var8) {
                var8.printStackTrace();
                return "";
            } catch (IOException var9) {
                var9.printStackTrace();
                return "";
            }
        }

    }
    public void HotKeyword_Setting(final JSONArray row) throws JSONException {
        event1_text = (TextView) findViewById(id.event1);
        String event1_str = row.getJSONObject(0).getString("title");
        event1_str = event1_str.replaceAll("<","&lt;");
        event1_str = event1_str.replaceAll(">", "&gt;");
        event1_text.setText(Html.fromHtml(event1_str).toString());

        event2_text = (TextView) findViewById(id.event2);
        String event2_str = row.getJSONObject(1).getString("title");
        event2_str = event2_str.replaceAll("<","&lt;");
        event2_str = event2_str.replaceAll(">", "&gt;");
        event2_text.setText(Html.fromHtml(event2_str).toString());
//        event2_text.setText(event2_str);

        event3_text = (TextView) findViewById(id.event3);
        String event3_str = row.getJSONObject(2).getString("title");
        event3_str = event3_str.replaceAll("<","&lt;");
        event3_str = event3_str.replaceAll(">", "&gt;");
//        event3_text.setText(event3_str);
        event3_text.setText(Html.fromHtml(event3_str).toString());

        event4_text = (TextView) findViewById(id.event4);
        String event4_str = row.getJSONObject(3).getString("title");
        event4_str = event4_str.replaceAll("<","&lt;");
        event4_str = event4_str.replaceAll(">", "&gt;");
//        event4_text.setText(event4_str);
        event4_text.setText(Html.fromHtml(event4_str).toString());

        event5_text = (TextView) findViewById(id.event5);
        String event5_str = row.getJSONObject(4).getString("title");
        event5_str = event5_str.replaceAll("<","&lt;");
        event5_str = event5_str.replaceAll(">", "&gt;");
        event5_text.setText(event5_str);
        event5_text.setText(Html.fromHtml(event5_str).toString());



    }


//    public void setRecommendList(ArrayList<CulturalInfo> pickDataList){
//        this.culturalRecommendList =  pickDataList;
//
//    }
//    public ArrayList<CulturalInfo> getRecommendList(){
//        Log.e("Test1=>", culturalRecommendList.get(0).getCODENAME());
//        return this.culturalRecommendList;
//    }
}
