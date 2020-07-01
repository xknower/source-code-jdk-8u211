package com.xknower.utils.excel;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
//import com.sinoxx.common.utils.DateUtil;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

/**
 * @Author lujiaming
 * @Date 2019/5/22
 * @Description 经纬度转换成中文地址工具类
 **/
public class ExcelExportUtil {
    /**
     * 经纬度转换成中文地址拼接成字符串
     *
     * @param object
     * @return
     */
    public static String buildAdress(JSONObject object) {
        StringBuilder buf = new StringBuilder();
        //构建中文地理位置
        buf.append(object.get("nation").toString())
                .append(object.get("province").toString())
                .append(object.get("city").toString())
                .append(object.get("district").toString())
                .append(object.get("street").toString())
                .append(object.get("township").toString())
                .append(object.get("address").toString())
                .append(object.get("direction").toString())
                .append(object.get("distance").toString());
        return buf.toString();
    }

    /**
     * 获取reportDate共同方法
     */
    public static Date[] getDates(Map<String, Object> searchCondition) {
        if (!searchCondition.containsKey("reportDate") && searchCondition.get("reportDate") == null) {
            return null;
        }
        if (searchCondition.containsKey("page")) {
            searchCondition.remove("page");
        }
        if (searchCondition.containsKey("pageSize")) {
            searchCondition.remove("pageSize");
        }
        JSONArray objects = (JSONArray) searchCondition.get("reportDate");
        Date[] dates = new Date[2];
//        try {
//            dates[0] = DateUtil.str2date(objects.get(0).toString());
//            dates[1] = DateUtil.str2date(objects.get(1).toString());
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        searchCondition.remove("reportDate");
        return dates;
    }
}
