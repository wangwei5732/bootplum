package com.wdx.bootplum.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wdx.bootplum.system.entity.ExcelModelDemo;
import com.wdx.bootplum.system.mapper.ExcelMapper;
import com.wdx.bootplum.system.service.IExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * ExcelModelDemo 实现类
 * </p>
 *
 * @author gqc
 * @since 2019-04-15
 */
@Service
public class ExcelServiceImpl extends ServiceImpl<ExcelMapper, ExcelModelDemo> implements IExcelService {

}