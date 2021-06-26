package at.htlkaindorf.bigbrain.beans;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private int uid;
    private String username;
    private String email;
    private String token;

    public User() {
    }

    public User(int uid, String username, String email, String token) {
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.token = token;
    }

    protected User(Parcel in) {
        uid = in.readInt();
        username = in.readString();
        token = in.readString();
        email = in.readString();
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(uid);
        parcel.writeString(username);
        parcel.writeString(token);
        parcel.writeString(email);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
