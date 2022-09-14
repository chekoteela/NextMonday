package com.sharkit.nextmonday.main_menu.support.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.sharkit.nextmonday.main_menu.support.entity.enums.MessageType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackMessage implements Parcelable {

    private String id;
    private String message;
    private MessageType messageType;

    protected FeedbackMessage(final Parcel in) {
        this.id = in.readString();
        this.message = in.readString();
    }

    public static final Creator<FeedbackMessage> CREATOR = new Creator<FeedbackMessage>() {
        @Override
        public FeedbackMessage createFromParcel(final Parcel in) {
            return new FeedbackMessage(in);
        }

        @Override
        public FeedbackMessage[] newArray(final int size) {
            return new FeedbackMessage[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeString(this.id);
        dest.writeString(this.message);
    }
}
