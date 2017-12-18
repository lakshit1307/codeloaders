package com.healthedge.codeloaders.service.Parser;

import com.healthedge.codeloaders.common.CodeLoaderConstants;
import com.healthedge.codeloaders.dto.FileMetadata;
import com.healthedge.codeloaders.entity.BaseEntity;
import com.healthedge.codeloaders.entity.ZipToCarrierLocality;
import org.apache.commons.io.FilenameUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class ZipToCarrierLocalityParser implements Parser{

    private ZipToCarrierLocality zipToCarrierLocality;

    @Override
    public List<BaseEntity> parse(String filePath) throws IOException {
        List<BaseEntity> zipToCarrierLocalityList=new ArrayList<>();
        String line="";
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String fileName = FilenameUtils.getName(filePath);

        FileMetadata fileMetadata = new FileMetadata(fileName);
        String fileType = fileMetadata.getFileType().toLowerCase(Locale.getDefault());
        DateTime fileDate=fileMetadata.getFileDate();

        while ((line = br.readLine()) != null) {
            if (line.length() > 0) {

                zipToCarrierLocality=getZipToCarrierLocalotyEntity(line);
                zipToCarrierLocalityList.add(zipToCarrierLocality);

            }
        }
        return zipToCarrierLocalityList;
    }


    public ZipToCarrierLocality getZipToCarrierLocalotyEntity(String line){

        ZipToCarrierLocality zipToCarrierLocality=new ZipToCarrierLocality();

        DateTime current = new DateTime();
        zipToCarrierLocality.setLocalityNbr(line.substring(12,14));
        zipToCarrierLocality.setCarrierNbr(line.substring(7,12));
        zipToCarrierLocality.setZipCode(line.substring(2,7));
        zipToCarrierLocality.setTxCnt(current.toDate().getTime());
        zipToCarrierLocality.setLastTransactionDate(current.toDate());
        zipToCarrierLocality.setLastTransactionUserText(CodeLoaderConstants.TRANSACTION_USER);
        //TODO: Remove the hard-coded value

        return zipToCarrierLocality;
    }
}
