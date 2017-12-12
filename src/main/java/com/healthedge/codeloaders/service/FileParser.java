package com.healthedge.codeloaders.service;


import com.healthedge.codeloaders.common.CodeLoaderConstants;
import com.healthedge.codeloaders.dto.FileMetadata;
import com.healthedge.codeloaders.entity.Service;
import com.healthedge.codeloaders.util.CodeLoaderProperty;

import com.healthedge.codeloaders.util.StringUtil;
import org.apache.commons.io.FilenameUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@SuppressWarnings({"PMD.LocalVariableCouldBeFinal", "PMD.MethodArgumentCouldBeFinal"})
@org.springframework.stereotype.Service
public class FileParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileParser.class);

    private Properties properties;
    private static final String CODE=".code";
    private static final String LONG_DESC=".long.desc";
    private static final String SHORT_DESC=".short.desc";
    private static final String FULL_DESC=".full.desc";
    private static final String SERV_TYPE_CODE=".servtypecode";
    private static final String DELEMITER=".delimiter";

    public FileParser () {
        LOGGER.info("FileParser class initialized");
    }

    @PostConstruct
    public void onInit () {
        properties = CodeLoaderProperty.getInstance().getProperties();
    }

    public Map<String, Service> parse(String filePath) throws IOException {
        Map<String, Service> result = new HashMap<String, Service>();
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line = "";

        String fileName = FilenameUtils.getName(filePath);

        FileMetadata fileMetadata = new FileMetadata(fileName);
        String fileType = fileMetadata.getFileType().toLowerCase(Locale.getDefault());
        DateTime fileDate=fileMetadata.getFileDate();

        String delimiter = properties.getProperty(fileType + DELEMITER);
        br.readLine();//consuming the first line
        String[] fields;
        while ((line = br.readLine()) != null) {
            if (line.length() > 0) {
                fields = line.split(String.valueOf(delimiter),-1);
                Service pojo = getService(fileMetadata, fileType, fields);

                result.put(pojo.getServiceCode(), pojo);
            }
        }

        return result;

    }

    private Service getService(FileMetadata fileMetadata, String fileType, String... fields) {
        Service pojo = new Service();
        DateTime current = new DateTime();

        //TX_CNT
        pojo.setTxCnt(current.toDate().getTime());
        //LAST_TX_DT
        pojo.setLastTransactionDate(current.toDate());
        //LAST_TX_USER_TXT
        pojo.setLastTransactionUserText(CodeLoaderConstants.TRANSACTION_USER);
        //CODE_PROCESSING_HISTORY_ID
        //TODO: Remove the hard-coded value
        pojo.setCodeProcessingHistoryId(5);

        pojo.setVersion(fileMetadata.getFileDate().toDate());


        //serv_cd
        if (Integer.parseInt(properties.getProperty(fileType + CODE)) < fields.length){
            pojo.setServiceCode(fields[Integer.parseInt(properties.getProperty(fileType + CODE))]);

        }
        //alt_dsc
        if (Integer.parseInt(properties.getProperty(fileType + FULL_DESC)) < fields.length){
            String altDesc = fields[Integer.parseInt(properties.getProperty(fileType + FULL_DESC))];
            altDesc = modifyInputString(altDesc, 1000);
            pojo.setServiceAlternateDesciption(altDesc);
        }


        if (Integer.parseInt(properties.getProperty(fileType + LONG_DESC)) < fields.length){
            String longDesc = fields[Integer.parseInt(properties.getProperty(fileType + LONG_DESC))];
            longDesc = modifyInputString(longDesc, 1000);
            pojo.setServiceLongDesciption(longDesc);

        }

        if (Integer.parseInt(properties.getProperty(fileType + SHORT_DESC)) < fields.length){
            String shortDesc = fields[Integer.parseInt(properties.getProperty(fileType + SHORT_DESC))];
            shortDesc = modifyInputString(shortDesc, 50);
            pojo.setServiceShortDesciption(shortDesc);

        }
        pojo.setServiceTypeCD(properties.getProperty(fileType + SERV_TYPE_CODE));
        pojo.setStandardizedServiceCode(pojo.getServiceCode());
        return pojo;
    }

    private String modifyInputString(String input, int limit) {
        input = StringUtil.replaceMultipleSpacesWithSingleSpace(input);
        input = StringUtil.restrictLengthOfInput(input, limit);
        return input;
    }

}




