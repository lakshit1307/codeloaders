package com.healthedge.codeloaders.dao;

import com.healthedge.codeloaders.common.CodeLoaderConstants;
import com.healthedge.codeloaders.entity.BaseEntity;
import com.healthedge.codeloaders.entity.Diagnosis;
import com.healthedge.codeloaders.myparser.MyFileMetaData;
import com.healthedge.codeloaders.repository.DiagnosisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DiagnosisDao implements BaseDao {

	@Autowired
	private DiagnosisRepository diagnosisRepository;

	@Override
	public Map<String, ? extends BaseEntity> getLatestVersionWithoutTerminate(MyFileMetaData fileMetaData,
			Long prevVersion) {
		Map<String, Diagnosis> map = new HashMap<>();
		for (Diagnosis diagnosis : diagnosisRepository.getDiagnosisCodesByCodeTypeForVersionWithoutAction(
				fileMetaData.getFileTypeCd(), prevVersion, CodeLoaderConstants.TERMINATE_ACTION)) {
			map.put(diagnosis.getCode(), diagnosis);
		}
		return map;
	}

	@Override
	public <T extends BaseEntity> boolean save(T entity) {
		diagnosisRepository.save((Diagnosis) entity);
		return true;
	}

	@Override
	public <T extends BaseEntity> boolean save(List<T> entity) {
		diagnosisRepository.save((List<Diagnosis>) entity);
		return false;
	}

	@Override
	@Transactional
	public void updateLatestVersionForProcessedFile(Long currentVersion, Long previousVersion, List<String> codes) {
		diagnosisRepository.updateLatestVersionForProcessedFile(currentVersion, previousVersion, codes);
	}

	@Override
	public List<? extends BaseEntity> getDeltaCodes(Long currPayorVersion, Long payorRequestedVersion,
			String codeType) {
		// TODO Auto-generated method stub
		return null;
	}
}
