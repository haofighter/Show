package com.myself.show.show.Ui.viewpage.fagment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myself.show.show.R;
import com.myself.show.show.Ui.home.adapter.NoteListAdapter;
import com.myself.show.show.base.App;
import com.myself.show.show.base.BackCall;
import com.myself.show.show.base.BaseFragment;
import com.myself.show.show.listener.OnFragmentInteractionListener;
import com.myself.show.show.net.responceBean.NoteDate;
import com.myself.show.show.sql.NoteDateDao;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class VirtivalViewPageFragment extends BaseFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.note_date_list)
    RecyclerView noteDateList;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Unbinder unbinder;
    private NoteListAdapter noteListAdapter;

    public VirtivalViewPageFragment() {
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

    public static VirtivalViewPageFragment newInstance(String param1, String param2) {
        VirtivalViewPageFragment fragment = new VirtivalViewPageFragment();
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
        unbinder = ButterKnife.bind(view);
        noteListAdapter = new NoteListAdapter(getActivity(), new BackCall() {
            @Override
            public void backCall(int tag, Object... obj) {

            }
        });
        //设置布局管理器
        noteDateList.setLayoutManager(new LinearLayoutManager(getActivity()));
        //设置增加或删除条目的动画
        noteDateList.setItemAnimator(new DefaultItemAnimator());
        noteDateList.setAdapter(noteListAdapter);
        return view;
    }


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

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        noteListAdapter.setDate(loadLocalDate());
        noteListAdapter.notifyDataSetChanged();;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public List<NoteDate> loadLocalDate() {
        NoteDateDao noteDateDao = App.getInstance().getDaoSession().getNoteDateDao();
        List<NoteDate> noteDates = noteDateDao.loadAll();
        return noteDates;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
