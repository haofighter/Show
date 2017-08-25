package com.myself.show.show.Ui.home.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import com.myself.show.show.R;
import com.myself.show.show.Ui.home.AddNoteActivity;
import com.myself.show.show.Ui.home.LookNoteActivity;
import com.myself.show.show.Ui.home.adapter.NoteListAdapter;
import com.myself.show.show.View.Twink.RefreshListenerAdapter;
import com.myself.show.show.View.Twink.TwinklingRefreshLayout;
import com.myself.show.show.base.App;
import com.myself.show.show.base.BackCall;
import com.myself.show.show.base.BaseFragment;
import com.myself.show.show.customview.RecycleViewDivider;
import com.myself.show.show.listener.OnFragmentInteractionListener;
import com.myself.show.show.net.responceBean.NoteDate;
import com.myself.show.show.sql.NoteDateDao;
import com.myself.show.show.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class HomeFragment extends BaseFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.note_date_list)
    RecyclerView noteDateList;
    @BindView(R.id.refresh)
    TwinklingRefreshLayout refresh;
    @BindView(R.id.home_layout)
    FrameLayout homeLayout;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Unbinder unbinder;
    private NoteListAdapter noteListAdapter;

    public HomeFragment() {
    }

    /**
     * 用于显示的时候调用  主要是配合ViewPager初始化界面
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        onHiddenChanged(isVisibleToUser);
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        refresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                refresh.finishRefreshing();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                refresh.finishLoadmore();
            }
        });

        noteListAdapter = new NoteListAdapter(getActivity(), new BackCall() {
            @Override
            public void backCall(int tag, Object... obj) {//跳转编辑界面
                switch (tag) {
                    case R.id.write:
                        Intent intent = new Intent(getActivity(), AddNoteActivity.class);
                        intent.putExtra("dateID", (Long) obj[0]);
                        startActivity(intent);
                        break;
                    case R.id.look:
                        Intent intent1 = new Intent(getActivity(), LookNoteActivity.class);
                        intent1.putExtra("select", (int) obj[0]);
                        startActivity(intent1);
                        break;
                    case R.id.item_content://条目长按触发
                        break;
                }
            }
        });
        //设置布局管理器
        noteDateList.setLayoutManager(new LinearLayoutManager(getActivity()));
        //设置增加或删除条目的动画
        noteDateList.setItemAnimator(new DefaultItemAnimator());
        noteDateList.addItemDecoration(new RecycleViewDivider(getActivity(), DividerItemDecoration.HORIZONTAL, 1, ContextCompat.getColor(getActivity(), R.color.gray_22)));
        noteDateList.setAdapter(noteListAdapter);

        //可移动条目的Recycleview设置
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(mCallback);
        itemTouchHelper.attachToRecyclerView(noteDateList);
        return view;
    }


//    // 弹出框
//    private void iniPopupWindow() {
//        LayoutInflater inflater = (LayoutInflater) getActivity()
//                .getSystemService(LAYOUT_INFLATER_SERVICE);
//        //POP的布局
//        View layout = inflater.inflate(R.layout.item_click_show, null);
//
//        PopupWindow myPopWindow = new PopupWindow(layout);
//        myPopWindow.setFocusable(true); // 加上这个popupwindow中的ListView才可以接收点击事件
//        // 控制popupwindow 的宽度和高度
//        myPopWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
//        myPopWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//
//        // 控制popupwindow 点击屏幕其他地方消失
//        myPopWindow.setBackgroundDrawable(this.getResources().getDrawable(
//                R.color.traslant)); // 设置背景图片,不能在布局中设置，要通过代码来设置
//        myPopWindow.setOutsideTouchable(true); // 触摸popupwindow 外部,popupwindow消失,这个要求你的 popupwindow要有背景图片才可以成功
//        myPopWindow.showAtLocation(homeLayout, Gravity.NO_GRAVITY, (int) touchX, (int) touchY);
//    }


    float touchX = 100;
    float touchY = 100;

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        if (noteListAdapter != null) {
//            noteListAdapter.setDate(loadLocalDate());
//            noteListAdapter.notifyDataSetChanged();
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (noteListAdapter != null) {
            noteListAdapter.setDate(loadLocalDate());
            noteListAdapter.notifyDataSetChanged();
            refresh.setEnableLoadmore(!(noteListAdapter.getItemCount() < 5));
        }
    }

    public List<NoteDate> loadLocalDate() {
        NoteDateDao noteDateDao = App.getInstance().getDaoSession().getNoteDateDao();
        List<NoteDate> noteDates = noteDateDao.loadAll();
        Log.i("获取到的笔记数", noteDates.size() + "");
        if (noteDates == null) {
            noteDates = new ArrayList<>();
        }
        return noteDates;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //RecycleView的拖拽功能回调
    ItemTouchHelper.Callback mCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        /**
         * @param recyclerView
         * @param viewHolder 拖动的ViewHolder
         * @param target 目标位置的ViewHolder
         * @return
         */
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();//得到拖动ViewHolder的position
            int toPosition = target.getAdapterPosition();//得到目标ViewHolder的position
            if (fromPosition < toPosition) {
                //分别把中间所有的item的位置重新交换
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(noteListAdapter.getDate(), i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(noteListAdapter.getDate(), i, i - 1);
                }
            }
            noteListAdapter.notifyItemMoved(fromPosition, toPosition);
            //返回true表示执行拖动
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            noteListAdapter.getDate().remove(position);
            noteListAdapter.notifyItemRemoved(position);
        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                //左右滑动时改变Item的透明度
                final float alpha = 1 - Math.abs(dX) / (float) viewHolder.itemView.getWidth();
                viewHolder.itemView.setAlpha(alpha);
                viewHolder.itemView.setTranslationX(dX);
            }
        }

        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            super.onSelectedChanged(viewHolder, actionState);
            //当选中Item时候会调用该方法，重写此方法可以实现选中时候的一些动画逻辑
            if (viewHolder == null || viewHolder.itemView == null) {
            } else {
                viewHolder.itemView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.gray_11));
            }
            Log.v("zxy", "onSelectedChanged");
        }

        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            //当动画已经结束的时候调用该方法，重写此方法可以实现恢复Item的初始状态
            if (viewHolder == null || viewHolder.itemView == null) {
            } else {
                viewHolder.itemView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.traslant));
            }
            Log.v("zxy", "clearView");
        }
    };


}
