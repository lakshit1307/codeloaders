package com.healthedge.codeloaders.service.Transformer;

import com.healthedge.codeloaders.entity.BaseEntity;
import com.healthedge.codeloaders.entity.ClientBaseEntity;

import java.util.List;

public interface ClientTransformer {

    public List<ClientBaseEntity> transform (List<BaseEntity> input) throws Exception;
}
