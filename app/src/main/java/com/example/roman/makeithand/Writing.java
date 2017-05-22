package com.example.roman.makeithand;

import android.os.Parcel;
import android.os.Parcelable;


class Writing implements Parcelable
{
    Writing(int id, String title, String value, String path)
    {
        this.id = id;
        this.title = title;
        this.value = value;
        this.path = path;
    }

    public static final Parcelable.Creator<Writing> CREATOR = new Creator<Writing>() {
        @Override
        public Writing createFromParcel(Parcel source) {
            return new Writing(source.readInt(), source.readString(), source.readString(), source.readString());
        }

        @Override
        public Writing[] newArray(int size) {
            return new Writing[size];
        }
    };

    public int describeContents()
    {
        return  0;
    }

    public void writeToParcel(Parcel parcel, int flags)
    {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(value);
        parcel.writeString(path);
    }

    public int      id;
    String   title;
    String   value;
    String   path;
}
