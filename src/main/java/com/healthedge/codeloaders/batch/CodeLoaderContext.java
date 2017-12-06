package com.healthedge.codeloaders.batch;

public class CodeLoaderContext {

    private String currentFilePath;

    private static CodeLoaderContext ourInstance = new CodeLoaderContext();

    private CodeLoaderContext() {
    }

    public static CodeLoaderContext getInstance() {
        return ourInstance;
    }

    public String getCurrentFilePath() {
        return currentFilePath;
    }

    public void setCurrentFilePath(String currentFilePath) {
        this.currentFilePath = currentFilePath;
    }
}
