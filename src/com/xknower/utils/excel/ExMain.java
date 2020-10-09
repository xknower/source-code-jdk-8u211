package com.xknower.utils.excel;

import java.util.ArrayList;
import java.util.List;

public class ExMain {
    public static void main(String[] args) throws Exception {
        //
        List<ExportDataEntity> list = new ArrayList<>();
        ExportDataEntity v = new ExportDataEntity();
        v.setLicenseno("xxxx");
        list.add(v);
        ExportExcel exportExcel = new ExportExcel("", ExportDataEntity.class).setDataList(list);
        exportExcel.writeFile("D:\\xx1.xlsx").dispose();
    }
}

