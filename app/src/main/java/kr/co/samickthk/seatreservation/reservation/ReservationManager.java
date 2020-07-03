package kr.co.samickthk.seatreservation.reservation;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import kr.co.samickthk.seatreservation.presenter.Presenter;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ReservationManager implements IReservation {
    private static final String TAG = "ReservationManager";
    private static ReservationManager mInstance;
    private List<Presenter> mPresenterList;
    private AsyncHttpClient mHttpClient;

    private ReservationManager(Context context) {
        mPresenterList = new ArrayList<Presenter>();
        mHttpClient = AsyncHttpClient.getDefaultInstance();
    }

    public static ReservationManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ReservationManager(context);
        }

        return mInstance;
    }

    public void addPresenter(Presenter presenter) {
        synchronized (mPresenterList) {
            mPresenterList.add(presenter);
        }

    }

    public void removePresenter(Presenter presenter) {
        synchronized (mPresenterList) {
            mPresenterList.remove(presenter);
        }
    }

    public Observable<JSONObject> get(Uri uri) {
        return Observable.fromFuture(mHttpClient.executeJSONObject(new AsyncHttpGet(uri), null))
                .timeout(3, TimeUnit.SECONDS);
    }

    public Observable<JSONObject> get(String uri) {
        return Observable.fromFuture(mHttpClient.executeJSONObject(new AsyncHttpGet(uri), null))
                .timeout(3, TimeUnit.SECONDS);
    }

    @Override
    public Observable<LoginEvent> login(Uri uri) {
        return get(uri)
                .map(new Function<JSONObject, LoginEvent>() {
                    @Override
                    public LoginEvent apply(JSONObject jsonObject) throws Exception {
                        LoginEvent event = null;
                        try {
                            event = new LoginEvent(jsonObject.getJSONObject("user").getString("userId"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (event == null) {
                            event = new LoginEvent(LoginEvent.Error.INVALID_ID_OR_PASSWORD);
                        }
                        Log.d(TAG, event.toString());
                        return event;
                    }
                });
    }
}
