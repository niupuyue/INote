package com.paulniu.inote.data;

/**
 * Coder: niupuyue
 * Date: 2019/8/15
 * Time: 19:21
 * Desc: 备忘录对象
 * Version:
 */
public class MemoModel {

    private int memoId;

    private int folderId;

    private String title;

    private String content;

    private String folderName;

    private String date;

    public MemoModel(int memoId, int folderId, String title, String content, String folderName, String date) {
        this.memoId = memoId;
        this.folderId = folderId;
        this.title = title;
        this.content = content;
        this.folderName = folderName;
        this.date = date;
    }

    public MemoModel() {
    }

    public int getMemoId() {
        return memoId;
    }

    public void setMemoId(int memoId) {
        this.memoId = memoId;
    }

    public int getFolderId() {
        return folderId;
    }

    public void setFolderId(int folderId) {
        this.folderId = folderId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "MemoModel{" +
                "memoId=" + memoId +
                ", folderId=" + folderId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", folderName='" + folderName + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
