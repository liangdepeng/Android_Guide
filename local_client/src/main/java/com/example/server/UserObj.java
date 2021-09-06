package com.example.server;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/6/24
 * <p>
 * Summary:
 */
public class UserObj implements Parcelable {

    private String userName;

    private int userAge;

    private String userSign;

    protected UserObj(Parcel in) {
        userName = in.readString();
        userAge = in.readInt();
        userSign = in.readString();
    }

    public static final Creator<UserObj> CREATOR = new Creator<UserObj>() {
        @Override
        public UserObj createFromParcel(Parcel in) {
            return new UserObj(in);
        }

        @Override
        public UserObj[] newArray(int size) {
            return new UserObj[size];
        }
    };

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

    public String getUserSign() {
        return userSign;
    }

    public void setUserSign(String userSign) {
        this.userSign = userSign;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userName);
        dest.writeInt(userAge);
        dest.writeString(userSign);
    }
}
