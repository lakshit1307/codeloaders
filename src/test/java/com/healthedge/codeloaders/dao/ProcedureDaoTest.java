package com.healthedge.codeloaders.dao;

import com.healthedge.codeloaders.entity.Procedure;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProcedureDaoTest {
    @Autowired
    private ProcedureDao proceduredao;
    @Test
    public void procedurePersistTest() {
        List<Procedure> procedures=proceduredao.getAll();
        assertEquals("Check DB is empty first", 0, procedures.size());
        Procedure procedure=new Procedure();
        procedure.setEffectiveEndDate(new Date());
        procedure.setEffectiveStartDate(new Date());
        procedure.setLastTransactionDate(new Date());
        procedure.setProcedureAltDsc("alt desc");
        procedure.setProcedureDescLng("long desc");
        procedure.setProcedureDesc("short ");
        procedure.setProcedureType("a2");
        procedure.setProcedureWorkFlow("sd");
        procedure.setCode("2");
        procedure.setLastTransactionUserText("user text");
        proceduredao.save(procedure);
        procedures=proceduredao.getAll();
        assertEquals("check Account has been created", 1, procedures.size());
    }

}
