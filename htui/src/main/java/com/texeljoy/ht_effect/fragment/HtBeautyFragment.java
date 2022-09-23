package com.texeljoy.ht_effect.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.FragmentListPageAdapter;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.viewpager.SViewPager;
import com.texeljoy.ht_effect.HTPanelLayout;
import com.texeljoy.ht_effect.R;
import com.texeljoy.ht_effect.base.HtBaseFragment;
import com.texeljoy.ht_effect.model.HTEventAction;
import com.texeljoy.ht_effect.model.HTViewState;
import com.texeljoy.ht_effect.model.HtState;
import com.texeljoy.ht_effect.model.HtStyle;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能——美颜
 */
public class HtBeautyFragment extends HtBaseFragment {


  private SViewPager htPager;
  private FixedIndicatorView topIndicatorView;
  private IndicatorViewPager indicatorViewPager;
  private IndicatorViewPager.IndicatorFragmentPagerAdapter fragmentPagerAdapter;
  private View container;
  private View line;
  private RelativeLayout bottomLayout;
  private AppCompatImageView returnIv;
  private String which;

  private final List<String> htTabs = new ArrayList<>();
  private HTPanelLayout HTPanelLayout;


  @Override
  protected int getLayoutId()  {
    return R.layout.layout_beauty;
  }

  @Override
  protected void initView(View view, Bundle savedInstanceState) {

    htPager = view.findViewById(R.id.ht_pager);
    topIndicatorView = view.findViewById(R.id.top_indicator_view);
    container = view.findViewById(R.id.container);
    line = view.findViewById(R.id.line);
    bottomLayout = view.findViewById(R.id.rl_bottom);
    returnIv = view.findViewById(R.id.return_iv);
    HTPanelLayout = new HTPanelLayout(getContext());

    Bundle bundle = this.getArguments();//得到从Activity传来的数据
    if (bundle != null) {
      which = bundle.getString("switch");
    }

    //添加标题
    htTabs.clear();
    htTabs.add(requireContext().getString(R.string.beauty_skin));
    htTabs.add(requireContext().getString(R.string.beauty_shape));
    htTabs.add(requireContext().getString(R.string.filter));
    htTabs.add(requireContext().getString(R.string.beauty_style));

    topIndicatorView.setSplitMethod(FixedIndicatorView.SPLITMETHOD_WEIGHT);
    indicatorViewPager = new IndicatorViewPager(topIndicatorView, htPager);
    returnIv.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {
        RxBus.get().post(HTEventAction.ACTION_CHANGE_PANEL, HTViewState.MODE);

      }
    });

    htPager.setCanScroll(false);
    htPager.setOffscreenPageLimit(2);

    htPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

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
        if (!HtState.isDark) {
          convertView = LayoutInflater.from(getContext())
              .inflate(R.layout.item_ht_tab_white, container, false);
        } else {
          convertView = LayoutInflater.from(getContext())
              .inflate(R.layout.item_ht_tab_dark, container, false);
        }
        ((AppCompatTextView) convertView).setText(htTabs.get(position));
        ((AppCompatTextView) convertView).setTextSize(15);
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
            return new HtFaceTrimFragment();
          case 2:
            return new HtFilterFragment();
          case 3:
            return new HtStyleFragment();
          default:
            return new HtBeautySkinFragment();
        }
      }
    };
    indicatorViewPager.setAdapter(fragmentPagerAdapter);
    if("beauty_filter".equals(which)){
      htPager.setCurrentItem(2,false);
    }
    if(HtState.currentStyle != HtStyle.YUAN_TU){
      htPager.setCurrentItem(3,false);
      topIndicatorView.setItemClickable(false);
      RxBus.get().post(HTEventAction.ACTION_STYLE_SELECTED,"请先取消“风格推荐”效果");
    }else{
      topIndicatorView.setItemClickable(true);
    }
    changeTheme("");
  }
  /**
   * 更改指示器的可选状态
   */
  @Subscribe(thread = EventThread.MAIN_THREAD,
             tags = { @Tag(HTEventAction.ACTION_CHANGE_ENABLE) })
  public void changeIndicatorViewEnable(@Nullable Object o){
    //topIndicatorView.setEnabled(HtState.currentStyle.equals(HtStyle.YUAN_TU));
    topIndicatorView.setItemClickable(HtState.currentStyle.equals(HtStyle.YUAN_TU));
  }
  /**
   * 更换主题
   */
  @Subscribe(thread = EventThread.MAIN_THREAD,
             tags = { @Tag(HTEventAction.ACTION_CHANGE_THEME) })
  public void changeTheme(@Nullable Object o) {
    Log.e("切换主题:", HtState.isDark ? "黑色" : "白色");

    if (HtState.isDark) {
      topIndicatorView.setBackground(ContextCompat.getDrawable(getContext(),
          R.color.dark_background));
      bottomLayout.setBackground(ContextCompat.getDrawable(getContext(),
          R.color.dark_background));

      line.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.gray_line));
      returnIv.setSelected(false);

    } else {
      topIndicatorView.setBackground(ContextCompat.getDrawable(getContext(),
          R.color.light_background));
      bottomLayout.setBackground(ContextCompat.getDrawable(getContext(),
          R.color.light_background));

      line.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.black_line));
      returnIv.setSelected(true);

    }
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
  }
}
