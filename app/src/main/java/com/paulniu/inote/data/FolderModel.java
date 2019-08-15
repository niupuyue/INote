package com.paulniu.inote.data;

/**
 * Coder: niupuyue
 * Date: 2019/8/15
 * Time: 17:07
 * Desc: 文件夹对象
 * Version:
 */
public class FolderModel {

    private int folderId;

    private String folderName;

    private int folderNumbers;

    public FolderModel() {
    }

    public FolderModel(int folderId, String folderName, int folderNumbers) {
        this.folderId = folderId;
        this.folderName = folderName;
        this.folderNumbers = folderNumbers;
    }

    public int getFolderId() {
        return folderId;
    }

    public void setFolderId(int folderId) {
        this.folderId = folderId;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public int getFolderNumbers() {
        return folderNumbers;
    }

    public void setFolderNumbers(int folderNumbers) {
        this.folderNumbers = folderNumbers;
    }

    @Override
    public String toString() {
        return "FolderModel{" +
                "folderId=" + folderId +
                ", folderName='" + folderName + '\'' +
                ", folderNumbers='" + folderNumbers + '\'' +
                '}';
    }
}