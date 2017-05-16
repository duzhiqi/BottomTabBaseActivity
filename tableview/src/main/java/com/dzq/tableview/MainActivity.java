package com.dzq.tableview;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TableView tableView = (TableView) findViewById(R.id.tableView);

        List<TableView.TableItemView> views = new ArrayList<>();


        views.add(new TableView.TableItemView(this, R.mipmap.ic_launcher, R.mipmap.ic_launcher, Color.RED,Color.DKGRAY, "首页"));
        views.add(new TableView.TableItemView(this, R.mipmap.ic_launcher, R.mipmap.ic_launcher, Color.RED,Color.DKGRAY, "分类"));
        views.add(new TableView.TableItemView(this, R.mipmap.ic_launcher, R.mipmap.ic_launcher, Color.RED,Color.DKGRAY, "关注"));
        views.add(new TableView.TableItemView(this, R.mipmap.ic_launcher, R.mipmap.ic_launcher, Color.RED,Color.DKGRAY, "我的"));
        ImageView iv = new ImageView(this);
        iv.setImageResource(R.mipmap.ic_launcher);
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        iv.setLayoutParams(new ViewGroup.LayoutParams(300, 400));
        tableView.setTableItemViews(views, iv);
    }
}
