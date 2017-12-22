package com.healthedge.codeloaders.service.Transformer;

import com.healthedge.codeloaders.entity.BaseEntity;
import com.healthedge.codeloaders.entity.ClientBaseEntity;

import java.util.List;
import java.util.Map;

public interface Transformer {

	Map<String, BaseEntity> transform(List<Map<String, String>> input);

	List<ClientBaseEntity> clientEntityTransform(List<BaseEntity> input);

	<T extends BaseEntity> ClientBaseEntity clientEntityTransform(T input);
}
