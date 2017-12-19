package com.healthedge.codeloaders.service.Transformer;

import com.healthedge.codeloaders.common.CodeLoaderConstants;
import com.healthedge.codeloaders.entity.BaseEntity;
import com.healthedge.codeloaders.entity.Diagnosis;
import com.healthedge.codeloaders.util.StringUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DiagnosisTransformer implements Transformer {
    @Override
    public Map<String, BaseEntity> transform(List<Map<String, String>> input) {
        Map<String, BaseEntity> result = new HashMap<>();

        DateTime current = new DateTime();
        input.forEach(item -> {
            Diagnosis diagnosis=new Diagnosis();

            String code=item.get("code");
            if (code.contains(".")) {
                diagnosis.setDiagnosisCode(code);
            } else {
                diagnosis.setDiagnosisCode(insertDot(code, 3));
            }

            diagnosis.setStandardizedDiagnosisCode(diagnosis.getDiagnosisCode().replace(".",""));
            diagnosis.setTxCnt(current.toDate().getTime());
            diagnosis.setLastTransactionDate(current.toDate());
            diagnosis.setLastTransactionUserText(CodeLoaderConstants.TRANSACTION_USER);
            diagnosis.setAlternateDescription(modifyInputString(item.get("fulldesc"),1000));
            diagnosis.setDiagnosisLongDescription(modifyInputString(item.get("longdesc"),1000));
            diagnosis.setDiagnosisShortDescription(modifyInputString(item.get("shortdesc"),50));
            diagnosis.setDiagnosisTypeCode("dia"); //TODO
            result.put(diagnosis.getDiagnosisCode(),diagnosis);
        });

        return result;
    }

    private String modifyInputString(String input, int limit) {
        input = StringUtil.replaceMultipleSpacesWithSingleSpace(input);
        input = StringUtil.restrictLengthOfInput(input, limit);
        return input;
    }

    private String insertDot(String diagCode, int insertAt) {
        if (diagCode.length() > insertAt) {
            return diagCode.substring(0, insertAt) + "." + diagCode.substring(insertAt);
        }
        return diagCode;
    }
}
