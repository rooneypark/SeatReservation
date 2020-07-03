package kr.co.samickthk.seatreservation.reservation;

import io.reactivex.Observable;
import android.net.Uri;

public interface IReservation {
    enum Type {
        LOGIN,
        SEAT
    }

    class Event {
        private final Type mType;

        public Event(Type type) {
            mType = type;
        }

        public Type getType() {
            return mType;
        }
    }

    class LoginEvent extends Event {
        public enum Error {
            INVALID_ID_OR_PASSWORD,
            NONE
        }
        private String mUserId;
        private Error mError;

        public LoginEvent(String userId) {
            super(Type.LOGIN);
            mUserId = userId;
            mError = Error.NONE;
        }

        public LoginEvent(Error error) {
            super(Type.LOGIN);
            mUserId = "none";
            mError = error;
        }

        public String getUserId() {
            return mUserId;
        }

        public Error getError() {
            return mError;
        }

        @Override
        public String toString() {
            return "LoginEvent userId=" + mUserId + " error=" + mError;
        }
    }

    class SeatEvent extends Event {
        // TODO
        public SeatEvent() {
            super(Type.SEAT);
        }

        @Override
        public String toString() {
            return "SeatEvent ";
        }
    }

    Observable<LoginEvent> login(Uri uri);
}
