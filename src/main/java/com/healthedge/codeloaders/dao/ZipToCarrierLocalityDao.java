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
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ZipToCarrierLocalityDao implements BaseDao{

    @Autowired
    private ZipToCarrierLocalityRepository zipToCarrierLocalityRepository;
    private static final Logger LOGGER= LoggerFactory.getLogger("ZipToCarrierLocalityDao is initalized");


    @Override
    public Map<String, ? extends BaseEntity> getLatestVersionWithoutTerminate(MyFileMetaData fileMetaData, Long prevVersion) {
        Map<String,ZipToCarrierLocality> map=new HashMap<>();
        for(ZipToCarrierLocality zipToCarrierLocality:zipToCarrierLocalityRepository.getZipToCarrierLocalityCodesForVersionWithoutAction(
                fileMetaData.getFileType(),prevVersion, CodeLoaderConstants.TERMINATE_ACTION)){
            map.put(zipToCarrierLocality.getZipCode(),zipToCarrierLocality);
        }
        return map;
    }

    @Override
    public <T extends BaseEntity> boolean save(T entity) {
        zipToCarrierLocalityRepository.save((ZipToCarrierLocality) entity);
        return true;
    }

    @Override
    public <T extends BaseEntity> boolean save(List<T> entity) {
        zipToCarrierLocalityRepository.save((List<ZipToCarrierLocality>) entity);
        return true;
    }

    @Override
    @Transactional
    public void updateLatestVersionForProcessedFile(Long currentVersion, Long previousVersion, List<String> codes) {
        zipToCarrierLocalityRepository.updateLatestVersionForProcessedFile(currentVersion, previousVersion, codes);
    }

	@Override
	public List<? extends BaseEntity> getEntitiesPerFiletypeForVersion(String filetype, Long Version) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<? extends BaseEntity> getDeltaCodes(Long currPayorVersion, Long payorRequestedVersion,
			String codeType) {
		// TODO Auto-generated method stub
		return null;
	}
}
