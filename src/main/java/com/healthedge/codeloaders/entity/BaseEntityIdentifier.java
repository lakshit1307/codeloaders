package com.healthedge.codeloaders.entity;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public class BaseEntityIdentifier implements Serializable {

    @Id
    private Long versionStart;

    @Id
    private Long versionEnd;

    @Id
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getVersionStart() {
        return versionStart;
    }

    public void setVersionStart(Long versionStart) {
        this.versionStart = versionStart;
    }

    public Long getVersionEnd() {
        return versionEnd;
    }

    public void setVersionEnd(Long versionEnd) {
        this.versionEnd = versionEnd;
    }
}
