package com.healthedge.codeloaders.service.Parser;

import com.healthedge.codeloaders.entity.BaseEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.List;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DiagnosisParserTest {
    @Autowired
    private DiagnosisParser diagnosisParser;

    @Test
    public void testFileParsing() throws Exception{
        File file = new File("src/test/resources/basedata/ICD10DIAG/OPTUM_ICD10DIAGNOSIS_2017_10.TAB");

        List<BaseEntity> record=diagnosisParser.parse(file.getAbsolutePath());
       // for (BaseEntity baseEntity:record)
            //baseEntity.
//        if(record.containsKey("A01.00")&&record.containsKey("A01.2")){
//            System.out.println("File values are parsed");
//            assert true;
//        }
//        else
//            assert false;

    }

}
