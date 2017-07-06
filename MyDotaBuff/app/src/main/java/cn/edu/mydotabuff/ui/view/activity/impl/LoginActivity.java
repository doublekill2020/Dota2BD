package cn.edu.mydotabuff.ui.view.activity.impl;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.base.BaseActivity;
import cn.edu.mydotabuff.common.CommAdapter;
import cn.edu.mydotabuff.common.CommViewHolder;
import cn.edu.mydotabuff.model.PlayerInfo;
import cn.edu.mydotabuff.ui.MainActivity;
import cn.edu.mydotabuff.ui.presenter.ILoginPresenter;
import cn.edu.mydotabuff.ui.presenter.impl.LoginPresenterImpl;
import cn.edu.mydotabuff.ui.view.activity.ILoginView;

public class LoginActivity extends BaseActivity<ILoginPresenter> implements ILoginView {

    @BindView(R.id.player_search_spinner)
    Spinner mSpinner;
    @BindView(R.id.et_search_word)
    EditText mKeywordEdittext;
    @BindView(R.id.btn_search)
    Button mSearchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new LoginPresenterImpl(this);
        initView();
    }

    public void initView() {
        ButterKnife.setDebug(true);
        // FIXME: 2017/7/6 还要排除其他界面跳转过了的情况
        if (mPresenter.hasFocusPlayer()) {
            toOtherActivity(MainActivity.class);
        } else {
            setContentView(R.layout.act_login);
            mKeywordEdittext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        onSearchClicked();
                        return true;
                    }
                    return false;
                }
            });
            mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position == 0) {
                        mKeywordEdittext.setInputType(InputType.TYPE_CLASS_NUMBER);
                        mKeywordEdittext.setHint(R.string.please_input_steamId);
                    } else {
                        mKeywordEdittext.setInputType(InputType.TYPE_CLASS_TEXT);
                        mKeywordEdittext.setHint(R.string.please_input_steamId_or_name);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

    }


    @OnClick(R.id.btn_search)
    public void onSearchClicked() {
        if (TextUtils.isEmpty(mKeywordEdittext.getText())) {
            showToast(getString(R.string.search_player_content_empty_hint));
        } else {
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(mKeywordEdittext.getWindowToken()
                            , InputMethodManager.HIDE_NOT_ALWAYS);
            mPresenter.searchPlayer(mKeywordEdittext.getText().toString(),
                    mSpinner.getSelectedItemPosition() == 0);
        }
    }


    @Override
    public void showResult(List<PlayerInfo> playerInfos) {
        if (playerInfos.size() > 0) {
            showResultDialog(playerInfos);
        } else {
            showToast("没有匹配结果");
        }
    }

    private void showResultDialog(final List<PlayerInfo> beans) {
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
        list.setAdapter(new CommAdapter<PlayerInfo>(this, beans,
                R.layout.dlg_user_list_item) {
            @Override
            public void convert(CommViewHolder helper, PlayerInfo item) {
                helper.setText(R.id.name, item.profile.personaname);
                helper.setText(R.id.id, item.profile.account_id);
                helper.setImagUri(R.id.icon, item.profile.avatarfull);
            }
        });
        list.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                PlayerInfo info = beans.get(position);
                info.follow = true;
                mPresenter.bindPlayer(info);
                dialog.dismiss();
                toOtherActivity(MainActivity.class);
                finish();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

}
