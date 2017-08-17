package com.myself.show.show.Ui.home;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.myself.show.show.R;
import com.myself.show.show.base.App;
import com.myself.show.show.base.ThemeBaseActivity;
import com.myself.show.show.customview.richeditor.RichEditor;
import com.myself.show.show.net.responceBean.NoteDate;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/2.
 */

public class LookNoteActivity extends ThemeBaseActivity {

    @BindView(R.id.vp_note)
    ViewPager vpNote;
    private List<NoteDate> noteDateList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_note);
        ButterKnife.bind(this);
        noteDateList = App.getInstance().getDaoSession().getNoteDateDao().loadAll();

        vpNote.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return noteDateList == null ? 0 : noteDateList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = LayoutInflater.from(LookNoteActivity.this).inflate(R.layout.look_note_show, null);
                ((TextView) view.findViewById(R.id.note_title)).setText(noteDateList.get(position).getTitle());
                ((RichEditor) view.findViewById(R.id.note_content)).setHtml(noteDateList.get(position).getNoteHtml());
//                ((WebView) view.findViewById(R.id.note_content)).loadDataWithBaseURL("about:blank",noteDateList.get(position).getNoteHtml(), "text/html", "utf-8",null);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
        if(noteDateList!=null&&noteDateList.size()>0){
            vpNote.setCurrentItem(getIntent().getIntExtra("select",0));
        }

    }

    @Override
    public int getContentView() {
        return 0;
    }
}
