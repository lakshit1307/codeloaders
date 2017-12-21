package com.healthedge.codeloaders.dao;

import com.healthedge.codeloaders.common.CodeLoaderConstants;
import com.healthedge.codeloaders.entity.BaseEntity;
import com.healthedge.codeloaders.entity.FileStatus;
import com.healthedge.codeloaders.entity.ZipCode;
import com.healthedge.codeloaders.myparser.MyFileMetaData;
import com.healthedge.codeloaders.repository.ZipCodeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ZipCodeDao implements BaseDao{

    @Autowired
    private ZipCodeRepository zipCodeRepository;


    private static final Logger LOGGER= LoggerFactory.getLogger("ZipCodeDao is initalized");


    @Override
    public Map<String, ? extends BaseEntity> getLatestVersionWithoutTerminate(MyFileMetaData fileMetaData, Long prevVersion) {
        Map<String,ZipCode> map=new HashMap<>();
        for(ZipCode zipCode: zipCodeRepository.getZipCodesForVersionWithoutAction(fileMetaData.getFileType(),
                prevVersion, CodeLoaderConstants.TERMINATE_ACTION)){
            map.put(zipCode.getZipCode(),zipCode);
        }

        return map;
    }

    @Override
    public <T extends BaseEntity> boolean save(T entity) {
        zipCodeRepository.save((ZipCode) entity);
        return true;
    }

    @Override
    public <T extends BaseEntity> boolean save(List<T> entity) {
        zipCodeRepository.save( (List<ZipCode>) entity);
        return true;
    }

    @Override
    public void updateLatestVersionForProcessedFile(Long currentVersion, Long previousVersion, List<String> codes) {
        zipCodeRepository.updateLatestVersionForProcessedFile(currentVersion, previousVersion, codes);
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
