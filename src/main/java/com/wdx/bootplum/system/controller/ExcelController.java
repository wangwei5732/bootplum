package com.wdx.bootplum.system.controller;

import com.wdx.bootplum.common.constant.Constant;
import com.wdx.bootplum.common.utils.EasyPoiUtil;
import com.wdx.bootplum.common.utils.ExcelCommUtil;
import com.wdx.bootplum.common.vo.AjaxObject;
import com.wdx.bootplum.system.entity.ExcelModelDemo;
import com.wdx.bootplum.system.service.IExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Excel文件上传下载 控制器
 * </p>
 *
 * @author gqc
 * @since 2019-04-12
 */
@Controller
public class ExcelController {
    @Autowired
    IExcelService excelService;

    @Autowired
    ExcelCommUtil excelImportCommUtil;

    /**
     * 导出excelDemo
     *
     * @param response
     */
    @GetMapping("exportExcelDemo")
    public void exportExcelDemo(HttpServletResponse response) {
        //模拟数据
        List<ExcelModelDemo> list = new ArrayList<>();
        for (int i = 0; i < 8000; i++) {
            if (i / 2 == 0) {
                list.add(new ExcelModelDemo("id-" + i, i, "1", "address" + i));
            } else {
                list.add(new ExcelModelDemo("id-" + i, i, "2", "address" + i));
            }
        }
        //导出excel
        EasyPoiUtil.exportExcel(list, "导出测试", "页面1", ExcelModelDemo.class, "导出测试", response);
    }

    /**
     * 导入excelDemo
     *
     * @param request
     * @param multipartFile
     * @return
     * @throws IOException
     */
    @PostMapping("importExcelDemo")
    @ResponseBody
    public AjaxObject importExcel(HttpServletRequest request, @RequestParam("excelFile") MultipartFile multipartFile) throws IOException {
        //上传并解析excel
        return excelImportCommUtil.ImportExcelByDo(request, multipartFile, ExcelModelDemo.class);
    }

    /**
     * 异步获取当前消息队列中未被消费的队列大小
     * @return
     */
    @GetMapping("/getUndoSize")
    @ResponseBody
    public  AjaxObject getUndoSize() {
        return AjaxObject.customOk("待导入数据",excelImportCommUtil.getTempSize(Constant.SUCCSIZETEMPKEY));
    }

}