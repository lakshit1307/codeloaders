package com.healthedge.codeloaders.dao;

import com.healthedge.codeloaders.common.CodeLoaderConstants;
import com.healthedge.codeloaders.entity.BaseEntity;
import com.healthedge.codeloaders.entity.FileStatus;
import com.healthedge.codeloaders.entity.Service;
import com.healthedge.codeloaders.myparser.MyFileMetaData;
import com.healthedge.codeloaders.repository.ServiceRepository;
import com.healthedge.codeloaders.service.Parser.ImplementationFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@org.springframework.stereotype.Service
public class ServiceDao implements BaseDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceDao.class);

	@Autowired
	private ServiceRepository serviceRepository;

	@Autowired
	private FileStatusDao fileStatusDao;

	public ServiceDao() {
		LOGGER.info("ServieDao initialized");
	}

	@Transactional
	public Boolean update(final Service service) {
		serviceRepository.update(service.getServiceShortDesciption(), service.getServiceLongDesciption(),
				service.getServiceAlternateDesciption(), service.getAction(), service.getVersionStart(),
				service.getServiceCode());
		return true;
	}

	@Transactional
	public Boolean terminate(final Service service) {
		serviceRepository.terminate(service.getEffectiveEndDate(), service.getAction(), service.getVersionStart(),
				service.getServiceCode());
		return true;
	}

	@Transactional
	public Date getCodeVersion(final String codeType) {
		Date version = serviceRepository.getCodeTypeLatestVersion(codeType);
		return version;
	}

	@Transactional
	public List<String> getDistinctCodeTypes() {
		List<String> allCodeTypes = serviceRepository.getDistinctCodeTypes();
		return allCodeTypes;
	}

	@Transactional
	public List<Service> getDeltaCodes(Date version, String codeType) {
		List<Service> ls = serviceRepository.getDeltaCloaderCodes(version, codeType);
		return ls;
	}

	public List<Service> getAll() {
		return serviceRepository.findAll();
	}

	public Service[] getServiceCodesByCodeType(String codeType) {
		List<Service> services = serviceRepository.getServiceCodesByCodeType(codeType);
		return services.toArray(new Service[services.size()]);
	}

	@Override
	public Map<String, ? extends BaseEntity> getLatestVersionWithoutTerminate(MyFileMetaData fileMetaData, Long prevVersion) {
		Map<String, Service> map = new HashMap<String, Service>();
		for (Service service : serviceRepository.getServiceCodesByCodeTypeForVersionWithoutAction(
				fileMetaData.getFileTypeCd(), prevVersion, CodeLoaderConstants.TERMINATE_ACTION)) {
			map.put(service.getServiceCode(), service);
		}
		return map;
	}

	@Override
	public <T extends BaseEntity> boolean save(T entity) {
		serviceRepository.save((Service) entity);
		return true;
	}

	@Override
	public <T extends BaseEntity> boolean save(List<T> entity) {
		serviceRepository.save((List<Service>) entity);
		return false;
	}

	@Override
	@Transactional
	public void updateLatestVersionForProcessedFile(Long currentVersion, Long previousVersion, List<String> codes) {
		serviceRepository.updateLatestVersionForProcessedFile(currentVersion, previousVersion, codes);
	}

	@Override
	public List<? extends BaseEntity> getEntitiesPerFiletypeForVersion(String filetype, Long version) {
		return getEntitiesPerFiletypeForVersion(filetype, version);
	}

	@Override
	public List<? extends BaseEntity> getDeltaCodes(Long currPayorVersion, Long payorRequestedVersion,
			String codeType) {
		// TODO Auto-generated method stub
		return null;
	}
}
