package com.myself.show.show.net.responceBean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/14.
 */
@Entity
public class NoteDate {
    @Id(autoincrement = true)
    private Long id;
    private int userId;
    private String noteHtml;
    private int noteId;
    private String title;
    private long saveTime;


    @Generated(hash = 710124588)
    public NoteDate(Long id, int userId, String noteHtml, int noteId, String title,
            long saveTime) {
        this.id = id;
        this.userId = userId;
        this.noteHtml = noteHtml;
        this.noteId = noteId;
        this.title = title;
        this.saveTime = saveTime;
    }

    @Generated(hash = 928794295)
    public NoteDate() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNoteHtml() {
        return noteHtml;
    }

    public void setNoteHtml(String noteHtml) {
        this.noteHtml = noteHtml;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(long saveTime) {
        this.saveTime = saveTime;
    }
}
