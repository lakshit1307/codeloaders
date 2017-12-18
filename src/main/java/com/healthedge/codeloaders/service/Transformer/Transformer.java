package com.healthedge.codeloaders.service.Transformer;

import com.healthedge.codeloaders.entity.BaseEntity;

import java.util.List;
import java.util.Map;

public interface Transformer {

    public Map<String,BaseEntity> transform(List<BaseEntity> baseEntityList);
}
