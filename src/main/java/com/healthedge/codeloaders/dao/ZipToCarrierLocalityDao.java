package com.healthedge.codeloaders.dao;

import com.healthedge.codeloaders.common.CodeLoaderConstants;
import com.healthedge.codeloaders.entity.BaseEntity;
import com.healthedge.codeloaders.entity.ZipToCarrierLocality;
import com.healthedge.codeloaders.myparser.MyFileMetaData;
import com.healthedge.codeloaders.repository.ZipToCarrierLocalityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ZipToCarrierLocalityDao implements BaseDao{

    @Autowired
    private ZipToCarrierLocalityRepository zipToCarrierLocalityRepositoryDao;
    private static final Logger LOGGER= LoggerFactory.getLogger("ZipToCarrierLocalityDao is initalized");


    @Override
    public Map<String, ? extends BaseEntity> getLatestVersionWithoutTerminate(MyFileMetaData fileMetaData, Long prevVersion) {
        Map<String,ZipToCarrierLocality> map=new HashMap<>();
        for(ZipToCarrierLocality zipToCarrierLocality:zipToCarrierLocalityRepositoryDao.getZipToCarrierLocalityCodesForVersionWithoutAction(
                fileMetaData.getFileType(),prevVersion, CodeLoaderConstants.TERMINATE_ACTION)){
            map.put(zipToCarrierLocality.getZipCode(),zipToCarrierLocality);
        }
        return map;
    }

    @Override
    public <T extends BaseEntity> boolean save(T entity) {
        zipToCarrierLocalityRepositoryDao.save((ZipToCarrierLocality) entity);
        return true;
    }

    @Override
    public <T extends BaseEntity> boolean save(List<T> entity) {
        zipToCarrierLocalityRepositoryDao.save((ZipToCarrierLocality) entity);
        return true;
    }

    @Override
    public void updateLatestVersionForProcessedFile(Long currentVersion, Long previousVersion, List<String> codes) {

    }
}
