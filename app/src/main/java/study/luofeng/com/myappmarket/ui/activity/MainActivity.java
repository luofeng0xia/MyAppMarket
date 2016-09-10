package study.luofeng.com.myappmarket.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import study.luofeng.com.myappmarket.R;
import study.luofeng.com.myappmarket.adapter.MyPagerAdapter;
import study.luofeng.com.myappmarket.adapter.MyRecyclerAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //左箭头的图标，默认可以回到上一个activity，一定会finish自身
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //DrawerLayout
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        //抽屉和toolbar上的左箭头关联，原先返回父activity事件被覆盖
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0);
        //可以把左箭头变成三条杠，开关抽屉时有动画效果
        toggle.syncState();
        //TabLayout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab);
        //ViewPager
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        //tabLayout和viewPager建立联系，后面boolean参数暂不清楚
        tabLayout.setupWithViewPager(pager,false);
    }
}
