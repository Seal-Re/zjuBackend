package com.hengtiansoft.fastop.service.designer.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.hengtiansoft.fastop.base.common.entity.Response.Response;
import com.hengtiansoft.fastop.base.common.factory.ResponseFactory;
import com.hengtiansoft.fastop.model.designer.dto.BaseStructDto;
import com.hengtiansoft.fastop.model.designer.dto.BaseStructMapper;
import com.hengtiansoft.fastop.model.designer.entity.BaseStruct;
import com.hengtiansoft.fastop.model.designer.entity.BaseStructExample;
import com.hengtiansoft.fastop.model.designer.entity.TestBase;
import com.hengtiansoft.fastop.service.designer.service.BaseStructService;
import com.hengtiansoft.fastop.service.designer.service.TestBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.cache.interceptor.SimpleKeyGenerator.generateKey;

@Service
public class BaseStructServiceImpl implements BaseStructService {

    @Autowired
    private TestBaseService testBaseService;

    @Autowired
    private BaseStructMapper baseStructMapper;

    @Override
    public Response listAllBaseStruct() {
        List<BaseStruct> baseStructs = getBaseStructData();
        return ResponseFactory.success(baseStructs);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response listAllBaseStructAndId() {
        List<BaseStruct> baseStructs = getBaseStructData();
        if (CollectionUtil.isEmpty(baseStructs)) {
            return ResponseFactory.success(Collections.emptyList());
        }

        List<TestBase> allTestBases = testBaseService.listAllTestBase();

        Map<String, Integer> idCache = allTestBases.stream()
                .collect(Collectors.toMap(
                        tb -> buildCacheKey(tb.getModel(), tb.getProfession(), tb.getSubsystem()),
                        TestBase::getId,
                        (oldValue, newValue) -> newValue // 如果有重复key，取新的
                ));


        List<BaseStructDto> dtoList = baseStructs.stream().map(struct -> {
            BaseStructDto dto = new BaseStructDto();
            dto.setBaseStruct(struct);

            String key = buildCacheKey(struct.getModel(), struct.getProfession(), struct.getSubsystem());

            if (idCache.containsKey(key)) {
                dto.setBaseId(idCache.get(key));
            } else {
                Integer newId = testBaseService.getTestBaseWithLimitUtil(
                        struct.getModel(), struct.getProfession(), struct.getSubsystem()
                );
                dto.setBaseId(newId);
                idCache.put(key, newId);
            }
            return dto;
        }).collect(Collectors.toList());

        return ResponseFactory.success(dtoList);
    }

    public List<BaseStruct> getBaseStructData() {
        BaseStructExample baseStructExample = new BaseStructExample();
        List<BaseStruct>  baseStructs = baseStructMapper.selectByExample(baseStructExample);
        return baseStructs;
    }

    private String buildCacheKey(String model, String profession, String subsystem) {
        return model + "@@" +
                profession + "@@" +
                subsystem;
    }
}
