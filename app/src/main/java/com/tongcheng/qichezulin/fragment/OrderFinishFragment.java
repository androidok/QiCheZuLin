package com.tongcheng.qichezulin.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiongbull.jlog.JLog;
import com.pacific.adapter.Adapter;
import com.pacific.adapter.AdapterHelper;
import com.tongcheng.qichezulin.Param.ParamOrderList;
import com.tongcheng.qichezulin.R;
import com.tongcheng.qichezulin.model.JsonBase2;
import com.tongcheng.qichezulin.model.OrderModel;
import com.tongcheng.qichezulin.pulltorefresh.PullToRefreshLayout;
import com.tongcheng.qichezulin.pulltorefresh.PullableListView;
import com.tongcheng.qichezulin.utils.UtilsJson;
import com.tongcheng.qichezulin.utils.UtilsUser;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 林尧 on 2016/8/18.
 */

@ContentView(R.layout.fragment_order_finish)
public class OrderFinishFragment extends Fragment {

    public Adapter adapter;
    Map<String, Boolean> delete_BooleanHashMap = new HashMap<String, Boolean>(); //记录要删除的id
    Map<Integer, Boolean> positon_BooleanHashMap = new HashMap<Integer, Boolean>(); //记录要移除的位置

    @ViewInject(R.id.buuuuuuu)
    Button button;
    //===============================end
    String user_id = "";
    String status = "3";
    int page = 1;
    String page_size = "10";
    @ViewInject(R.id.prl_prl_03) //上下拉控件
            PullToRefreshLayout prl_prl_03;
    @ViewInject(R.id.plv_order_finish_list)
    PullableListView plv_order_finish_list; //list 控件
    private ListenerOnOrderFinishFragment listenerOnOrderFinishFragment;
    private TextView tv_second; //编辑按钮
    private boolean injected = false;

    /**
     * Fragment第一次附属于Activity时调用,在onCreate之前调用
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listenerOnOrderFinishFragment = (ListenerOnOrderFinishFragment) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        injected = true;
        return x.view().inject(this, inflater, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!injected) {
            x.view().inject(this, this.getView());
        }
        setOnPullListenerOnprl_prl();
        user_id = UtilsUser.getUser(getContext()).PID;
        get_order_yu_yue_list(user_id, status, page, page_size);


        //对activity的 回调
    /*    button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenerOnOrderFinishFragment.do_work();
            }
        });*/

        tv_second = (TextView) getActivity().findViewById(R.id.tv_second);
        tv_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JLog.w("OrderFinishFragment");
            }
        });


    }

    public void setOnPullListenerOnprl_prl() {
        prl_prl_03.setOnPullListener(new PullToRefreshLayout.OnPullListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                // get_order_yu_yue_list(user_id,status,page,page_size);
                pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
            }
        });
    }

    //获取预约订单数据
    public void get_order_yu_yue_list(String user_id, String status, int page, String page_size) {
        ParamOrderList paramOrderList = new ParamOrderList();
        paramOrderList.user_id = user_id;
        paramOrderList.status = status;
        paramOrderList.page = page + "";
        paramOrderList.page_size = page_size;
        Callback.Cancelable cancelable
                = x.http().post(paramOrderList, new Callback.CommonCallback<String>() {
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }


            @Override
            public void onFinished() {

            }

            @Override
            public void onSuccess(String result) {
                try {
                    UtilsJson.printJsonData(result);
                    Gson gson = new Gson();
                    Type type = new TypeToken<JsonBase2<List<OrderModel>>>() {
                    }.getType();
                    JsonBase2<List<OrderModel>> base = gson
                            .fromJson(result, type);
                    if (!base.status.toString().trim().equals("0")) {
                        if (base.data != null) {
                            JLog.w("获取已完成订单成功");
                            if (adapter == null) {
                                adapter = new Adapter<OrderModel>(getActivity(), R.layout.listview_item_order_finish) {
                                    @Override
                                    protected void convert(final AdapterHelper helper, final OrderModel orderModel) {
                                        final int position = helper.getPosition();
                                        delete_BooleanHashMap.put(orderModel.PID, false);
                                        positon_BooleanHashMap.put(position, false);
                                        helper.setImageUrl(R.id.iv_car_picture, orderModel.FImg)
                                                .setText(R.id.tv_order_number_show, orderModel.PID)
                                                .setText(R.id.tv_show_qu_che_shop, orderModel.FShopName)
                                                .setText(R.id.tv_show_qu_che_time, orderModel.FStartTime)
                                                .setText(R.id.tv_show_crete_date, orderModel.FCreateDate)
                                                .setImageResource(R.id.iv_is_zia_xian_tou_su, R.mipmap.zai_xian_tou_su);
                                        helper.getView(R.id.ck_is_choose).setVisibility(View.VISIBLE);
                                        CheckBox checkBox = helper.getView(R.id.ck_is_choose);
                                        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                            @Override
                                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                                JLog.w("...>>>>>>" + b);
                                                if (b) {
                                                    delete_BooleanHashMap.put(orderModel.PID, true);
                                                    positon_BooleanHashMap.put(position, true);
                                                    JLog.w(delete_BooleanHashMap.size() + "");
                                                    JLog.w(positon_BooleanHashMap.size() + "");
                                                } else {
                                                    delete_BooleanHashMap.put(orderModel.PID, false);
                                                    positon_BooleanHashMap.put(position, false);
                                                    JLog.w(delete_BooleanHashMap.size() + "");
                                                    JLog.w(positon_BooleanHashMap.size() + "");
                                                }
                                            }
                                        });
                                    }
                                };

                                adapter.addAll(base.data);
                                plv_order_finish_list.setAdapter(adapter);
                            } else {
                                adapter.addAll(base.data);
                                adapter.notifyDataSetChanged();
                            }


                        }
                    } else {
                        JLog.w("获取预约订单失败");
                    }
                } catch (Exception E) {
                    E.printStackTrace();
                }

            }
        });
    }

    //=======fg跟新 acticity可以通过接口方式============begin
    public interface ListenerOnOrderFinishFragment {
        void do_work();
    }
}
