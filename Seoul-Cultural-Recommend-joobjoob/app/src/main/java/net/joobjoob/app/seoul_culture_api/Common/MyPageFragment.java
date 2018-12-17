package net.joobjoob.app.seoul_culture_api.Common;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

import net.joobjoob.app.seoul_culture_api.Kakao.KaKaoLoginActivity;
import net.joobjoob.app.seoul_culture_api.Kakao.KakaoSignupActivity;
import net.joobjoob.app.seoul_culture_api.R;

import org.w3c.dom.Text;


public class MyPageFragment extends Fragment {

    Context mcontext;
    KakaoSignupActivity kakao = new KakaoSignupActivity();

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_mypage, container, false);

        mcontext = getContext();
        TextView subTitle1 = rootView.findViewById(R.id.subTitle1);

        subTitle1.setText("서울문화콕콕에 오신것을 환영합니다!");

        TextView account_view = rootView.findViewById(R.id.setting_bt_logout);
        account_view.setOnClickListener(
                new TextView.OnClickListener() {
                    public void onClick(View v) {
//                        CustomDialog customDialog = new CustomDialog(getActivity());
//                        customDialog.callFunction();

                        UserManagement.requestLogout(new LogoutResponseCallback() {
                            @Override
                            public void onCompleteLogout() {
                                Log.d("onClick LogOut -> : ", "Test");
                                final Intent intent = new Intent(mcontext, KaKaoLoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(intent);
                                //finish();
                            }
                        });
                    }
                }
        );

        return rootView;
    }


    public class CustomDialog extends AppCompatActivity {

        private Context context;

        public CustomDialog(Context context) {
            this.context = context;
        }

        public void callFunction() {

            final Dialog dlg = new Dialog(context);

            dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

            dlg.setContentView(R.layout.dialog_logout);

            dlg.show();

            final TextView okButton = dlg.findViewById(R.id.okButton);
            final TextView cancelButton = dlg.findViewById(R.id.cancelButton);

            okButton.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View view) {

                    UserManagement.requestLogout(new LogoutResponseCallback() {
                        @Override
                        public void onCompleteLogout() {
                            Log.d("onClick LogOut -> : ", "Test");
                            final Intent intent = new Intent(context, KaKaoLoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            dlg.dismiss();
                            startActivity(intent);
                            //finish();
                        }
                    });


                }
            });

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "로그아웃을 취소 했습니다.", Toast.LENGTH_SHORT).show();

                    dlg.dismiss();
                }
            });
        }

    }
}
