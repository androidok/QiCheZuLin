package com.tongcheng.qichezulin.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.code19.library.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiongbull.jlog.JLog;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tongcheng.qichezulin.Param.ParamGetUserInfo;
import com.tongcheng.qichezulin.R;
import com.tongcheng.qichezulin.activity.AccountActivity;
import com.tongcheng.qichezulin.activity.DepositActivity;
import com.tongcheng.qichezulin.activity.MyBaoZhengJinActivity;
import com.tongcheng.qichezulin.activity.MyFaPiaoctivity;
import com.tongcheng.qichezulin.activity.MyJiFenActivity;
import com.tongcheng.qichezulin.activity.MyOrderActivity;
import com.tongcheng.qichezulin.activity.MyTouSuActivity;
import com.tongcheng.qichezulin.activity.MyWallectActivity;
import com.tongcheng.qichezulin.activity.LoginActivity;
import com.tongcheng.qichezulin.model.JsonBase;
import com.tongcheng.qichezulin.model.OrderModel;
import com.tongcheng.qichezulin.model.UserModel;
import com.tongcheng.qichezulin.utils.UtilsJson;
import com.tongcheng.qichezulin.utils.UtilsString;
import com.tongcheng.qichezulin.utils.UtilsTiaoZhuang;
import com.tongcheng.qichezulin.utils.UtilsUser;
import com.zhy.android.percent.support.PercentRelativeLayout;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.blurry.Blurry;

/**
 * 个人片段
 */

@ContentView(R.layout.fragment_person)
public class PersonFragment extends PuTongFragment {

    @ViewInject(R.id.btn_login_out) //退出按钮
            Button btn_login_out;


    @ViewInject(R.id.rrl_zu_che_record) //租车记录
            PercentRelativeLayout rrl_zu_che_record;

    @ViewInject(R.id.rrl_my_ji_fen) //我的积分
            PercentRelativeLayout rrl_my_ji_fen;

    @ViewInject(R.id.rrl_my_wallect) //我的钱包
            PercentRelativeLayout rrl_my_wallect;

    @ViewInject(R.id.rrl_bao_zheng_jin) //我的保证金
            PercentRelativeLayout rrl_bao_zheng_jin;

    @ViewInject(R.id.rrl_my_fa_piao) //我的发票
            PercentRelativeLayout rrl_my_fa_piao;

    @ViewInject(R.id.rrl_my_tou_su) //我的投诉
            PercentRelativeLayout rrl_my_tou_su;


