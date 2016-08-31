package com.tongcheng.qichezulin.model;


import java.io.Serializable;

/**
 * Created by 林尧 on 2016/7/26.
 */


public class ZhiFuBaoOrderModel implements Serializable {
    public String body;//对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。
    public String subject; // 商品的标题/交易标题/订单标题/订单关键字等。
    public String out_trade_no;//商户网站唯一订单号
    public String timeout_express;//该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m
    public String total_amount; //订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]
    public String seller_id="luolg@stchezu.com";//收款支付宝用户ID。 如果该值为空，则默认为商户签约账号对应的支付宝用户ID
    public String product_code="2088121527311366"; //销售产品码，商家和支付宝签约的产品码
}