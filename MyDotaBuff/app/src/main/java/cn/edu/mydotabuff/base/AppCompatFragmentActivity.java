package cn.edu.mydotabuff.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.util.Utils;

/**
 * @author Yrom
 */
public class AppCompatFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.swipe_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.actionbar_bg);
        if (!Utils.hasSmartBar()) {
            tintManager.setNavigationBarTintEnabled(true);
            tintManager.setNavigationBarTintResource(R.color.statusbar_bg);
        }
    }

}
