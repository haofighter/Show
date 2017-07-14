package com.myself.show.show.net.responceBean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/7/14.
 */
@Entity
public class NoteDate {
    @Id
    private int userId;
    private String noteHtml;

    @Generated(hash = 1682528207)
    public NoteDate(int userId, String noteHtml) {
        this.userId = userId;
        this.noteHtml = noteHtml;
    }

    @Generated(hash = 928794295)
    public NoteDate() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNoteHtml() {
        return this.noteHtml;
    }

    public void setNoteHtml(String noteHtml) {
        this.noteHtml = noteHtml;
    }
}
