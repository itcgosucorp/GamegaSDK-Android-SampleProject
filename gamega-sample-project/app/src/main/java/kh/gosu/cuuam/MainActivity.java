package kh.gosu.cuuam;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gasdk.gstracking.GTrackingManger;
import com.gasdk.nsdk.inteface.IGameInitListener;
import com.gasdk.nsdk.inteface.IGameOauthListener;
import com.gasdk.nsdk.inteface.IGamePaymentListener;
import com.gasdk.nsdk.inteface.OnSingleClickListener;
import com.gasdk.nsdk.object.GameItemIAPObject;
import com.gasdk.nsdk.utils.GameConstant;
import com.gasdk.nsdk.utils.GameException;
import com.gasdk.nsdk.utils.GaSDK;
import com.gasdk.nsdk.utils.GameUtils;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.Locale;

import android.app.Activity;

public class MainActivity extends AppCompatActivity {

    private Button btnDangNhap;
    private Button btnTTTK;
    private Button btnDSITEM;
    private Button btnTTITEM1;
    private Button btnReview;
    private Button btnDangXuat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handleDeepLink(getIntent());
        setupSDK();

        initView();

        Context context = getApplicationContext();

        Log.d("T123", Locale.getDefault().getLanguage());

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent); // Cập nhật Intent mới
        handleDeepLink(intent);
    }

    public void setupSDK() {
        //
        GaSDK.sdkInitialize(this, new IGameInitListener() {
            @Override
            public void onSuccess() {

                GaSDK.showLogin();
                //GameSDK.setLanguage("");
                Log.d("SDKINIT", "onsucceed");
                onLogin();
//                GameSDK.playnow();
            }

            @Override
            public void onError(GameException exception) {

                Log.d("SDKINIT", "onError");
                exception.printStackTrace();
            }
        });
    }

    Activity mActivity;

    private void onLogin() {
        mActivity = this;
        GaSDK.onLogin(new IGameOauthListener() {
            @Override
            public void onLoginSuccess(String UserId, String UserName, String accesstoken) {
                Log.d("45678", GameUtils.buildWebUrl(mActivity, "S1", "123", "Name123"));
                Log.d("onLoginSuccess", "dang nhap thanh cong" + UserName);
                btnDangNhap.setVisibility(View.GONE);
                btnDSITEM.setVisibility(View.VISIBLE);
                btnTTITEM1.setVisibility(View.VISIBLE);
                btnReview.setVisibility(View.VISIBLE);
                btnDangXuat.setVisibility(View.VISIBLE);


                callTrackingExample();
            }

            @Override
            public void onLogout() {
                btnDangNhap.setVisibility(View.GONE);
                btnDSITEM.setVisibility(View.GONE);
                btnTTITEM1.setVisibility(View.GONE);
                btnReview.setVisibility(View.GONE);
                btnDangXuat.setVisibility(View.GONE);
                btnDangNhap.setVisibility(View.VISIBLE);
                GameConstant.fbAllow = true;
            }

            @Override
            public void onError() {

            }
        });
    }

    public void initView() {
        btnDangNhap = findViewById(R.id.btnDangNhap);
        btnDangNhap.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View var1) {
                GameConstant.fbAllow = true;
                GaSDK.showLogin();
            }
        });


        btnDSITEM = (Button) findViewById(R.id.btnDSITEM);
        btnDSITEM.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                for (int i = 0; i < GameConstant.iap_product_ids.size(); i++) {
                    Log.d("TAG_ITEM", GameConstant.iap_product_ids.get(i) + "");
                }
            }
        });


        btnTTITEM1 = (Button) findViewById(R.id.btnTTITEM1);
        btnTTITEM1.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View var1) {
                String productID = "com.idolnrt.gift.0.99";
                String mProductName = "Mua gói 100KNB";
                String currencyUnit = "USD";
                String amount = "0.99";
                String serverID = "10001";
                String characterID = "123457";
                String characterName = "Character_ID (&%#^Ashjba";
                String orderID = "Character_ID";
                String extraInfo = "";
                GameItemIAPObject gosuItemIAPObject = new GameItemIAPObject(orderID, productID, mProductName, currencyUnit, amount, serverID, characterID, extraInfo);
                GaSDK.showTopUp(serverID, characterID, characterName);
            }
        });
        btnReview = (Button) findViewById(R.id.btn_show_review);
        btnReview.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View var1) {
                GaSDK.showReview(mActivity);
            }
        });

        btnDangXuat = (Button) findViewById(R.id.btnDangXuat);
        btnDangXuat.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View var1) {
                GaSDK.logout();
                try {
                    throw new RuntimeException("test000 with log message"); // Force a crash
                } catch (Exception e) {
                    GTrackingManger.crashlytics().setUserId("00001111");
                    GTrackingManger.crashlytics().crashLog(e);
                    GTrackingManger.crashlytics().crashLog(e, "day la message 3");
                    JSONObject object = new JSONObject();
                    try {
                        object.put("role_id", 888888);
                        object.put("data_test", "crash077777");
                    } catch (Exception e2) {

                    }
                    GTrackingManger.crashlytics().crashLog(e, "day la message 4", object);
                }
            }
        });

        btnDangNhap.setVisibility(View.VISIBLE);
        btnDSITEM.setVisibility(View.GONE);
        btnTTITEM1.setVisibility(View.GONE);
