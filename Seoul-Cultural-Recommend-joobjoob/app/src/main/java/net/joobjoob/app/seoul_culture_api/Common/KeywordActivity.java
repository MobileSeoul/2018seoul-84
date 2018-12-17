package net.joobjoob.app.seoul_culture_api.Common;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

import net.joobjoob.app.seoul_culture_api.CultureEvent.CulturalEventSearch;
import net.joobjoob.app.seoul_culture_api.Kakao.KaKaoLoginActivity;
import net.joobjoob.app.seoul_culture_api.MainActivity;
import net.joobjoob.app.seoul_culture_api.R;

import java.util.ArrayList;

public class KeywordActivity extends AppCompatActivity {
    Context mContext;
    CheckBox[] checkBoxes = new CheckBox[18];

    Integer[] numCheckBoxIDs = {R.id.Checkbox01, R.id.Checkbox02, R.id.Checkbox03,
            R.id.Checkbox04, R.id.Checkbox05, R.id.Checkbox06, R.id.Checkbox07,
            R.id.Checkbox08, R.id.Checkbox09, R.id.Checkbox10, R.id.Checkbox11,
            R.id.Checkbox12, R.id.Checkbox13, R.id.Checkbox14, R.id.Checkbox15,
            R.id.Checkbox16, R.id.Checkbox17, R.id.Checkbox18};

    String[] numKeyword = {"keyword1", "keyword2", "keyword3",
            "keyword4", "keyword5", "keyword6", "keyword7",
            "keyword8", "keyword9", "keyword10", "keyword11",
            "keyword12", "keyword13", "keyword14", "keyword15",
            "keyword16", "keyword17", "keyword18"};

//    Integer[] numRemove = {R.id.remove1, R.id.remove2, R.id.remove3,
//            R.id.remove4, R.id.remove5, R.id.remove6, R.id.remove7,
//            R.id.remove8, R.id.remove9, R.id.remove10, R.id.remove11,
//            R.id.remove12, R.id.remove13, R.id.remove14, R.id.remove15,
//            R.id.remove16, R.id.remove17, R.id.remove18};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyword);

        mContext = getApplicationContext();

        String str = "당신의 취향 키워드를\n모두 골라주세요.";
        TextView tv = findViewById(R.id.keyword);

        SpannableStringBuilder ssb = new SpannableStringBuilder(str);
        ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#0092ff")), 3, 10, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(ssb);

        Button start_1 = findViewById(R.id.start_button);

        start_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkedCheckBoxes();
                final Intent intent = new Intent(getApplicationContext(), CulturalEventSearch.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

//        SharedPreferences sf = getSharedPreferences("PREF", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sf.edit();
//
//        for (int i = 0; i < 18; i++) {
//            checkBoxes[i] = findViewById(numCheckBoxIDs[i]);
//        }
//
//        for (int i = 0; i < 18; i++) {
//            if (checkBoxes[i].isChecked()) {
//                editor.putString("name", checkBoxes[i].getText().toString()); // 입력
//                Log.e("name::", checkBoxes[i].getText().toString());
//            }
//        }
//
//        editor.commit();
    }

    private void checkedCheckBoxes() {

        ArrayList<String> checkedItem = new ArrayList<>();
        SharedPreferences sf = getSharedPreferences("PREF", MODE_PRIVATE);
        SharedPreferences.Editor editor = sf.edit();

        for (int i = 0; i < 18; i++) {
            checkBoxes[i] = findViewById(numCheckBoxIDs[i]);
        }

        for (int i = 0; i < 18; i++) {
            if (checkBoxes[i].isChecked()) {
                Log.e("name::", checkBoxes[i].getText().toString());
                checkedItem.add(numKeyword[i]);
            }
        }

        editor.putString("name", checkedItem.toString()); // 입력

        editor.commit();
    }

}
