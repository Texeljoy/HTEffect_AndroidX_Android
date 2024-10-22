package com.texeljoy.ht_effect.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.shizhefei.view.indicator.FragmentListPageAdapter;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.LayoutBar;
import com.shizhefei.view.viewpager.SViewPager;
import com.texeljoy.ht_effect.R;
import com.texeljoy.ht_effect.base.HtBaseFragment;
import com.texeljoy.ht_effect.model.HTEventAction;
import com.texeljoy.ht_effect.model.HtState;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能——人像抠图——绿幕抠图
 */

public class HtGreenScreenFragment extends HtBaseFragment {

  private SViewPager htPager;
  private ScrollIndicatorView indicatorView;
  private IndicatorViewPager indicatorViewPager;
  private IndicatorViewPager.IndicatorFragmentPagerAdapter fragmentPagerAdapter;
  private View container;
  private View line;

  private final List<String> htTabs = new ArrayList<>();

  @Override protected int getLayoutId() {
    return R.layout.layout_greenscreen;
  }

  @Override protected void initView(View view, Bundle savedInstanceState) {

    htPager = view.findViewById(R.id.ht_pager);
    indicatorView = view.findViewById(R.id.indicatorView);
    container = view.findViewById(R.id.container);
    line = view.findViewById(R.id.line);


    //添加标题
    htTabs.clear();
    htTabs.add(requireContext().getString(R.string.edit));
    htTabs.add(requireContext().getString(R.string.background));


    indicatorView.setSplitAuto(false);
    indicatorViewPager = new IndicatorViewPager(indicatorView, htPager);
    indicatorViewPager.setIndicatorScrollBar(new LayoutBar(getContext(),
        R.layout.layout_greenscreen_indicator_tab));

    htPager.setCanScroll(false);
    htPager.setOffscreenPageLimit(0);
    htPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // if(position == 1){
        //   HtSelectedPosition.POSITION_GREEN_SCREEN_EDIT = 3;
        // }

      }

      @Override public void onPageSelected(int position) {

      }

      @Override public void onPageScrollStateChanged(int state) {

      }
    });
    fragmentPagerAdapter = new IndicatorViewPager.IndicatorFragmentPagerAdapter(getChildFragmentManager()) {
      @Override public int getCount() {
        return htTabs.size();
      }

      @Override public View getViewForTab(int position,
                                          View convertView,
                                          ViewGroup container) {
        convertView = LayoutInflater.from(getContext())
            .inflate(R.layout.item_ht_tab_dark, container, false);
        ((AppCompatTextView) convertView).setText(htTabs.get(position));
        return convertView;
      }

      @Override
      public int getItemPosition(Object object) {
        return FragmentListPageAdapter.POSITION_NONE;
      }

      @Override public Fragment getFragmentForPage(int position) {
        Log.e("position:", position + "");

        switch (position) {
           case 1:
             return new HtPorpraitGreenScreenFragment();
          default:
            return new HtGreenScreenEditFragment();
        }
      }
    };

    indicatorViewPager.setAdapter(fragmentPagerAdapter);
    // container.setBackground(ContextCompat.getDrawable(getContext(),
    //     R.color.dark_background));
    line.setVisibility(View.GONE);

    // line.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.gray_line));
    //changeTheme("");

  }
  /**
   * 更换主题
   */
  @Subscribe(thread = EventThread.MAIN_THREAD,
             tags = { @Tag(HTEventAction.ACTION_CHANGE_THEME) })
  public void changeTheme(@Nullable Object o) {
    Log.e("切换主题:", HtState.isDark ? "黑色" : "白色");

    if (HtState.isDark) {
      container.setBackground(ContextCompat.getDrawable(getContext(),
          R.color.dark_background));

      line.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.gray_line));

    } else {
      container.setBackground(ContextCompat.getDrawable(getContext(),
          R.color.light_background));

      line.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.black_line));

    }
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
  }
}