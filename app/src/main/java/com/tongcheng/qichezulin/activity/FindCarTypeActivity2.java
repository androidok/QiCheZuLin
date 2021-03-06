package com.tongcheng.qichezulin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;

import com.code19.library.DateUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiongbull.jlog.JLog;
import com.pacific.adapter.Adapter;
import com.pacific.adapter.AdapterHelper;
import com.tongcheng.qichezulin.Param.ParamChooseCar;
import com.tongcheng.qichezulin.Param.ParamGetAllCar;
import com.tongcheng.qichezulin.R;
import com.tongcheng.qichezulin.model.CarModel2;
import com.tongcheng.qichezulin.model.CarModel3;
import com.tongcheng.qichezulin.model.JsonBase;
import com.tongcheng.qichezulin.pulltorefresh.PullToRefreshLayout;
import com.tongcheng.qichezulin.pulltorefresh.PullableListView;
import com.tongcheng.qichezulin.utils.Utils;
import com.tongcheng.qichezulin.utils.UtilsJson;
import com.tongcheng.qichezulin.utils.UtilsTiaoZhuang;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by 林尧 on 2016/8/10.
 */

@ContentView(R.layout.activity_find_car_type2)
public class FindCarTypeActivity2 extends PuTongActivity2 {


    @ViewInject(R.id.iv_my_chose)
    ImageButton iv_my_chose; //筛选按钮

    String shop_id="";
    String start_time="";
    String end_time="";

    int page = 1;

    @ViewInject(R.id.prl_prl) //下拉刷新控件
            PullToRefreshLayout prl_prl;
    @ViewInject(R.id.plv_car_list)
    PullableListView plv_car_list; //listview
    Adapter<CarModel3> catModel2Adapter;


    private boolean isFirstIn = true;


    @Override
    void initData() {

    }

    @Override
    void initView() {
        tv_first.setVisibility(View.VISIBLE);
        tv_first.setText("选择车型");
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_return:
                onBackPressed();
                break;
            case R.id.iv_my_chose:
                UtilsTiaoZhuang.ToAnotherActivity(FindCarTypeActivity2.this, ChoseActivity.class, 0);
                break;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iv_my_chose.setOnClickListener(this);
        initView();
        setListenerOnView();
        setOnPullListenerOnprl_prl();
        shop_id=getIntent().getExtras().getString("shop_id");
        start_time=getIntent().getExtras().getString("start_time");
        end_time=getIntent().getExtras().getString("end_time");
        do_get_data(shop_id, "", "", "", "", "");



    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        // 第一次进入自动刷新
      /*  if (isFirstIn) {
            prl_prl.autoRefresh();
            isFirstIn = false;
        }*/
    }

    public void setOnPullListenerOnprl_prl() {
        prl_prl.setOnPullListener(new PullToRefreshLayout.OnPullListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
            }
        });
    }


    // 获取列表数据
    private void do_get_data(String shop_id, String is_auto, String type, String paytype, String min_price, String max_price) {
        ParamChooseCar paramChooseCar = new ParamChooseCar();
        paramChooseCar.shop_id = shop_id;
        paramChooseCar.is_auto = is_auto;
        paramChooseCar.type = type;
        paramChooseCar.paytype = paytype;
        paramChooseCar.max_price = max_price;
        paramChooseCar.min_price = min_price;
        Callback.Cancelable cancelable
                = x.http().post(paramChooseCar, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    UtilsJson.printJsonData(result);
                    Gson gson = new Gson();
                    Type type = new TypeToken<JsonBase<ArrayList<CarModel3>>>() {
                    }.getType();
                    JsonBase<ArrayList<CarModel3>> base = gson
                            .fromJson(result, type);
                    if (!base.status.toString().trim().equals("0")) {
                        if (base.data != null) {
                            JLog.w(base.data.size() + "");
                            if (base.data.size() > 0) {
                                JLog.w("获取数据成功");
                                if (catModel2Adapter == null) {
                                    catModel2Adapter = new Adapter<CarModel3>(FindCarTypeActivity2.this, R.layout.listview_item_car2) {
                                        @Override
                                        protected void convert(AdapterHelper helper, final CarModel3 item) {
                                            final int position = helper.getPosition();
                                            JLog.w(position + "");
                                            if (Integer.parseInt(item.FCount.trim()) - Integer.parseInt(item.FUseCount.trim()) > 0) {
                                                helper.setImageUrl(R.id.iv_car_picture, item.FImg)
                                                        .setText(R.id.tv_car_name, item.FCarName)
                                                        .setText(R.id.tv_car_remark, item.FRemark)
                                                        .setText(R.id.tv_car_price, "¥" + item.FDayMoney + "/")
                                                        .setText(R.id.tv_car_yue_price, "¥" + item.FMonthMoney + "/")
                                                        .setVisible(R.id.pll_is_zu_man, View.GONE)
                                                        .setVisible(R.id.line1233, View.GONE)
                                                        .getView(R.id.rrl_item).setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        Bundle bundle = new Bundle();
                                                        bundle.putString("days", getIntent().getExtras().getString("days"));
                                                        bundle.putString("start_time", getIntent().getExtras().getString("start_time"));
                                                        bundle.putString("end_time", getIntent().getExtras().getString("end_time"));
                                                        bundle.putString("shop_id", getIntent().getExtras().getString("shop_id"));
                                                        bundle.putSerializable("obj", item);
                                                        UtilsTiaoZhuang.ToAnotherActivity(FindCarTypeActivity2.this, YuYueActivity2.class, bundle);

                                                    }
                                                });
                                            } else {
                                                helper.setImageUrl(R.id.iv_car_picture, item.FImg)
                                                        .setText(R.id.tv_car_name, item.FCarName)
                                                        .setText(R.id.tv_car_remark, item.FRemark)
                                                        .setText(R.id.tv_car_price, "¥" + item.FDayMoney + "/")
                                                        .setText(R.id.tv_car_yue_price, "¥" + item.FMonthMoney + "/")
                                                        .setVisible(R.id.pll_is_zu_man, View.VISIBLE)
                                                        .setVisible(R.id.line1233, View.VISIBLE)
                                                        .getView(R.id.rrl_item).setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        Utils.ShowText2(FindCarTypeActivity2.this, "该车已经被组满");
                                                    }
                                                });
                                            }
                                        }
                                    };
                                    catModel2Adapter.addAll(base.data);
                                    plv_car_list.setAdapter(catModel2Adapter);
                                } else {
                                    catModel2Adapter.addAll(base.data);
                                    catModel2Adapter.notifyAll();
                                }
                            } else {
                                Utils.ShowText(FindCarTypeActivity2.this, "没有符合条件的车");
                            }

                        }
                    } else {
                        Utils.ShowText(FindCarTypeActivity2.this, base.info.toString());
                    }
                } catch (Exception E) {

                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                JLog.w(isOnCallback + "");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                JLog.w(cex + "");
            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
            case RESULT_OK:
                Bundle b = data.getExtras(); //data为B中回传的Intent
                String min = b.getString("min");//str即为回传的值
                String max = b.getString("max");//str即为回传的值
                String carType= b.getString("carType");//str即为回传的值
                JLog.w(min);
                JLog.w(max);
                JLog.w(carType);
                catModel2Adapter.clear();
                prl_prl.autoRefresh();
                do_get_data(shop_id, "",carType, "2", min,max);
        }
    }
}
