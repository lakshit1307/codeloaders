package com.healthedge.codeloaders.service.Parser;

import com.healthedge.codeloaders.common.CodeLoaderConstants;
import com.healthedge.codeloaders.dto.FileMetadata;
import com.healthedge.codeloaders.entity.BaseEntity;
import com.healthedge.codeloaders.entity.Diagnosis;
import com.healthedge.codeloaders.util.StringUtil;
import org.apache.commons.io.FilenameUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Service
public class DiagnosisParser implements Parser{

    private static final Logger LOGGER = LoggerFactory.getLogger(DiagnosisParser.class);

    private Properties properties;
    private static final String CODE=".code";
    private static final String LONG_DESC=".long.desc";
    private static final String SHORT_DESC=".short.desc";
    private static final String FULL_DESC=".full.desc";
    private static final String DIAG_TYPE_CODE=".diagtypecode";
    private static final String DELEMITER=".delimiter";

    @Override
    public List<BaseEntity> parse(String filepath) throws IOException{

        List<BaseEntity> diagnosisList = new ArrayList<>();
        String line="";
        BufferedReader br = new BufferedReader(new FileReader(filepath));
        String fileName = FilenameUtils.getName(filepath);

        FileMetadata fileMetadata = new FileMetadata(fileName);
        String fileType = fileMetadata.getFileType().toLowerCase(Locale.getDefault());
        DateTime fileDate = fileMetadata.getFileDate();

        String delimiter = properties.getProperty(fileType + DELEMITER);
        br.readLine();//consuming the first line
        String[] fields;
        while ((line = br.readLine()) != null) {
            if (line.length() > 0) {
                fields = line.split(String.valueOf(delimiter),-1);
                Diagnosis pojo = getDiagnosisEntity(fileMetadata, fileType, fields);
                diagnosisList.add(pojo);
            }
        }
        return diagnosisList;
    }

    public Diagnosis getDiagnosisEntity(FileMetadata fileMetadata, String fileType, String... fields){

        Diagnosis diagnosis = new Diagnosis();
        DateTime current = new DateTime();

        //TX_CNT
        diagnosis.setTxCnt(current.toDate().getTime());
        //LAST_TX_DT
        diagnosis.setLastTransactionDate(current.toDate());
        //LAST_TX_USER_TXT
        diagnosis.setLastTransactionUserText(CodeLoaderConstants.TRANSACTION_USER);

        diagnosis.setVersion(fileMetadata.getFileDate().toDate());

        //DIAG_CD
        if (Integer.parseInt(properties.getProperty(fileType + CODE)) < fields.length){

            String diag_cd = fields[Integer.parseInt(properties.getProperty(fileType + CODE))].toUpperCase();

            if (diag_cd.contains(".")) {
                diagnosis.setDiagnosisCode(fields[Integer.parseInt(properties.getProperty(fileType + CODE))]);
            } else {
                diagnosis.setDiagnosisCode(insertDot(diag_cd, 3));
            }
        }

        //ALT_DSC
        if (Integer.parseInt(properties.getProperty(fileType + FULL_DESC)) < fields.length){
            String altDesc = fields[Integer.parseInt(properties.getProperty(fileType + FULL_DESC))];
            altDesc = modifyInputString(altDesc, 1000);
            diagnosis.setAlternateDescription(altDesc);
        }

        // DIAG_LONG_DESC
        if (Integer.parseInt(properties.getProperty(fileType + LONG_DESC)) < fields.length){
            String longDesc = fields[Integer.parseInt(properties.getProperty(fileType + LONG_DESC))];
            longDesc = modifyInputString(longDesc, 1000);
            diagnosis.setDiagnosisLongDescription(longDesc);
        }

        // DIAG_SHORT_DESC
        if (Integer.parseInt(properties.getProperty(fileType + SHORT_DESC)) < fields.length){
            String shortDesc = fields[Integer.parseInt(properties.getProperty(fileType + SHORT_DESC))];
            shortDesc = modifyInputString(shortDesc, 50);
            diagnosis.setDiagnosisShortDescription(shortDesc);
        }

        // DIAG_TYPE_CODE
        diagnosis.setDiagnosisTypeCode(properties.getProperty(fileType + DIAG_TYPE_CODE));
        diagnosis.setStandardizedDiagnosisCode(diagnosis.getDiagnosisCode().replace(".",""));

        return diagnosis;
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
