package com.healthedge.codeloaders.service;

import com.healthedge.codeloaders.entity.ClientService;
import com.healthedge.codeloaders.entity.Service;

public abstract class ServiceEntityToClientEntity {



    public ClientService serviceTransformer(Service service){

        ClientService clientService=new ClientService();

        clientService.setEffectiveStartDate(service.getEffectiveStartDate());
        clientService.setEffectiveEndDate(service.getEffectiveEndDate());
        clientService.setServiceCode(service.getServiceCode());
        clientService.setServiceAlternateDesciption(service.getServiceAlternateDesciption());
        clientService.setServiceLongDesciption(service.getServiceLongDesciption());
        clientService.setServiceShortDesciption(service.getServiceShortDesciption());
        clientService.setServiceTypeCode(service.getServiceTypeCD());
        clientService.setStandardizedServiceCode(service.getStandardizedServiceCode());
        clientService.setTransactionCount(service.getTxCnt());
        clientService.setLastTransactionDate(service.getLastTransactionDate());
        clientService.setLastTransactionUserText(service.getLastTransactionUserText());

        return clientService;
    }
}
