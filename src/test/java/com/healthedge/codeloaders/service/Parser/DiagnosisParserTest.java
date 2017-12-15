package com.healthedge.codeloaders.service.Parser;

import com.healthedge.codeloaders.entity.BaseEntity;
import com.healthedge.codeloaders.entity.Diagnosis;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DiagnosisParserTest {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DiagnosisParserTest.class);
    @Autowired
    private DiagnosisParser diagnosisParser;

    @Test
    public void testFileParsing() throws Exception{
        File file = new File("src/test/resources/basedata/ICD10DIAG/OPTUM_ICD10DIAGNOSIS_2017_10.TAB");
        Diagnosis diagnosis = new Diagnosis();
        List<BaseEntity> record=diagnosisParser.parse(file.getAbsolutePath());
        for (BaseEntity baseEntity:record) {
            diagnosis = (Diagnosis)baseEntity;
            if(diagnosis.getDiagnosisCode().equals("T82.898S") && diagnosis.getStandardizedDiagnosisCode().equals(diagnosis.getDiagnosisCode().replace(".",""))){
                LOGGER.info("File values are parsed");
                LOGGER.info(diagnosis.getDiagnosisCode());
                Assert.assertTrue(true);
            } else {
                Assert.assertFalse(false);
            }
        }
    }
}