    @ViewInject(R.id.iv_head_photo)
    CircleImageView iv_head_photo;
    @ViewInject(R.id.rrl_zhang_hao)
    PercentRelativeLayout rrl_zhang_hao;
    //用户账号
    @ViewInject(R.id.tv_user_account)
    TextView tv_user_account;
    //从缓存中获取 user_id
    private String user_id;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initView();
        setClickListenerOnView();




    }

    @Override
    void setClickListenerOnView() {
        btn_login_out.setOnClickListener(this);
        rrl_zhang_hao.setOnClickListener(this);
        rrl_zu_che_record.setOnClickListener(this);
        rrl_my_ji_fen.setOnClickListener(this);
        rrl_my_wallect.setOnClickListener(this);
        rrl_bao_zheng_jin.setOnClickListener(this);
        rrl_my_tou_su.setOnClickListener(this);
        rrl_my_fa_piao.setOnClickListener(this);
        iv_head_photo.setOnClickListener(this);
    }

    @Override
    void initData() {
        user_id = (String) UtilsUser.getUserID(getActivity());
        JLog.w("参数user_id:" + user_id);
    }

    @Override
    void initView() {
        if (UtilsUser.getUSER_PHOTO(getActivity()).equals("")) {
            get_User_info();
        }else{
            ImageLoader.getInstance().displayImage(UtilsUser.getUSER_PHOTO(getActivity()), iv_head_photo);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rrl_zhang_hao:
                JLog.w("跳转到我的账号界面");
                UtilsTiaoZhuang.ToAnotherActivity(getActivity(), AccountActivity.class);
                break;
            case R.id.rrl_zu_che_record:
                JLog.w("跳转到我的订单界面");
                UtilsTiaoZhuang.ToAnotherActivity(getActivity(), MyOrderActivity.class);
                break;
            case R.id.rrl_my_ji_fen:
                JLog.w("跳转到我的积分界面");
                UtilsTiaoZhuang.ToAnotherActivity(getActivity(), MyJiFenActivity.class);
                break;
            case R.id.rrl_my_wallect:
                JLog.w("跳转到我的钱包界面");
                UtilsTiaoZhuang.ToAnotherActivity(getActivity(), MyWallectActivity.class);
                break;
            case R.id.rrl_bao_zheng_jin:
                JLog.w("跳转到我的保证金界面");
                UtilsTiaoZhuang.ToAnotherActivity(getActivity(), DepositActivity.class);
                break;
            case R.id.rrl_my_fa_piao:
                JLog.w("跳转到我的发票界面");
                UtilsTiaoZhuang.ToAnotherActivity(getActivity(), MyFaPiaoctivity.class);
                break;
            case R.id.rrl_my_tou_su:
                JLog.w("跳转到我的投诉界面");
                UtilsTiaoZhuang.ToAnotherActivity(getActivity(), MyTouSuActivity.class);
                break;
            case R.id.btn_login_out:
                JLog.w("退出操作");
                UtilsUser.cleanAllSP(getContext());
                UtilsTiaoZhuang.ToAnotherActivity(getActivity(), LoginActivity.class);
                getActivity().finish();
                break;
            case R.id.iv_head_photo:
                JLog.w("换头像");
                showAlertView();

        }
    }

    //获取个人信息
    public void get_User_info() {
        ParamGetUserInfo paramGetUserInfo = new ParamGetUserInfo();
        paramGetUserInfo.user_id = user_id.trim();
        paramGetUserInfo.token = UtilsUser.getToken(getActivity());
        Callback.Cancelable cancelable
                = x.http().post(paramGetUserInfo, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                UtilsJson.printJsonData(result);
                Gson gson = new Gson();
                Type type = new TypeToken<JsonBase<UserModel>>() {
                }.getType();
                JsonBase<UserModel> base = gson
                        .fromJson(result, type);
                if (!base.status.toString().trim().equals("0")) {
                    if (base.data != null) {
                        JLog.w("获取个人信息成功");
                        //判断有没有图片
                        if (StringUtils.isEmpty(base.data.FImg)) {
                            iv_head_photo.setImageResource(R.mipmap.default_head_photo);
                        } else {
                            ImageLoader.getInstance().displayImage(base.data.FImg, iv_head_photo);

                        }
                        tv_user_account.setText(UtilsString.hidePhoneNumber(base.data.FMobilePhone));
                        UtilsUser.setSP(getContext(), UtilsUser.USER_PHOTO,base.data.FImg); //缓存头像地址
                        UtilsUser.setSP(getContext(), UtilsUser.USER_NAME,base.data.FUserName); //缓存头像地址
                        UtilsUser.setSP(getContext(), UtilsUser.USER_PHOEN,base.data.FMobilePhone); //缓存头像地址
                        UtilsUser.setSP(getContext(), UtilsUser.FBONDMOENY,base.data.FBondMoeny); //缓存用户保证金
                        UtilsUser.setSP(getContext(), UtilsUser.USER_SEX,base.data.FSex); //缓存用户性别

                    }
                } else {
                    JLog.w("获取个人信息失败");
                }
            }


            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }


        });
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
    public void onPause() {
        super.onPause();
        JLog.w("onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        JLog.w("onResume");
    }


    public void showAlertView() {

        new AlertView(null, null, "取消", null,
                new String[]{"拍照", "从相册中选择"}, getActivity(), AlertView.Style.ActionSheet, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                if (position == 0) {

                } else if (position == 1) {

                } else if (position == -1) {

                }
            }
        }).show();


    }
}
