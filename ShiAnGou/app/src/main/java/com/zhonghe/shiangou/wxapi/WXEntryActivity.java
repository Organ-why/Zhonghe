package com.zhonghe.shiangou.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhonghe.lib_base.utils.UtilLog;
import com.zhonghe.lib_base.utils.UtilString;
import com.zhonghe.shiangou.system.constant.CstProject;


public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
	private IWXAPI mApi;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mApi = WXAPIFactory.createWXAPI(this, CstProject.WEIXIN.WEIXIN_APP_ID, false);
		mApi.handleIntent(getIntent(), this);
	}
	
	@Override
	public void onResp(BaseResp arg0) {
		UtilLog.d("onResp arg0.errCode="+arg0.errCode);
		switch (arg0.errCode) {
			case BaseResp.ErrCode.ERR_OK:
				if(arg0 instanceof SendAuth.Resp) {
					SendAuth.Resp resp = (SendAuth.Resp) arg0;
//					UtilLog.d("SendAuth resp token:"+ resp.token+"---expiredate:"+resp.expireDate
//							+"---username:"+resp.userName+"---state:"+resp.state
//							+"---resulturl:"+resp.resultUrl);
					
//					if(UtilString.isNotBlank(resp.code)
//							&& CstOuer.WEIXIN.WEIXIN_LOGIN_STATE.equals(resp.state)) {
//						OuerDispatcher.sendWeixinLoginBroadcast(this, resp.code);
//					}
				} 
				break;
			case BaseResp.ErrCode.ERR_USER_CANCEL:
				break;
			case BaseResp.ErrCode.ERR_AUTH_DENIED:
				break;
			default:
				break;
		}
		
		finish();
	}


	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		mApi.handleIntent(intent, this);
	}
	
	@Override
	public void onReq(BaseReq arg0) {
		
	}
}
