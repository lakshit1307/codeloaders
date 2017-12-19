package com.healthedge.codeloaders.batch;

import com.healthedge.codeloaders.myparser.MyFileMetaData;

public class CodeLoaderContext {

    private String currentFilePath;
    private MyFileMetaData fileMetaData;

    private static CodeLoaderContext ourInstance = new CodeLoaderContext();

    private CodeLoaderContext() {
    }

    public static CodeLoaderContext getInstance() {
        return ourInstance;
    }

    public MyFileMetaData getFileMetaData() {
        return fileMetaData;
    }

    public void setFileMetaData(MyFileMetaData fileMetaData) {
        this.fileMetaData = fileMetaData;
    }

    public String getCurrentFilePath() {
        return currentFilePath;
    }

    public void setCurrentFilePath(String currentFilePath) {
        this.currentFilePath = currentFilePath;
    }
}
