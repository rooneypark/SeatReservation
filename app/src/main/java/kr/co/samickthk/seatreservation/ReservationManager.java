package kr.co.samickthk.seatreservation;

import android.content.Context;
import android.net.Uri;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import io.reactivex.Observable;
import kr.co.samickthk.seatreservation.presenter.Presenter;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ReservationManager {
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
        return Observable.fromFuture(mHttpClient.executeJSONObject(new AsyncHttpGet(uri), null));
    }

    public Observable<JSONObject> get(String uri) {
        return Observable.fromFuture(mHttpClient.executeJSONObject(new AsyncHttpGet(uri), null));
    }
}
