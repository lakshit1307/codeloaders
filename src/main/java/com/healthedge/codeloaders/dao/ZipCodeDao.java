package com.healthedge.codeloaders.dao;

import com.healthedge.codeloaders.entity.BaseEntity;
import com.healthedge.codeloaders.entity.ZipCode;
import com.healthedge.codeloaders.repository.ZipCodeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZipCodeDao {

    @Autowired
    private ZipCodeRepository zipCodeRepository;

    private static final Logger LOGGER= LoggerFactory.getLogger("ZipCodeDao is initalized");

    public void save(ZipCode zipCode){
        zipCodeRepository.save(zipCode);
    }
    public void saveAll(List<ZipCode> zipCodeList){
        for (ZipCode zipCode: zipCodeList){
            zipCodeRepository.save(zipCode);
        }
    }
}
