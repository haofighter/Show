package com.myself.show.show.Ui;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myself.show.show.R;
import com.myself.show.show.View.MyViewGroup;
import com.myself.show.show.listener.OnFragmentInteractionListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderMuneActivity extends FragmentActivity implements OnFragmentInteractionListener {



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyViewGroup group = new MyViewGroup(this);
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(R.mipmap.add_imge);
        group.addView(imageView);
        group.setBackgroundColor(Color.GREEN);

        setContentView(R.layout.activity_order_mune);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
