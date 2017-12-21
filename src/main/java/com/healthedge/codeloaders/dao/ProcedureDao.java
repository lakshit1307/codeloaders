package com.healthedge.codeloaders.dao;

import com.healthedge.codeloaders.common.CodeLoaderConstants;
import com.healthedge.codeloaders.entity.BaseEntity;
import com.healthedge.codeloaders.entity.Procedure;
import com.healthedge.codeloaders.myparser.MyFileMetaData;
import com.healthedge.codeloaders.repository.ProcedureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProcedureDao implements BaseDao {

    @Autowired
    private ProcedureRepository procedureRepository;

    public List<Procedure> getAll() {
        return procedureRepository.findAll();
    }

    @Override
    public Map<String, ? extends BaseEntity> getLatestVersionWithoutTerminate(MyFileMetaData fileMetaData, Long prevVersion) {
        Map<String, Procedure> map = new HashMap<>();
        for (Procedure procedure : procedureRepository.getServiceCodesByCodeTypeForVersionWithoutAction(
                fileMetaData.getFileTypeCd(), prevVersion, CodeLoaderConstants.TERMINATE_ACTION)) {
            map.put(procedure.getProcedureCode(), procedure);
        }
        return map;
    }

    @Override
    public <T extends BaseEntity> boolean save(T entity) {
        procedureRepository.save((Procedure)entity);
        return true;
    }

    @Override
    public <T extends BaseEntity> boolean save(List<T> entity) {
        procedureRepository.save((List<Procedure>)entity);
        return true;
    }
    @Override
    public void updateLatestVersionForProcessedFile(Long currentVersion, Long previousVersion, List<String> codes) {
        procedureRepository.updateLatestVersionForProcessedFile(currentVersion, previousVersion, codes);
    }

}
