package com.tongcheng.qichezulin.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiongbull.jlog.JLog;
import com.pacific.adapter.Adapter;
import com.pacific.adapter.AdapterHelper;
import com.tongcheng.qichezulin.Param.ParamInvoicelist;
import com.tongcheng.qichezulin.R;
import com.tongcheng.qichezulin.activity.AddFaPiaoTaiTouActivity;
import com.tongcheng.qichezulin.model.InvoicelistModel;
import com.tongcheng.qichezulin.model.JsonBase2;
import com.tongcheng.qichezulin.pulltorefresh.PullToRefreshLayout;
import com.tongcheng.qichezulin.pulltorefresh.PullableListView;
import com.tongcheng.qichezulin.utils.Utils;
import com.tongcheng.qichezulin.utils.UtilsJson;
import com.tongcheng.qichezulin.utils.UtilsTiaoZhuang;
import com.tongcheng.qichezulin.utils.UtilsUser;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by 林尧 on 2016/8/18.
 */

public class FaPiaoTaiTouFragment extends Fragment implements View.OnClickListener {

    public Adapter adapter;
    private TextView iv_add_fa_piao;
    private PullToRefreshLayout prl_prl_05;
    private PullableListView plv_fa_piao_list; //list 控件

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fa_piao_tai_tou, null);
        prl_prl_05 = (PullToRefreshLayout) v.findViewById(R.id.prl_prl_05);
        plv_fa_piao_list = (PullableListView) v.findViewById(R.id.plv_fa_piao_list);
        iv_add_fa_piao = (TextView) v.findViewById(R.id.iv_add_fa_piao);
        iv_add_fa_piao.setOnClickListener(this);
        setOnPullListenerOnprl_prl();
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        get_fa_piaos(UtilsUser.getUserID(getActivity()));


    }

    public void setOnPullListenerOnprl_prl() {
        prl_prl_05.setOnPullListener(new PullToRefreshLayout.OnPullListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                get_fa_piaos(UtilsUser.getUserID(getActivity()));
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                // get_order_yu_yue_list(user_id,status,page,page_size);
                pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
            }
        });
    }

    //获取多个发票抬头
    public void get_fa_piaos(String user_id) {
        ParamInvoicelist paramInvoicelist = new ParamInvoicelist();
        paramInvoicelist.user_id = user_id;
        Callback.Cancelable cancelable
                = x.http().post(paramInvoicelist, new Callback.CommonCallback<String>() {
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
                    Type type = new TypeToken<JsonBase2<List<InvoicelistModel>>>() {
                    }.getType();
                    JsonBase2<List<InvoicelistModel>> base = gson
                            .fromJson(result, type);
                    if (!base.status.toString().trim().equals("0")) {
                        JLog.w("获取发票抬头成功");
                        if (adapter == null) {
                            adapter = new Adapter<InvoicelistModel>(getActivity(), R.layout.listview_item_fapiao) {
                                @Override
                                protected void convert(final AdapterHelper helper, final InvoicelistModel item) {
                                    final int position = helper.getPosition();
                                    helper.setText(R.id.tv_show_fapiao_title, item.FName);
                                }
                            };

                            adapter.addAll(base.data);
                            plv_fa_piao_list.setAdapter(adapter);
                        } else {
                            adapter.clear();
                            adapter.addAll(base.data);
                            adapter.notifyDataSetChanged();
                        }

                    } else {
                        JLog.w("获取发票抬头失败");

                    }
                } catch (Exception E) {
                    E.printStackTrace();
                }

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_add_fa_piao:
                UtilsTiaoZhuang.ToAnotherActivity(getActivity(), AddFaPiaoTaiTouActivity.class);
                break;
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        JLog.w("onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        JLog.w("onDestroy");
    }

    @Override
    public void onResume() {
        super.onResume();
        JLog.w("onResume");
        if (AddFaPiaoTaiTouActivity.flag == null) {
            
        }else if (AddFaPiaoTaiTouActivity.flag == 0) {

        }else if (AddFaPiaoTaiTouActivity.flag == 1) {
            prl_prl_05.autoRefresh();
            if (UtilsUser.getUser(getContext()) != null) {
                get_fa_piaos(UtilsUser.getUser(getContext()).PID);
            }
            AddFaPiaoTaiTouActivity.flag = 0;
        }

    }
}
