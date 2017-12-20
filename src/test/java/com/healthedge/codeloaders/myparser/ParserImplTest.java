package com.healthedge.codeloaders.myparser;

import com.healthedge.codeloaders.entity.BaseEntity;
import com.healthedge.codeloaders.service.FileParser;
import com.healthedge.codeloaders.service.Parser.ImplementationFactory;
import com.healthedge.codeloaders.service.Transformer.Transformer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ParserImplTest {

    @Autowired
    private AbstractParser abstractParser;

    @Autowired
    private ImplementationFactory implementationFactory;

    @Test
    public void parse() throws Exception {
        File readFile;
        MyFileMetaData fileMetaData;
        Transformer transformer;
        List<Map<String, String>> fileLoadResult;
        Map<String, BaseEntity> tranformerResult;

        HashMap<String,String> fileInfoList = new HashMap<String, String>();
        fileInfoList.put("CPT","src/test/resources/basedata/CPT/OPTUM_CPT_2016-01-01.txt" );
        fileInfoList.put("icd10proc","src/test/resources/basedata/ICD10PROC/OPTUM_ICD10PCS_BASE_2018-01-01.tab" );
        fileInfoList.put("ZIP","src/test/resources/basedata/ZipToCarrierLocality/OPTUM_ZipToCarrierLocality_2003-01-01.txt" );
        fileInfoList.put("ICD10DIAG","src\\test\\resources\\basedata\\ICD10DIAG\\OPTUM_ICD10DIAGNOSIS_2017-10-01.TAB" );

        // Iterate the files
        Iterator it = fileInfoList.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry item = (Map.Entry)it.next();
            readFile = new File(item.getValue().toString());
            fileMetaData = new MyFileMetaData( item.getKey().toString(), readFile.getAbsolutePath());
            fileLoadResult = abstractParser.parse(fileMetaData);
            System.out.println(fileLoadResult);
            transformer = implementationFactory.getTransformer(fileMetaData.getFileType());
            tranformerResult = transformer.transform(fileLoadResult);
            it.remove();
        }
    }
}