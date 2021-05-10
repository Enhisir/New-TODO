package com.roar.new_todo.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Entity
public class Task implements Parcelable {

    public static final int ACTUAL = 0;
    public static final int DONE = 1;
    public static final int FAILED = 2;
    public static final int NEW = 3;
    public static final int DELETE = 4;
    private static final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "text")
    public String text;

    @ColumnInfo(name = "status")
    public int status;

    @ColumnInfo(name = "important", defaultValue = "false")
    public boolean important;

    @ColumnInfo(name = "have_date", defaultValue = "false")
    public boolean have_date;

    @ColumnInfo(name = "date")
    public long date;

    @ColumnInfo(name = "timestamp", defaultValue = "CURRENT_TIMESTAMP")
    public long timestamp;

    public Task() { }

    protected Task(Parcel in) {
        id = in.readInt();
        name = in.readString();
        text = in.readString();
        status = in.readInt();
        important = in.readByte() != 0;
        have_date = in.readByte() != 0;
        date = in.readLong();
        timestamp = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(text);
        dest.writeInt(status);
        dest.writeByte((byte) (important ? 1 : 0));
        dest.writeByte((byte) (have_date ? 1 : 0));
        dest.writeLong(date);
        dest.writeLong(timestamp);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id &&
                status == task.status &&
                important == task.important &&
                have_date == task.have_date &&
                date == task.date &&
                timestamp == task.timestamp &&
                name.equals(task.name) &&
                Objects.equals(text, task.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, text, status, important, have_date, date, timestamp);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", text='" + text + '\'' +
                ", status=" + status +
                ", important=" + important +
                ", have_date=" + have_date +
                ", date=" + date +
                ", timestamp=" + timestamp +
                '}';
    }

    public Date getDate() {

        return new Date(this.date);
    }

    public String getDateInString() {
        return format.format(date);
    }

    public void setDate(Date date) {
        this.date = date.getTime();
    }

    public void setDate(String value) {
        try {
            date = format.parse(value).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void copy(Task other) {
        this.id = other.id;
        this.name = other.name;
        this.text = other.text;
        this.status = other.status;
        this.important = other.important;
        this.have_date = other.have_date;
        this.date = other.date;
        this.timestamp = other.timestamp;
    }

}
