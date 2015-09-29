package cn.edu.mydotabuff.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

import com.umeng.comm.core.CommunitySDK;
import com.umeng.comm.core.beans.CommConfig;
import com.umeng.comm.core.impl.CommunityFactory;
import com.umeng.comm.core.sdkmanager.LoginSDKManager;
import com.umeng.community.login.UMAuthService;
import com.umeng.community.login.UMLoginServiceFactory;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.sso.UMQQSsoHandler;

import java.util.Map;
import java.util.Set;

import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.base.BaseFragment;

public class FragFound extends BaseFragment implements OnClickListener {

    private View view;
    private CommunitySDK mCommSDK;

    @Override
    protected View initViewAndData(LayoutInflater inflater,
                                   @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        view = inflater.inflate(R.layout.frag_found_base, null);
        return view;
    }

    @Override
    protected void initEvent() {
        // TODO Auto-generated method stub
        view.findViewById(R.id.board).setOnClickListener(this);
        view.findViewById(R.id.news).setOnClickListener(this);
        view.findViewById(R.id.community).setOnClickListener(this);
        view.findViewById(R.id.live).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        int id = v.getId();
        switch (id) {
            case R.id.board:
                startActivity(new Intent(getActivity(), ActBoard.class));
                break;
            case R.id.news:
                startActivity(new Intent(getActivity(), ActNewsList.class));
                break;
            case R.id.community:
                final LoginSDKManager manager = CommConfig.getConfig().getLoginSDKManager();
                UMAuthService mLogin = UMLoginServiceFactory.getLoginService("umeng_login_impl");
                // 将登录实现注入到sdk中,key为umeng_login
//                manager.addImpl("umeng_login",mLogin );
//                manager.useThis("umeng_login");


                String QQappID = "1103458121";
                String QQappSecret = "PtcezFKwEHmAF0t9";
                UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(getActivity(), QQappID,
                        QQappSecret);
                qqSsoHandler.addToSocialSDK();
                final UMSocialService mController = UMServiceFactory
                        .getUMSocialService("com.umeng.share");
                mController.doOauthVerify(getActivity(), SHARE_MEDIA.QQ, new SocializeListeners.UMAuthListener() {
                    @Override
                    public void onStart(SHARE_MEDIA platform) {
                        Toast.makeText(getActivity(), "授权开始", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(SocializeException e, SHARE_MEDIA platform) {
                        Toast.makeText(getActivity(), "授权错误", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete(Bundle value, SHARE_MEDIA platform) {
                        Toast.makeText(getActivity(), "授权完成", Toast.LENGTH_SHORT).show();
                        //获取相关授权信息
                        mController.getPlatformInfo(getActivity(), SHARE_MEDIA.QQ, new SocializeListeners.UMDataListener() {
                            @Override
                            public void onStart() {
                                Toast.makeText(getActivity(), "获取平台数据开始...", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onComplete(int status, Map<String, Object> info) {
                                if (status == 200 && info != null) {
                                    StringBuilder sb = new StringBuilder();
                                    Set<String> keys = info.keySet();
                                    for (String key : keys) {
                                        sb.append(key + "=" + info.get(key).toString() + "\r\n");
                                    }
                                    Log.d("TestData", sb.toString());
                                    manager.addImpl(MyLoginImpl.class.getSimpleName(), new MyLoginImpl(info));
                                    // 使用该登录实现
                                    manager.useThis(MyLoginImpl.class.getSimpleName());


                                    // 打开微社区的接口, 参数1为Context类型
                                    mCommSDK.openCommunity(getActivity());
                                } else {
                                    Log.d("TestData", "发生错误：" + status);
                                }
                            }
                        });
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA platform) {
                        Toast.makeText(getActivity(), "授权取消", Toast.LENGTH_SHORT).show();
                    }
                });

                break;
            case R.id.live:
                startActivity(new Intent(getActivity(), ActTrack.class));
                break;
            default:
                break;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // 获取CommunitySDK实例, 参数1为Context类型
        if(mCommSDK == null) {
            mCommSDK = CommunityFactory.getCommSDK(context);
        }
    }
}
