package com.healthedge.codeloaders.service.Transformer;

import com.healthedge.codeloaders.common.CodeLoaderConstants;
import com.healthedge.codeloaders.entity.BaseEntity;
import com.healthedge.codeloaders.entity.ClientBaseEntity;
import com.healthedge.codeloaders.entity.ZipCode;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ZipCodeTransformer implements Transformer {

    @Override
    public Map<String, BaseEntity> transform(List<Map<String, String>> input) {
        Map<String, BaseEntity> result = new HashMap<>();

        DateTime current = new DateTime();
        input.forEach(item -> {
            ZipCode zipCode = new ZipCode();
            zipCode.setZipCode(item.get("code"));
            zipCode.setTxCnt(current.toDate().getTime());
            zipCode.setLastTransactionDate(current.toDate());
            zipCode.setLastTransactionUserText(CodeLoaderConstants.TRANSACTION_USER);
            result.put(zipCode.getZipCode(),zipCode);
        });

        return result;
    }

	@Override
	public List<ClientBaseEntity> clientEntityTransform(List<BaseEntity> input) {
		// TODO Auto-generated method stub
		return null;
	}
}
