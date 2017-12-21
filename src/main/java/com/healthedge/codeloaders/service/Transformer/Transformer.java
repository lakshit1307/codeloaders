package com.healthedge.codeloaders.service.Transformer;

import com.healthedge.codeloaders.entity.BaseEntity;
import com.healthedge.codeloaders.entity.ClientBaseEntity;

import java.util.List;
import java.util.Map;

public interface Transformer {

	public Map<String, BaseEntity> transform(List<Map<String, String>> input);

	public List<ClientBaseEntity> clientEntityTransform(List<BaseEntity> input);

	public ClientBaseEntity clientEntityTransform(BaseEntity input);
}
