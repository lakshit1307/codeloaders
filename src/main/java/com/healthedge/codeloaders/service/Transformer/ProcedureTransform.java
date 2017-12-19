package com.healthedge.codeloaders.service.Transformer;

import com.healthedge.codeloaders.common.CodeLoaderConstants;
import com.healthedge.codeloaders.entity.BaseEntity;
import com.healthedge.codeloaders.entity.Procedure;
import java.util.List;
import java.util.Map;

import com.healthedge.codeloaders.util.StringUtil;
import org.joda.time.DateTime;
import java.util.HashMap;

public class ProcedureTransform implements Transformer
{
    @Override
    public Map<String, BaseEntity> transform(List<Map<String, String>> input) {
        Map<String, BaseEntity> result = new HashMap<>();

        DateTime current = new DateTime();
        input.forEach(item -> {
            Procedure procedure = new Procedure();
            procedure.setProcedureCode(item.get("code"));
            procedure.setProcedureType("1"); //TODO
            procedure.setProcedureDesc(modifyInputString(item.get("shortdesc"), 50));
            procedure.setProcedureDescLng(modifyInputString(item.get("longdesc"), 1000));
            procedure.setProcedureAltDsc(modifyInputString(item.get("fulldesc"), 1000));
            procedure.setProcedureStd(procedure.getProcedureStd());
            procedure.setProcedureWorkFlow(procedure.getProcedureWorkFlow());
            procedure.setTxCnt(current.toDate().getTime());
            procedure.setLastTransactionDate(current.toDate());
            procedure.setLastTransactionUserText(CodeLoaderConstants.TRANSACTION_USER);
            result.put(procedure.getProcedureCode(),procedure);
        });
        return result;
    }
    private String modifyInputString(String input, int limit) {
        input = StringUtil.replaceMultipleSpacesWithSingleSpace(input);
        input = StringUtil.restrictLengthOfInput(input, limit);
        return input;
    }
}
