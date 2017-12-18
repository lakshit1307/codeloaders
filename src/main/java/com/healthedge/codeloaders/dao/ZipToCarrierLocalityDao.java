package com.healthedge.codeloaders.dao;

import com.healthedge.codeloaders.entity.ZipToCarrierLocality;
import com.healthedge.codeloaders.repository.ZipToCarrierLocalityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZipToCarrierLocalityDao {

    @Autowired
    private ZipToCarrierLocalityRepository zipToCarrierLocalityRepositoryDao;
    private static final Logger LOGGER= LoggerFactory.getLogger("ZipToCarrierLocalityDao is initalized");

    public void save(ZipToCarrierLocality zipToCarrierLocality){
        zipToCarrierLocalityRepositoryDao.save(zipToCarrierLocality);
    }
    public void saveAll(List<ZipToCarrierLocality> zipToCarrierLocalityList){
        for (ZipToCarrierLocality zipCode: zipToCarrierLocalityList){
            zipToCarrierLocalityRepositoryDao.save(zipCode);
        }
    }
}
