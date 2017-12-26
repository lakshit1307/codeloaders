package com.healthedge.codeloaders.service.Transformer;

import com.healthedge.codeloaders.common.CodeLoaderConstants;
import com.healthedge.codeloaders.entity.BaseEntity;
import com.healthedge.codeloaders.entity.ClientBaseEntity;
import com.healthedge.codeloaders.entity.Procedure;
import java.util.List;
import java.util.Map;

import com.healthedge.codeloaders.util.StringUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class ProcedureTransformer implements Transformer
{
    @Override
    public Map<String, BaseEntity> transform(List<Map<String, String>> input) {
        Map<String, BaseEntity> result = new HashMap<>();

        DateTime current = new DateTime();
        input.forEach(item -> {
            Procedure procedure = new Procedure();
            String codeString = item.get("code");
            if(!codeString.contains(".")) {
                codeString = insertDot(codeString, 3);
            }
            procedure.setCode(codeString);
            procedure.setProcedureType("1"); //TODO
            procedure.setProcedureDesc(modifyInputString(item.get("shortdesc"), 50));
            procedure.setProcedureDescLng(modifyInputString(item.get("longdesc"), 1000));
            procedure.setProcedureAltDsc(modifyInputString(item.get("fulldesc"), 1000));
            procedure.setProcedureStd(procedure.getCode().replace(".",""));
            procedure.setProcedureWorkFlow(procedure.getProcedureWorkFlow());
            procedure.setTxCnt(current.toDate().getTime());
            procedure.setLastTransactionDate(current.toDate());
            procedure.setLastTransactionUserText(CodeLoaderConstants.TRANSACTION_USER);
            result.put(procedure.getCode(),procedure);
        });
        return result;
    }
    private String modifyInputString(String input, int limit) {
        input = StringUtil.replaceMultipleSpacesWithSingleSpace(input);
        input = StringUtil.restrictLengthOfInput(input, limit);
        return input;
    }
    private  String insertDot(String codeVal, int index) {
        if(codeVal.length()>index) {
            codeVal = codeVal.substring(0,index) + "." + codeVal.substring(index,codeVal.length());
        }
        return codeVal;
    }
	@Override
	public List<ClientBaseEntity> clientEntityTransform(List<BaseEntity> input) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ClientBaseEntity clientEntityTransform(BaseEntity input) {
		// TODO Auto-generated method stub
		return null;
	}
}
