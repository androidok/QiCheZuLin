package com.tongcheng.qichezulin.Param;

import com.tongcheng.qichezulin.config.AppConfig;

import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;

/**
 * Created by 林尧 on 2016/7/26.
 */
@HttpRequest(
        host = AppConfig.HOST,
        path = AppConfig.SET_INVOICE
)
public class ParamSetInvoice extends RequestParams {
    public String user_id;
    public String name;//名称/单位名称
}
