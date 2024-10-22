package com.texeljoy.ht_effect.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.shizhefei.view.indicator.FragmentListPageAdapter;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.viewpager.SViewPager;
import com.texeljoy.ht_effect.HTPanelLayout;
import com.texeljoy.ht_effect.R;
import com.texeljoy.ht_effect.base.HtBaseFragment;
import com.texeljoy.ht_effect.model.HTEventAction;
import com.texeljoy.ht_effect.model.HTViewState;
import com.texeljoy.ht_effect.model.HtState;
import com.texeljoy.ht_effect.utils.HtSelectedPosition;
import com.texeljoy.ht_effect.view.HtBarView;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能——美颜
 */
public class HtBeautyFragment extends HtBaseFragment {


  private SViewPager htPager;
  private ScrollIndicatorView topIndicatorView;
  private Button alternateIndicatorView;
  private IndicatorViewPager indicatorViewPager;
  private IndicatorViewPager.IndicatorFragmentPagerAdapter fragmentPagerAdapter;
  private View container;
  private View line;
  private RelativeLayout bottomLayout;
  private AppCompatImageView returnIv;
  private String which;
  private HtBarView barView;

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
    alternateIndicatorView = view.findViewById(R.id.alternate_indicator_view);
    container = view.findViewById(R.id.container);
    line = view.findViewById(R.id.line);
    barView = view.findViewById(R.id.ht_bar);
    bottomLayout = view.findViewById(R.id.rl_bottom);
    returnIv = view.findViewById(R.id.return_iv);
    HTPanelLayout = new HTPanelLayout(getContext());

    RxBus.get().post(HTEventAction.ACTION_SYNC_PROGRESS, "");


    Bundle bundle = this.getArguments();//得到从Activity传来的数据
    if (bundle != null) {
      which = bundle.getString("switch");
    }

    //添加标题
    htTabs.clear();
    htTabs.add(requireContext().getString(R.string.beauty_skin));
    htTabs.add(requireContext().getString(R.string.beauty_shape));
    // htTabs.add(requireContext().getString(R.string.beauty_hair));
    // htTabs.add(requireContext().getString(R.string.beauty_makeup));
    // htTabs.add(requireContext().getString(R.string.beauty_makeup_style));
    // htTabs.add(requireContext().getString(R.string.beauty_body));
    // htTabs.add(requireContext().getString(R.string.beauty_style));
    // htTabs.add("口红");

    // topIndicatorView.setSplitMethod(FixedIndicatorView.SPLITMETHOD_WEIGHT);
    // topIndicatorView.setSplitMethod(FixedIndicatorView.SPLITMETHOD_WRAP);
    topIndicatorView.setHorizontalScrollBarEnabled(true);
    // topIndicatorView.set
    indicatorViewPager = new IndicatorViewPager(topIndicatorView, htPager);
    returnIv.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {
        RxBus.get().post(HTEventAction.ACTION_CHANGE_PANEL, HTViewState.MODE);

      }
    });
    barView.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {

      }
    });
    alternateIndicatorView.setOnClickListener  (new OnClickListener() {
      @Override public void onClick(View v) {
        RxBus.get().post(HTEventAction.ACTION_STYLE_SELECTED,"");
      }
    });

    htPager.setCanScroll(false);
    htPager.setOffscreenPageLimit(2);

    // htPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
    //   @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    //     HtSelectedPosition.POSITION_BEAUTY = position;
    //     // if(!HtState.currentMakeUpStyle.getName().isEmpty() ){
    //     //   RxBus.get().post(HTEventAction.ACTION_MAKEUP_STYLE_SELECTED,"");
    //     // }
    //
    //   }
    //
    //   @Override public void onPageSelected(int position) {
    //
    //
    //
    //   }
    //
    //   @Override public void onPageScrollStateChanged(int state) {
    //
    //   }
    // });

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
            // case 2:
            //   return new HtHairFragment();
            // case 3:
            //   return new HtMakeUpFragment();
            // case 4:
            //   return new HtMakeUpStyleFragment();
            // case 5:
            //   return new HtBodyFragment();
            default:
              return new HtBeautySkinFragment();
          }
      }
    };
    indicatorViewPager.setAdapter(fragmentPagerAdapter);

    htPager.setCurrentItem(HtSelectedPosition.POSITION_BEAUTY,false);
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
