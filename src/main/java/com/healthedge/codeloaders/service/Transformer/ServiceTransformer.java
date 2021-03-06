package com.healthedge.codeloaders.service.Transformer;

import com.healthedge.codeloaders.common.CodeLoaderConstants;
import com.healthedge.codeloaders.entity.BaseEntity;
import com.healthedge.codeloaders.entity.ClientBaseEntity;
import com.healthedge.codeloaders.entity.ClientService;
import com.healthedge.codeloaders.entity.Service;
import com.healthedge.codeloaders.util.StringUtil;
import org.joda.time.DateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@org.springframework.stereotype.Service
public class ServiceTransformer implements Transformer {

    @Override
    public Map<String, BaseEntity> transform(List<Map<String, String>> input) {

        Map<String, BaseEntity> result = new HashMap<>();

        DateTime current = new DateTime();
        input.forEach(item -> {
            Service service=new Service();
            service.setCode(item.get("code"));
            service.setServiceTypeCD(service.getCode());
            service.setTxCnt(current.toDate().getTime());
            service.setLastTransactionDate(current.toDate());
            service.setLastTransactionUserText(CodeLoaderConstants.TRANSACTION_USER);
            service.setCodeProcessingHistoryId(5); // TODO
            service.setServiceAlternateDesciption(modifyInputString(item.get("fulldesc"),1000));
            service.setServiceLongDesciption(modifyInputString(item.get("longdesc"),1000));
            service.setServiceShortDesciption(modifyInputString(item.get("shortdesc"),50));
            service.setServiceTypeCD(item.get("filetypecd"));
            service.setStandardizedServiceCode(service.getCode());
            result.put(service.getCode(),service);
        });

        return result;

    }


    private String modifyInputString(String input, int limit) {
        input = StringUtil.replaceMultipleSpacesWithSingleSpace(input);
        input = StringUtil.restrictLengthOfInput(input, limit);
        return input;
    }


	@Override
	public List<ClientBaseEntity> clientEntityTransform(List<BaseEntity> input) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public <T extends BaseEntity> ClientBaseEntity clientEntityTransform(T input) {
		Service service=(Service) input;
		ClientService clientService=new ClientService();
		clientService.setEffectiveEndDate(service.getEffectiveEndDate());
		clientService.setEffectiveStartDate(service.getEffectiveStartDate());
		clientService.setLastTransactionDate(service.getLastTransactionDate());
		clientService.setLastTransactionUserText(service.getLastTransactionUserText());
		clientService.setServiceAlternateDesciption(service.getServiceAlternateDesciption());
		clientService.setServiceLongDesciption(service.getServiceLongDesciption());
		clientService.setServiceShortDesciption(service.getServiceShortDesciption());
		clientService.setServiceTypeCode(service.getServiceTypeCD());
		clientService.setStandardizedServiceCode(service.getStandardizedServiceCode());
		clientService.setTxCnt(service.getTxCnt());
		clientService.setWorkFlowCode(service.getWorkFlowCode());
		clientService.setCode(service.getCode());
		return clientService;
	}

}


