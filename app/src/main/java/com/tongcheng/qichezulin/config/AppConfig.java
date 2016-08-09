package com.tongcheng.qichezulin.config;

/**
 * Created by 林尧 on 2016/7/26.
 */
public class AppConfig {

    //域名 或者ip 地址
    public static final String HOST = "http://192.168.0.123:8090";

    //广告轮播地址
    public static final String GET_BANNER = "api/advert/get_advert.ashx";

    //广告轮播地址
    public static final String GET_REGIST = "api/user/register_user.ashx";


    //发送验证码
    public static final String GET_CHECK_CODE = "api/message/send_yzm.ashx";

    //密码登录
    public static final String GET_USER_LOGIN = "api/user/user_login.ashx";

    //热门车型列表
    public static final String GET_RE_MEN_CHE_XING = "api/car/get_hotcar.ashx";

    //验证码登录
    public static final String GET_USER_LOGIN_BY_CHECK_CODE = "api/user/user_loginbycode.ashx";

    //获取个人信息
    public static final String GET_USER_INFO = "api/user/get_userinfo.ashx";


    //二级目录
    public static final String GET_CITY_QU = "api/car/get_citycounty.ashx";

    //网点列表
    public static final String GET_WANG_DIAN_LIST = "api/shop/get_shoplist.ashx";


    //获取附近网点
    public static final String GET_DEFAULTSHOP = "api/shop/get_defaultshop.ashx";

    //获取默认网点(离我最近)
    public static final String GET_NEAR_DEFAULTSHOP = "api/car/get_defaultshop.ashx";

}
