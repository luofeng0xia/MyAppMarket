package study.luofeng.com.myappmarket.ui.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import study.luofeng.com.myappmarket.R;
import study.luofeng.com.myappmarket.utils.ToolBarHelper;
import study.luofeng.com.myappmarket.utils.UiUtils;

/**
 * MyBaseActivity
 * Created by weixi on 2016/9/12.
 */
public abstract class MyBaseActivity extends AppCompatActivity {

    protected Toolbar toolbar;

    @Override
    public void setContentView(int layoutResID) {
        setContentView(UiUtils.inflate(layoutResID));
    }

    @Override
    public void setContentView(View view) {
        ToolBarHelper toolbarHelper = new ToolBarHelper(this,view);
        super.setContentView(toolbarHelper.getContainer());
        toolbar=toolbarHelper.getToolbar();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    protected Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
