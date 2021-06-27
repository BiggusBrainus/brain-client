package at.htlkaindorf.bigbrain.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Represents the rank of a BigBrain user
 * @version BigBrain v1
 * @since 10.06.2021
 * @author Nico Pessnegger
 */
public class Rank implements Parcelable {
    private User user;
    private Long score;

    public Rank(User user, Long score) {
        this.user = user;
        this.score = score;
    }

    protected Rank(Parcel in) {
        user = in.readParcelable(User.class.getClassLoader());
        if (in.readByte() == 0) {
            score = null;
        } else {
            score = in.readLong();
        }
    }

    public static final Creator<Rank> CREATOR = new Creator<Rank>() {
        @Override
        public Rank createFromParcel(Parcel in) {
            return new Rank(in);
        }

        @Override
        public Rank[] newArray(int size) {
            return new Rank[size];
        }
    };

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(user, i);
        if (score == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(score);
        }
    }
}
