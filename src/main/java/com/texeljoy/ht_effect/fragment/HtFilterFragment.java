package com.texeljoy.ht_effect.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.shizhefei.view.indicator.FragmentListPageAdapter;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.LayoutBar;
import com.shizhefei.view.viewpager.SViewPager;
import com.texeljoy.ht_effect.HTPanelLayout;
import com.texeljoy.ht_effect.R;
import com.texeljoy.ht_effect.base.HtBaseFragment;
import com.texeljoy.ht_effect.model.HTEventAction;
import com.texeljoy.ht_effect.model.HtState;
import com.texeljoy.ht_effect.utils.HtSelectedPosition;
import com.texeljoy.ht_effect.view.HtBarView;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能——滤镜
 */
public class HtFilterFragment extends HtBaseFragment {


  private SViewPager htPager;
  private ScrollIndicatorView topIndicatorView;
  private Button alternateIndicatorView;
  private IndicatorViewPager indicatorViewPager;
  private IndicatorViewPager.IndicatorFragmentPagerAdapter fragmentPagerAdapter;
  private View container;
  private View line;
  private View divide;
  private RelativeLayout bottomLayout;
  private AppCompatImageView returnIv;
  private ImageView cleanIv;
  private String which;
  private HtBarView barView;

  private final List<String> htTabs = new ArrayList<>();
  private HTPanelLayout HTPanelLayout;


  @Override
  protected int getLayoutId()  {
    return R.layout.layout_beauty_skin;
  }

  @Override
  protected void initView(View view, Bundle savedInstanceState) {

    htPager = view.findViewById(R.id.ht_pager);
    topIndicatorView = view.findViewById(R.id.indicatorView);
    alternateIndicatorView = view.findViewById(R.id.alternate_indicator_view);
    container = view.findViewById(R.id.container);
    line = view.findViewById(R.id.line);
    divide = view.findViewById(R.id.divide);
    barView = view.findViewById(R.id.ht_bar);
    bottomLayout = view.findViewById(R.id.rl_bottom);
    returnIv = view.findViewById(R.id.return_iv);
    cleanIv = view.findViewById(R.id.iv_clean);
    HTPanelLayout = new HTPanelLayout(getContext());

    Bundle bundle = this.getArguments();//得到从Activity传来的数据
    if (bundle != null) {
      which = bundle.getString("switch");
    }

    //添加标题
    htTabs.clear();
    htTabs.add(requireContext().getString(R.string.filter_beauty));
    htTabs.add(requireContext().getString(R.string.filter_effect));
    htTabs.add(requireContext().getString(R.string.filter_funny));

    cleanIv.setVisibility(View.GONE);
    // line.setVisibility(View.GONE);

    topIndicatorView.setSplitAuto(false);
    indicatorViewPager = new IndicatorViewPager(topIndicatorView, htPager);
    indicatorViewPager.setIndicatorScrollBar(new LayoutBar(getContext(),
        R.layout.layout_greenscreen_indicator_tab));
    // returnIv.setOnClickListener(new OnClickListener() {
    //   @Override public void onClick(View v) {
    //     RxBus.get().post(HTEventAction.ACTION_CHANGE_PANEL, HTViewState.MODE);
    //
    //   }
    // });
    //
    barView.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {

      }
    });
    // alternateIndicatorView.setOnClickListener(new OnClickListener() {
    //   @Override public void onClick(View v) {
    //     RxBus.get().post(HTEventAction.ACTION_STYLE_SELECTED,"");
    //   }
    // });


    htPager.setCanScroll(false);
    htPager.setOffscreenPageLimit(2);

    htPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        HtSelectedPosition.POSITION_FILTER = position;
        //if (position == 0){
        //HtState.currentSecondViewState = HTViewState.FILTER_STYLE;
        //}
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
              return new HtEffectFilterFragment();
            case 2:
              return new HtFunnyFilterFragment();
            default:
              return new HtBeautyFilterFragment();
          }
      }
    };
    indicatorViewPager.setAdapter(fragmentPagerAdapter);
    if("filter".equals(which)){
      htPager.setCurrentItem(0,false);
    }
    // if(HtState.currentStyle != HtStyle.YUAN_TU){
    //   htPager.setCurrentItem(3,false);
    //   alternateIndicatorView.setVisibility(View.VISIBLE);
    //   //topIndicatorView.setItemClickable(false);
    //
    // }
    htPager.setCurrentItem(HtSelectedPosition.POSITION_FILTER,false);
    changeTheme("");
  }
  /**
   * 更改指示器的可选状态
   */
  @Subscribe(thread = EventThread.MAIN_THREAD,
             tags = { @Tag(HTEventAction.ACTION_CHANGE_ENABLE) })
  public void changeIndicatorViewEnable(@Nullable Object o){
    if(!HtState.currentStyle.getName().isEmpty()){
      //当选中风格推荐效果后，加一层透明遮罩，此时原先的指示器不再能获取焦点，透明遮罩被点击后弹出弹窗
      alternateIndicatorView.setVisibility(View.VISIBLE);
    }else{
      alternateIndicatorView.setVisibility(View.GONE);
    }

  }
  /**
   * 更换主题
   */
  @Subscribe(thread = EventThread.MAIN_THREAD,
             tags = { @Tag(HTEventAction.ACTION_CHANGE_THEME) })
  public void changeTheme(@Nullable Object o) {
    Log.e("切换主题:", HtState.isDark ? "黑色" : "白色");

    if (HtState.isDark) {
      // topIndicatorView.setBackground(ContextCompat.getDrawable(getContext(),
      //     R.color.dark_background));
      // bottomLayout.setBackground(ContextCompat.getDrawable(getContext(),
      //     R.color.dark_background));

      line.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.horizonal_line));
      // returnIv.setSelected(false);
      container.setBackground(ContextCompat.getDrawable(getContext(),
          R.color.dark_background));

    } else {
      // topIndicatorView.setBackground(ContextCompat.getDrawable(getContext(),
      //     R.color.light_background));
      // bottomLayout.setBackground(ContextCompat.getDrawable(getContext(),
      //     R.color.light_background));
      container.setBackground(ContextCompat.getDrawable(getContext(),
          R.color.light_background));

      line.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.black_line));
      // returnIv.setSelected(true);

    }
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
  }
}
