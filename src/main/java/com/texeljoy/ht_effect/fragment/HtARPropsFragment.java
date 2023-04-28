package com.texeljoy.ht_effect.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.hwangjr.rxbus.RxBus;
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
import com.texeljoy.hteffect.HTEffect;
import com.texeljoy.hteffect.model.HTItemEnum;
import java.util.ArrayList;
import java.util.List;

/**
 * AR道具
 */
public class HtARPropsFragment extends HtBaseFragment {
  private SViewPager htPager;
  private ScrollIndicatorView indicatorView;
  private IndicatorViewPager indicatorViewPager;
  private IndicatorViewPager.IndicatorFragmentPagerAdapter fragmentPagerAdapter;
  private View container;
  private View line;
  private View divide;
  private ImageView ivClean;

  private final List<String> htTabs = new ArrayList<>();

  @Override protected int getLayoutId() {
    return R.layout.layout_beauty_skin;
  }

  @Override protected void initView(View view, Bundle savedInstanceState) {
    htPager = view.findViewById(R.id.ht_pager);
    indicatorView = view.findViewById(R.id.indicatorView);
    container = view.findViewById(R.id.container);
    line = view.findViewById(R.id.line);
    divide = view.findViewById(R.id.divide);
    ivClean = view.findViewById(R.id.iv_clean);

    //添加标题
    htTabs.clear();
    htTabs.add(requireContext().getString(R.string.sticker));
    htTabs.add(requireContext().getString(R.string.mask));
    htTabs.add(requireContext().getString(R.string.gift));
    htTabs.add(requireContext().getString(R.string.watermark));




    indicatorView.setSplitAuto(false);

    indicatorViewPager = new IndicatorViewPager(indicatorView, htPager);
    indicatorViewPager.setIndicatorScrollBar(new LayoutBar(getContext(),
        R.layout.layout_indicator_tab));
    htPager.setCanScroll(false);
    htPager.setOffscreenPageLimit(3);
    htPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        ivClean.setOnClickListener(new OnClickListener() {
          @Override public void onClick(View v) {
            if(position == 0){
              HTEffect.shareInstance().setARItem(HTItemEnum.HTItemSticker.getValue(), "");
              RxBus.get().post(HTEventAction.ACTION_SYNC_STICKER_ITEM_CHANGED, "");
            }else if(position == 1){
              HTEffect.shareInstance().setARItem(HTItemEnum.HTItemMask.getValue(), "");
              RxBus.get().post(HTEventAction.ACTION_SYNC_MASK_ITEM_CHANGED, "");
            }else if(position == 2){
              HTEffect.shareInstance().setARItem(HTItemEnum.HTItemGift.getValue(), "");
              RxBus.get().post(HTEventAction.ACTION_SYNC_GIFT_ITEM_CHANGED, "");
            }else{
              HTEffect.shareInstance().setARItem(HTItemEnum.HTItemWatermark.getValue(), "");
              RxBus.get().post(HTEventAction.ACTION_SYNC_WATERMARK_ITEM_CHANGED, "");
            }

          }
        });


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
              .inflate(R.layout.item_ht_sticker_tab, container, false);


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
            return new HtMaskFragment();
          case 2:
            return new HtGiftFragment();
          case 3:
            return new HtWatermarkFragment();
          default:
            return new HtStickerFragment();
        }
      }
    };
    indicatorViewPager.setAdapter(fragmentPagerAdapter);
    container.setBackground(ContextCompat.getDrawable(getContext(),
        R.color.dark_background));

    line.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.horizonal_line));
    divide.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.divide_line));
    // changeTheme("");

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

      line.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.divide_line));

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