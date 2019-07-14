package com.wdx.bootplum.common.utils;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.wdx.bootplum.common.exception.BusinessApiException;
import com.wdx.bootplum.system.entity.ExcelModelDemo;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * <p>
 * EasyPoiUtil
 * </p>
 *
 * @author gqc
 * @since 2019-04-12
 */
public class EasyPoiUtil {

    /**
     * 导出excel，默认创建表头
     *
     * @param list
     * @param title
     * @param sheetName
     * @param pojoClass
     * @param fileName
     * @param response
     */
    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, HttpServletResponse response) {
        ExportParams exportParams = new ExportParams(title, sheetName);
        defaultExport(list, pojoClass, fileName, response, new ExportParams(title, sheetName));
    }

    /**
     * 导出excel
     *
     * @param list           数据集合List
     * @param title          标题
     * @param sheetName      sheet页名
     * @param pojoClass      数据模型
     * @param fileName       文件名
     * @param isCreateHeader 是否创建表头
     * @param response
     */
    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, boolean isCreateHeader, HttpServletResponse response) {
        ExportParams exportParams = new ExportParams(title, sheetName);
        exportParams.setCreateHeadRows(isCreateHeader);
        defaultExport(list, pojoClass, fileName, response, exportParams);
    }

    /**
     * 导出excel,一个excel 创建多个sheet
     *
     * @param List<Map<String, Object>>
     * @param fileName
     * @param response
     */
    public static void exportExcel(List<Map<String, Object>> list, String fileName, HttpServletResponse response) {
        defaultExport(list, fileName, response);
    }

    /**
     * @param list
     * @param pojoClass
     * @param fileName
     * @param response
     * @param exportParams
     */
    private static void defaultExport(List<?> list, Class<?> pojoClass, String fileName, HttpServletResponse response, ExportParams exportParams) {
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
        if (workbook != null) ;
        downLoadExcel(fileName, response, workbook);
    }

    /**
     * 一个excel 创建多个sheet
     * 多个Map key title 对应表格Title key entity 对应表格对应实体 key data Collection 数据
     *
     * @param list
     * @param fileName
     * @param response
     */
    private static void defaultExport(List<Map<String, Object>> list, String fileName, HttpServletResponse response) {
        Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.HSSF);
        if (workbook != null) ;
        downLoadExcel(fileName, response, workbook);
    }

    /**
     * 将excel写入->response响应输出流
     *
     * @param fileName
     * @param response
     * @param workbook
     */
    public static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + "-" + DateTimeUtils.getCurrentDateStr() + ".xls");
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            throw new BusinessApiException("FAIL", e.getMessage());
        }
    }

    /**
     * 保存excel到本地
     * @param localPath
     * @param list
     * @param pojoClass
     * @throws IOException
     */
    public static void saveLocal(String localPath,String fileName, List<?> list, Class<?> pojoClass) throws IOException {
        File savePath = new File(localPath);
        if (!savePath.exists()) {
            savePath.mkdirs();
        }
        ExportParams exportParams = new ExportParams();
        exportParams.setCreateHeadRows(false);
        FileOutputStream fos = new FileOutputStream(localPath+"/"+fileName);
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
        if (workbook != null)
            workbook.write(fos);
    }

    /**
     * 保存excel到本地
     * @param localPath
     * @param fileName
     * @param workbook
     * @throws IOException
     */
    public static void saveLocalByWorkbook(String localPath,String fileName,Workbook workbook) throws IOException {
        File savePath = new File(localPath);
        if (!savePath.exists()) {
            savePath.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream(localPath+"/"+fileName);
        workbook.write(fos);
    }


    /**
     * 导入excel
     *
     * @param file
     * @param titleRows
     * @param headerRows
     * @param pojoClass
     * @param <T>
     * @return
     */
    public static <T> ExcelImportResult<T> importExcel(InputStream inputStream, Integer titleRows, Integer headerRows, Class<T> pojoClass) {
        if (inputStream == null) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setNeedVerfiy(true);
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        ExcelImportResult<T> excelImportResult = null;
        try {
            excelImportResult = ExcelImportUtil.importExcelMore(inputStream, pojoClass, params);
        } catch (NoSuchElementException e) {
            throw new BusinessApiException("FAIL", "excel文件不能为空");
        } catch (Exception e) {
            throw new BusinessApiException("FAIL", e.getMessage());
        }
        return excelImportResult;
    }


    public static void main(String[] args) throws IOException {
        /**************导入校验测试****************/
        String filePath = "D:/测试-20190415.xls";

        //1.开始导入
        ExcelImportResult excelImportResult = importExcel(new FileInputStream(filePath), 1, 1, ExcelModelDemo.class);
        //2.导入结果
        System.err.println("=================成功数据=====================");
        System.err.println(excelImportResult.getList());
        System.err.println("=================错误数据=====================");
        System.err.println(excelImportResult.getFailList());//包含校验错误信息
        System.err.println("=================错误信息=====================");
        //打印错误信息+行数
        for (Object object : excelImportResult.getFailList()) {
            ExcelModelDemo excelModelDemo = (ExcelModelDemo) object;
            System.err.println("错误提示：" + excelModelDemo.getErrorMsg() + "---错误行数：" + excelModelDemo.getRowNum() + "行");
        }
        System.err.println("=================导入结果=====================");
        System.err.println("导入成功：" + excelImportResult.getList().size() + "条");
        System.err.println("导入失败：" + excelImportResult.getFailList().size() + "条");
        //3.将错误信息返回给客户端以excel文件形式
        //有红色标红
        excelImportResult.getFailWorkbook().write(new FileOutputStream("D:/error.xls"));
    }
}