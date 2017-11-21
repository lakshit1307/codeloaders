package com.healthedge.codeloaders.service.file.model;

import java.io.Serializable;


public class FilePojo implements Serializable {


    private String code;
    private String long_desc;
    private String short_desc;
    private String full_desc;



    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLong_desc() {
        return long_desc;
    }

    public void setLong_desc(String long_desc) {
        this.long_desc = long_desc;
    }

    public String getShort_desc() {
        return short_desc;
    }

    public void setShort_desc(String short_desc) {
        this.short_desc = short_desc;
    }

    public String getFull_desc() {
        return full_desc;
    }

    public void setFull_desc(String full_desc) {
        this.full_desc = full_desc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FilePojo filePojo = (FilePojo) o;

        if (code != null ? !code.equals(filePojo.code) : filePojo.code != null) return false;
        if (long_desc != null ? !long_desc.equals(filePojo.long_desc) : filePojo.long_desc != null) return false;
        if (short_desc != null ? !short_desc.equals(filePojo.short_desc) : filePojo.short_desc != null) return false;
        return full_desc != null ? full_desc.equals(filePojo.full_desc) : filePojo.full_desc == null;
    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (long_desc != null ? long_desc.hashCode() : 0);
        result = 31 * result + (short_desc != null ? short_desc.hashCode() : 0);
        result = 31 * result + (full_desc != null ? full_desc.hashCode() : 0);
        return result;
    }

    @Override
    public String  toString(){
        return ("Code: "+code+" Long description: "+long_desc+" Short description: "+short_desc+" Full description: "+full_desc);
    }




}
