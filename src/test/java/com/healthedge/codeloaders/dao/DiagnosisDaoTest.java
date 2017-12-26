package com.healthedge.codeloaders.dao;

import com.healthedge.codeloaders.entity.Diagnosis;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
public class DiagnosisDaoTest {

    @Autowired
    private DiagnosisDao diagnosisDao;

    @Test
    public void diagnosisPersistTest() throws Exception{
        Diagnosis diagnosis=new Diagnosis();
        diagnosis.setCode("A00.1");
        diagnosis.setAction("CREATE");
        diagnosis.setEffectiveStartDate(new Date());
        diagnosis.setLastTransactionDate(new Date());
        diagnosis.setVersionStart(new Date().getTime());
        diagnosis.setVersionEnd(new Date().getTime());
        diagnosis.setAlternateDescription("alt desc");
        diagnosis.setDiagnosisLongDescription("long desc");
        diagnosis.setDiagnosisShortDescription("short ");
        diagnosis.setStandardizedDiagnosisCode("A001");
        diagnosis.setWorkFlowCode("sd");
        diagnosis.setLastTransactionUserText("user text");
        diagnosis.setTxCnt(new Date().getTime());
        diagnosisDao.save(diagnosis);
    }

}