//        btnReview.setVisibility(View.GONE);
        btnDangXuat.setVisibility(View.GONE);


    }

    public void showMessage(String message) {
        Activity activity = this;
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CharSequence text = message;
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(activity /* MyActivity */, text, duration);
                toast.show();
            }
        });
    }

    protected void callTrackingExample() {
        String ServerID = "ServerTest_001";
        String CharId = "CharId_001";
        String CharName = "CharName_001";
        String OrderID = "OrderID_001";

        //GTrackingManger.getInstance().createNewCharacter(ServerID, CharId, CharName);
        GTrackingManger.getInstance().enterGame(GameConstant.response_userid, CharId, CharName, ServerID);
//        GTrackingManger.getInstance().startTutorial(GameConstant.response_userid, CharId, CharName, ServerID);
//        GTrackingManger.getInstance().completeTutorial(GameConstant.response_userid, CharId, CharName, ServerID);
//        GTrackingManger.getInstance().checkout(OrderID, "com.toantest.gamega.001", "1000", "VND", GameConstant.response_userid);
//        GTrackingManger.getInstance().purchase(OrderID, "com.toantest.gamega.001", "1000", "VND", GameConstant.response_userid);
//        GTrackingManger.getInstance().level(100);
//        GTrackingManger.getInstance().vip(1);
//        GTrackingManger.getInstance().useItem(GameConstant.response_userid, CharId, ServerID, "ItemID_001", 1);
//        GTrackingManger.getInstance().trackActivityResult(GameConstant.response_userid, CharId, ServerID, "ActivityID_001", "done");
//        try {
//            JSONObject object = new JSONObject();
//            object.put("role_id", "role custom");
//            object.put("data_test", "crash077777");
//            object.put("number_test", 100);
//            GTrackingManger.getInstance().trackCustomEvent("event_toan_custom", object);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        GaSDK.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void handleDeepLink(Intent intent) {
        Uri data = intent.getData();

        if (data != null) {
            // Chuyển URI thành String để kiểm tra
            String url = data.toString();

            // Log URI để kiểm tra xem có nhận đúng URL từ deeplink hay không
            Log.d("Deeplink URL", url);

            // Kiểm tra URL để thực hiện các hành động tùy theo đường dẫn
            if (url.equals("https://rzhx3d.com/")) {
                // Thực hiện hành động mong muốn khi mở đường dẫn chính xác
                Log.d("Deeplink Action", "URL matched and action executed");
            } else {
                // Xử lý các trường hợp đường dẫn khác
                Log.d("Deeplink Action", "URL not matched");
            }
        } else {
            Log.d("Deeplink URL", "No data received in Intent");
        }
    }


}