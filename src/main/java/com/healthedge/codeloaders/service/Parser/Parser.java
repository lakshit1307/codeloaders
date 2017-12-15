package com.healthedge.codeloaders.service.Parser;

import com.healthedge.codeloaders.entity.BaseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface Parser {

    public List<BaseEntity> parse(String filePath) throws IOException;
}
