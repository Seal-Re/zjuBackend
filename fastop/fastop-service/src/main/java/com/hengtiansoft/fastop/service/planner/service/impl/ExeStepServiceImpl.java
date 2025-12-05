package com.hengtiansoft.fastop.service.planner.service.impl;

import com.hengtiansoft.fastop.model.planner.dto.ExeStepMapper;
import com.hengtiansoft.fastop.model.planner.entity.ExeStep;
import com.hengtiansoft.fastop.model.planner.entity.ExeStepExample;
import com.hengtiansoft.fastop.model.planner.entity.ExeStepWithBLOBs;
import com.hengtiansoft.fastop.service.planner.service.ExeStepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExeStepServiceImpl implements ExeStepService {

    @Autowired
    private ExeStepMapper exeStepMapper;

    @Override
    @Transactional(readOnly = false)
    public int deleteExeStep(String exeFunctionId) {
        int res = 0;
        // 查出对应的数据
        ExeStepExample exeStepExample = new ExeStepExample();
        ExeStepExample.Criteria criteria = exeStepExample.createCriteria();
        criteria.andExeFunctionIdEqualTo(exeFunctionId);
        List<ExeStep> list = exeStepMapper.selectByExample(exeStepExample);
        // delete设置为true
        for (ExeStep exeStep : list) {
            exeStep.setDeleted(true);
            // 更新
            exeStepMapper.updateByPrimaryKeySelective((ExeStepWithBLOBs) exeStep);
            res++;
        }
        return res;
    }
}
