package com.zhonghe.shiangou.ui.activity;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.zhonghe.lib_base.utils.Util;
import com.zhonghe.lib_base.utils.UtilString;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.AddressInfo;
import com.zhonghe.shiangou.data.bean.AddressSelectInfo;
import com.zhonghe.shiangou.http.HttpUtil;
import com.zhonghe.shiangou.system.constant.CstProject;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;
import com.zhonghe.shiangou.ui.dialog.AreaSelectListDialog;
import com.zhonghe.shiangou.ui.listener.ResultListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 编辑地址
 */
public class ChangeAddressActivity extends BaseTopActivity {

    @BindView(R.id.id_addressmsg_person_et)
    EditText idAddressmsgPersonEt;
    @BindView(R.id.id_addressmsg_phone_et)
    EditText idAddressmsgPhoneEt;
    @BindView(R.id.id_addressmsg_address_et)
    TextView idAddressmsgAddressEt;
    @BindView(R.id.id_address_area_rl)
    RelativeLayout idAddressAreaRl;
    @BindView(R.id.id_addressmsg_addressmsg_et)
    EditText idAddressmsgAddressmsgEt;
    @BindView(R.id.id_address_confirm_bt)
    Button idAddressConfirmBt;

    AddressInfo addressInfo;
    //地区选择 dialog
    AreaSelectListDialog dialog;
    //地区Id
    String mProvinceId = "0", mCityId, mDistrictId;
    //地区文字
    String mAreaStr = "", provinceStr, cityStr, districtStr;

    AddressSelectInfo provinceList;

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
        provinceList = new AddressSelectInfo();
        Intent intent = getIntent();
        addressInfo = (AddressInfo) intent.getSerializableExtra(CstProject.KEY.DATA);
        getArea(mProvinceId);
        setAddMsg();
    }

    void setAddMsg() {
        if (addressInfo != null) {
            idAddressmsgPersonEt.setText(addressInfo.getConsignee());
            idAddressmsgPhoneEt.setText(addressInfo.getMobile());
            idAddressmsgAddressmsgEt.setText(addressInfo.getAddress());
            idAddressmsgAddressEt.setText(addressInfo.getArea_address());
        }
    }

    void getArea(String areaId) {
        setWaitingDialog(true);
        Request<?> request = HttpUtil.getAddressSelect(mContext, areaId, new ResultListener() {
            @Override
            public void onFail(String error) {
                setWaitingDialog(false);
                Util.toast(mContext, error);
            }

            @Override
            public void onSuccess(Object obj) {
                setWaitingDialog(false);
                provinceList = (AddressSelectInfo) obj;
            }
        });
        addRequest(request);
    }

    void addAddaress(String consignee, String phone, String addressStr) {
        setWaitingDialog(true);
        Request<?> request = HttpUtil.getAddressAdd(mContext, mAreaStr, consignee, phone, mProvinceId, mCityId, mDistrictId, addressStr, new ResultListener() {
            @Override
            public void onFail(String error) {
                setWaitingDialog(false);
                Util.toast(mContext, error);
            }

            @Override
            public void onSuccess(Object obj) {
                setWaitingDialog(false);
                finish();
            }
        });
        addRequest(request);
    }

    @OnClick({R.id.id_address_area_rl, R.id.id_address_confirm_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.id_address_area_rl:
                if (provinceList.getAddress().size() > 0) {
                    if (dialog == null) {
                        dialog = new AreaSelectListDialog(mContext, provinceList.getAddress());
                        dialog.setAddareaConfirm(new AreaSelectListDialog.AddareaConfirm() {
                            @Override
                            public void onConfirmArea(String areaStr, String provinceId, String cityId, String districtId) {
                                mProvinceId = provinceId;
                                mDistrictId = districtId;
                                mCityId = cityId;
                                mAreaStr = areaStr;
                                idAddressmsgAddressEt.setText(areaStr);
                            }
                        });
                    }
                    dialog.showAtLocation(view, Gravity.BOTTOM, 0, 0);

                }
                break;
            case R.id.id_address_confirm_bt:

                String persionStr = idAddressmsgPersonEt.getText().toString();
                if (UtilString.isBlank(persionStr)) {
                    Util.toast(mContext, R.string.address_tip_consignee);
                    break;
                }
                String phoneStr = idAddressmsgPhoneEt.getText().toString();
                if (UtilString.isBlank(phoneStr)) {
                    Util.toast(mContext, R.string.address_tip_consignee);
                    break;
                }
                String addressStr = idAddressmsgAddressmsgEt.getText().toString();
                if (UtilString.isBlank(addressStr)) {
                    Util.toast(mContext, R.string.address_tip_consignee);
                    break;
                }
                if (UtilString.isBlank(mAreaStr)) {
                    Util.toast(mContext, R.string.address_tip_consignee);
                    break;
                }
                addAddaress(persionStr, phoneStr, addressStr);
                break;
        }
    }
}
