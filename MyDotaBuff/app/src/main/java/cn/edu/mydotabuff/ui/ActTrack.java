package cn.edu.mydotabuff.ui;

import android.view.MenuItem;

import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.base.BaseActivity;

/**
 * Created by nevermore on 2015/5/18.
 * //                              _oo0oo_
 * //                             o8888888o
 * //                             88" . "88
 * //                             (| -_- |)
 * //                             0\  =  /0
 * //                           ___/`___'\___
 * //                         .' \\|     |// '.
 * //                        / \\|||  :  |||// \
 * //                       / _||||| -:- |||||_ \
 * //                      |   | \\\  _  /// |   |
 * //                      | \_|  ''\___/''  |_/ |
 * //                      \  .-\__  '_'  __/-.  /
 * //                    ___'. .'  /--.--\  '. .'___
 * //                  ."" '<  .___\_<|>_/___. '>' "".
 * //               | | :  `_ \`.;` \ _ / `;.`/ - ` : | |
 * //               \ \  `_.   \_ ___\ /___ _/   ._`  / /
 * //            ====`-.____` .__ \_______/ __. -` ___.`====
 * //                             `=-----='
 * //         ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * //                    佛祖保佑           永无BUG
 * //
 * //         ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
public class ActTrack extends BaseActivity{
    @Override
    protected void initViewAndData() {
        setContentView(R.layout.act_track);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("赛况直播");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initEvent() {

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
