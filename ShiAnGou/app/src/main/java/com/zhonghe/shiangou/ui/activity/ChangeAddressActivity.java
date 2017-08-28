package com.zhonghe.shiangou.ui.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.zhonghe.lib_base.utils.Utilm;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.AddressInfo;
import com.zhonghe.shiangou.http.HttpUtil;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;
import com.zhonghe.shiangou.ui.dialog.AreaSelectDialog;
import com.zhonghe.shiangou.ui.listener.ResultListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 编辑地址
 */
public class ChangeAddressActivity extends BaseTopActivity {

    @Bind(R.id.id_addressmsg_person_et)
    EditText idAddressmsgPersonEt;
    @Bind(R.id.id_addressmsg_phone_et)
    EditText idAddressmsgPhoneEt;
    @Bind(R.id.id_addressmsg_address_et)
    TextView idAddressmsgAddressEt;
    @Bind(R.id.id_address_area_rl)
    RelativeLayout idAddressAreaRl;
    @Bind(R.id.id_addressmsg_addressmsg_et)
    EditText idAddressmsgAddressmsgEt;
    @Bind(R.id.id_address_confirm_bt)
    Button idAddressConfirmBt;

    //地区选择 dialog
    AreaSelectDialog dialog;
    //地区Id
    String provinceId = "1", cityId, districtId;
    //地区文字
    String provinceStr, cityStr, districtStr;

    List<AddressInfo> provinceList;

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_changeaddress);
        ButterKnife.bind(this);
    }

    @Override
    protected void initTop() {
        setTitle(R.string.address_addresschange_title_text);
        setNavigation(R.mipmap.common_nav_back);
    }

    @Override
    protected void initViews() {
        provinceList = new ArrayList<>();
        getArea(provinceId);
    }

    void getArea(String areaId) {
        setWaitingDialog(true);
        Request<?> request = HttpUtil.getAddressSelect(mContext, areaId, new ResultListener() {
            @Override
            public void onFail(String error) {
                setWaitingDialog(false);
                Utilm.toast(mContext, error);
            }

            @Override
            public void onSuccess(Object obj) {
                setWaitingDialog(false);
                provinceList = (List<AddressInfo>) obj;
            }
        });
        addRequest(request);
    }


    @OnClick({R.id.id_address_area_rl, R.id.id_address_confirm_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.id_address_area_rl:
                if (provinceList.size() > 0) {
                    if (dialog == null)
                        dialog = new AreaSelectDialog(mContext, provinceList);
                    dialog.showAtLocation(view, Gravity.BOTTOM, 0, 0);

                }
                break;
            case R.id.id_address_confirm_bt:
                break;
        }
    }
}
