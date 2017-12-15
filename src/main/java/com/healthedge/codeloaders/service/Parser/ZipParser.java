package com.healthedge.codeloaders.service.Parser;

import com.healthedge.codeloaders.common.CodeLoaderConstants;
import com.healthedge.codeloaders.dto.FileMetadata;
import com.healthedge.codeloaders.entity.BaseEntity;
import com.healthedge.codeloaders.entity.ZipCode;
import org.apache.commons.io.FilenameUtils;
import org.joda.time.DateTime;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ZipParser implements Parser{

    private ZipCode zipCode;

    @Override
    public List<BaseEntity> parse(String filePath) throws IOException{

        List<BaseEntity> zipCodeList=new ArrayList<>();
        String line="";
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String fileName = FilenameUtils.getName(filePath);

        FileMetadata fileMetadata = new FileMetadata(fileName);
        String fileType = fileMetadata.getFileType().toLowerCase(Locale.getDefault());
        DateTime fileDate=fileMetadata.getFileDate();

        while ((line = br.readLine()) != null) {
            if (line.length() > 0) {

                zipCode=getZipCodeEntity(line);
                zipCodeList.add(zipCode);

            }
        }
        return zipCodeList;
    }

    public ZipCode getZipCodeEntity(String line){

        ZipCode zipCode=new ZipCode();

        DateTime current = new DateTime();
        zipCode.setZipCode(line.substring(2,7));
        zipCode.setTxCnt(current.toDate().getTime());
        zipCode.setLastTransactionDate(current.toDate());
        zipCode.setLastTransactionUserText(CodeLoaderConstants.TRANSACTION_USER);
        //TODO: Remove the hard-coded value
        zipCode.setCodeProcessingHistoryId(5);

        return zipCode;
    }
}
