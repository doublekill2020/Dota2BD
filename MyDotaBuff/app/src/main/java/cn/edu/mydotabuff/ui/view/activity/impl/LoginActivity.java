package cn.edu.mydotabuff.ui.view.activity.impl;

import android.content.DialogInterface;
import android.content.Intent;
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
import cn.edu.mydotabuff.model.PlayerInfo;
import cn.edu.mydotabuff.model.Profile;
import cn.edu.mydotabuff.model.SearchPlayerResult;
import cn.edu.mydotabuff.ui.MainActivity;
import cn.edu.mydotabuff.ui.presenter.ILoginPresenter;
import cn.edu.mydotabuff.ui.presenter.impl.LoginPresenterImpl;
import cn.edu.mydotabuff.ui.view.activity.ILoginView;

public class LoginActivity extends ActBase implements ILoginView {

    @BindView(R.id.player_search_spinner)
    Spinner mSpinner;
    @BindView(R.id.et_search_word)
    EditText mKeywordEdittext;
    @BindView(R.id.btn_search)
    Button mSearchBtn;


    private ILoginPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new LoginPresenterImpl(this);
        initView();
    }

    public void initView() {
        ButterKnife.setDebug(true);
        setContentView(R.layout.act_login);
        mKeywordEdittext.setText("172750452");
    }


    @OnClick(R.id.btn_search)
    public void onSearchClicked(View view) {
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
        final AlertDialog dialog = new AlertDialog.Builder(this)
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
                PlayerInfo info = new PlayerInfo();
                info.profile = new Profile();
                info.profile.account_id = String.valueOf(beans.get(position).accountId);
                //info.avatarUrl = String.valueOf(beans.get(position).avatarfull);
                //info.name = String.valueOf(beans.get(position).personaName);
                info.follow = true;
                mPresenter.bindPlayer(info);
                dialog.dismiss();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

}
