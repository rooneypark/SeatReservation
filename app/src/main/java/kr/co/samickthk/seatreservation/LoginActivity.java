package kr.co.samickthk.seatreservation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import kr.co.samickthk.seatreservation.common.UriBuilder;
import kr.co.samickthk.seatreservation.reservation.IReservation;
import kr.co.samickthk.seatreservation.reservation.ReservationManager;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private EditText mUserIdEditText, mPasswordEditText;
    private Button mLoginButton;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = getApplicationContext();
        mUserIdEditText = findViewById(R.id.edittext_userid);
        mPasswordEditText = findViewById(R.id.edittext_password);
        mLoginButton = findViewById(R.id.button_login);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onClick(View view) {
                String userId = mUserIdEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();
                ReservationManager.getInstance(mContext)
                        .login(UriBuilder.getLoginUri(userId, password))
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Consumer<IReservation.LoginEvent>() {
                            @Override
                            public void accept(IReservation.LoginEvent event) throws Exception {
                                Log.d(TAG, event.toString());
                                if (isError(event)) {
                                    showToast("invalid id or password");
                                } else {
                                    showActivity(MainActivity.class);
                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                showToast("timeout");
                                throwable.printStackTrace();
                            }
                        });
            }
        });
    }

    private boolean isError(IReservation.LoginEvent event) {
        if (event.getError() != IReservation.LoginEvent.Error.NONE) {
            return true;
        }
        return false;
    }

    private void showToast(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showActivity(Class<?> className) {
        Intent intent = new Intent(mContext, className);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mContext.startActivity(intent);
    }
}