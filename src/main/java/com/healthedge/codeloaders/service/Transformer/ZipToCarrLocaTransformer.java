package com.healthedge.codeloaders.service.Transformer;

import com.healthedge.codeloaders.common.CodeLoaderConstants;
import com.healthedge.codeloaders.dao.ZipToCarrierLocalityDao;
import com.healthedge.codeloaders.entity.BaseEntity;
import com.healthedge.codeloaders.entity.ClientBaseEntity;
import com.healthedge.codeloaders.entity.ClientZipToCarrLocEntity;
import com.healthedge.codeloaders.entity.ZipToCarrierLocality;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.util.*;

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

        List<ClientBaseEntity> output=new ArrayList<>();
        List<ZipToCarrierLocality> zipToCarrierLocalityList=(List<ZipToCarrierLocality>) (List<?>) input;

        DateTime current = new DateTime();
        zipToCarrierLocalityList.forEach(item->{
            ClientZipToCarrLocEntity clientZipToCarrLocEntity=new ClientZipToCarrLocEntity();
           // clientZipToCarrLocEntity.setZipToCarrLocalityId(); // TODO this need not be set
            clientZipToCarrLocEntity.setEffectiveStartDate(item.getEffectiveStartDate());
            clientZipToCarrLocEntity.setCarrierNbr(item.getCarrierNbr());
            clientZipToCarrLocEntity.setLocalityNbr(item.getLocalityNbr());
            clientZipToCarrLocEntity.setVersionId((long) 1); //TODO
            clientZipToCarrLocEntity.setConceptFulfilledCd(item.getConceptFulfilledCd());
            clientZipToCarrLocEntity.setCvcTypeCd(item.getCvcTypeCd());
            clientZipToCarrLocEntity.setInstActiveCd(item.getInstActiveCd());
            clientZipToCarrLocEntity.setEndorEffectiveDate(current.toDate().getTime());  //TODO
            clientZipToCarrLocEntity.setEndorExpiryDate(current.toDate().getTime());          //TODO
            clientZipToCarrLocEntity.setVersionEffectiveDate(current.toDate().getTime());     //TODO
            clientZipToCarrLocEntity.setVersionExpiryDate(current.toDate().getTime());        //TODO
            clientZipToCarrLocEntity.setScheduleId((long) 1); // TODO to read scheduled Id Medicare
            clientZipToCarrLocEntity.setCode(item.getCode());
            clientZipToCarrLocEntity.setZipCode(item.getZipCode());
            clientZipToCarrLocEntity.setLastTransactionDate(current.toDate().getTime());
            clientZipToCarrLocEntity.setTxCnt(current.toDate().getTime());
            clientZipToCarrLocEntity.setLastTransactionUserText(CodeLoaderConstants.TRANSACTION_USER);
            output.add(clientZipToCarrLocEntity);
        });

        return output;
    }

	@Override
	public ClientBaseEntity clientEntityTransform(BaseEntity input) {
		// TODO Auto-generated method stub
		return null;
	}
}
