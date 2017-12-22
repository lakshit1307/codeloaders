package com.healthedge.codeloaders.service.Transformer;

import com.healthedge.codeloaders.common.CodeLoaderConstants;
import com.healthedge.codeloaders.entity.BaseEntity;
import com.healthedge.codeloaders.entity.ClientBaseEntity;
import com.healthedge.codeloaders.entity.ZipToCarrierLocality;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ZipToCarrLocaTransformer implements Transformer{

    @Override
    public Map<String, BaseEntity> transform(List<Map<String, String>> input) {
        Map<String, BaseEntity> result = new HashMap<>();

        DateTime current = new DateTime();
        input.forEach(item -> {
            ZipToCarrierLocality zipToCarrierLocality = new ZipToCarrierLocality();
            zipToCarrierLocality.setZipCode(item.get("code"));
            zipToCarrierLocality.setCarrierNbr(item.get("carriernbr"));
            zipToCarrierLocality.setLocalityNbr(item.get("localitynbr"));
            zipToCarrierLocality.setTxCnt(current.toDate().getTime());
            zipToCarrierLocality.setLastTransactionDate(current.toDate());
            zipToCarrierLocality.setLastTransactionUserText(CodeLoaderConstants.TRANSACTION_USER);
            result.put(zipToCarrierLocality.getZipCode(),zipToCarrierLocality);
        });

        return result;
    }

    @Override
    public List<ClientBaseEntity> clientEntityTransform(List<BaseEntity> input){
        return null;
    }

	@Override
	public ClientBaseEntity clientEntityTransform(BaseEntity input) {
		// TODO Auto-generated method stub
		return null;
	}
}
