package cn.edu.mydotabuff.tmpUI.impl;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.common.CommAdapter;
import cn.edu.mydotabuff.common.CommViewHolder;
import cn.edu.mydotabuff.model.SearchPlayerResult;
import cn.edu.mydotabuff.presenter.ILoginPresenter;
import cn.edu.mydotabuff.presenter.impl.LoginPresenterImpl;
import cn.edu.mydotabuff.tmpUI.ILoginView;
import cn.edu.mydotabuff.ui.MainActivity;

public class ActLogin extends ActBase implements ILoginView {

    @BindView(R.id.player_search_spinner)
    Spinner mSpinner;
    @BindView(R.id.et_search_word)
    EditText mKeywordEdittext;
    @BindView(R.id.btn_search)
    Button mSearchBtn;


    private SharedPreferences myPreferences;
    private String userID;
    private ILoginPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myPreferences = getSharedPreferences("user_info", Activity.MODE_PRIVATE);
        userID = myPreferences.getString("userID", "");
        if (myPreferences.getString("isLogin", "").equals("true")) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            // requestWindowFeature(Window.FEATURE_NO_TITLE);
            mPresenter = new LoginPresenterImpl(this);
            initView();
        }
    }

    public void initView() {
        ButterKnife.setDebug(true);
        setContentView(R.layout.act_login);
        if (!TextUtils.isEmpty(userID)) {
            mKeywordEdittext.setText(userID);
        } else {
            mKeywordEdittext.setText("172750452");
        }
    }


    @OnClick(R.id.btn_search)
    public void submit(View view) {
        if (TextUtils.isEmpty(mKeywordEdittext.getText())) {
            showToast(getString(R.string.search_player_content_empty_hint));
        } else {
            mPresenter.searchPlayer(mKeywordEdittext.getText().toString(),
                    mSpinner.getSelectedItemPosition() == 0);
        }

    }


    @Override
    public void showResult(List<SearchPlayerResult> searchPlayerResults) {
        if (searchPlayerResults.size() > 0) {
            showwResultDialog(searchPlayerResults);
        } else {
            showToast("没有匹配结果");
        }
    }

    private void showwResultDialog(final List<SearchPlayerResult> beans) {
        View dlgView = getLayoutInflater().inflate(R.layout.dlg_user_list,
                null);
        ListView list = (ListView) dlgView.findViewById(R.id.list);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("搜索结果:")
                .setView(dlgView)
                .setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                            }
                        }).create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        list.setAdapter(new CommAdapter<SearchPlayerResult>(this, beans,
                R.layout.dlg_user_list_item) {

            @Override
            public void convert(CommViewHolder helper, SearchPlayerResult item) {
                helper.setText(R.id.name, item.personaName);
                helper.setText(R.id.id, item.accountId);
                helper.setImagUri(R.id.icon, item.avatarfull);
            }
        });
        list.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                startActivity(new Intent(ActLogin.this, MainActivity.class));
                finish();
            }
        });
    }


//    @SuppressWarnings("unchecked")
//    @Override
//    public <T> void onGetFinished(T data) {
//        // TODO Auto-generated method stub
//        dialog.dismiss();
//        // size=1 只有一条数据 直接跳转
//        final ArrayList<UserInfo> beans = (ArrayList<UserInfo>) data;
//        if (beans.size() == 1) {
//            UserInfo info = (UserInfo) beans.get(0);
//            String ID = info.getUserID().trim();
//            saveUserInfo(ID);
//            startActivity(new Intent(this, ActMain.class));
//            finish();
//        } else {
//        }
//    }

    private void saveUserInfo(String id) {
        // TODO Auto-generated method stub
        Editor editor = myPreferences.edit();
        editor.putString("userID", id);
        editor.putString("isLogin", "true");
        editor.commit();
    }


}
