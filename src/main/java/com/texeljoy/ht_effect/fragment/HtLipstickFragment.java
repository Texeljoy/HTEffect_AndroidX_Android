package com.texeljoy.ht_effect.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.texeljoy.ht_effect.R;
import com.texeljoy.ht_effect.adapter.HtLipstickItemViewBinder;
import com.texeljoy.ht_effect.base.HtBaseLazyFragment;
import com.texeljoy.ht_effect.model.HTEventAction;
import com.texeljoy.ht_effect.model.HTViewState;
import com.texeljoy.ht_effect.model.HtBlushConfig;
import com.texeljoy.ht_effect.model.HtEyebrowConfig;
import com.texeljoy.ht_effect.model.HtEyelashConfig;
import com.texeljoy.ht_effect.model.HtEyelineConfig;
import com.texeljoy.ht_effect.model.HtEyeshadowConfig;
import com.texeljoy.ht_effect.model.HtLipstickConfig;
import com.texeljoy.ht_effect.model.HtMakeup;
import com.texeljoy.ht_effect.model.HtPupilsConfig;
import com.texeljoy.ht_effect.model.HtState;
import com.texeljoy.ht_effect.utils.HtConfigCallBack;
import com.texeljoy.ht_effect.utils.HtConfigTools;
import com.texeljoy.ht_effect.utils.HtUICacheUtils;
import com.texeljoy.ht_effect.utils.MyItemDecoration;
import java.util.ArrayList;
import java.util.List;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * 滤镜
 */
public class HtLipstickFragment extends HtBaseLazyFragment {

  private View container;

  private RecyclerView rvMakeup;
  private AppCompatTextView tvTitle;
  // private AppCompatImageView ivReturn;
  private RelativeLayout rlTitle;
  private View line;

  private final List<HtMakeup> items = new ArrayList<>();


  private int makeType;

  @Override protected int getLayoutId() {
    return R.layout.fragment_lipstick;
  }

  private final MultiTypeAdapter listAdapter = new MultiTypeAdapter();

  @Override protected void initView(View view, Bundle savedInstanceState) {
    String type = "";
    Bundle bundle = this.getArguments();
    if(bundle != null){
      type = bundle.getString("switch");
    }
    tvTitle = findViewById(R.id.tv_title);
    switch (type){
      case "lipstick":
        HtState.currentThirdViewState = HTViewState.MAKEUP_LIPSTICK;
        makeType = 0;
        tvTitle.setText("口红");
        break;
      case "eyebrow":
        HtState.currentThirdViewState = HTViewState.MAKEUP_EYEBROW;
        makeType = 1;
        tvTitle.setText("眉毛");
        break;
      case "blush":
        HtState.currentThirdViewState = HTViewState.MAKEUP_BLUSH;
        makeType = 2;
        tvTitle.setText("腮红");
        break;
      case "eyeshadow":
        makeType = 3;
        tvTitle.setText("眼影");
        HtState.currentThirdViewState = HTViewState.MAKEUP_EYESHADOW;
        break;
      case "eyeline":
        makeType = 4;
        tvTitle.setText("眼线");
        HtState.currentThirdViewState = HTViewState.MAKEUP_EYELINE;
        break;
      case "eyelash":
        makeType = 5;
        tvTitle.setText("睫毛");
        HtState.currentThirdViewState = HTViewState.MAKEUP_EYELASH;
        break;
      case "pupils":
        makeType = 6;
        tvTitle.setText("美瞳");
        HtState.currentThirdViewState = HTViewState.MAKEUP_BEAUTYPUPILS;
        break;
      default:makeType = 7;

    }

    container = findViewById(R.id.container);
    rvMakeup = findViewById(R.id.rv_filter);
    rlTitle = findViewById(R.id.rl_title);
    // ivReturn = findViewById(R.id.return_iv);
    line = findViewById(R.id.line);

    // ivReturn.setVisibility(View.VISIBLE);
    tvTitle.setVisibility(View.VISIBLE);
    rlTitle.setVisibility(View.VISIBLE);
    line.setVisibility(View.VISIBLE);

    rvMakeup.addItemDecoration(new
        MyItemDecoration(5)
    );

    changeTheme(null);
    items.clear();
    HtMakeup NoMakeup = HtMakeup.NO_MAKEUP;
    NoMakeup.setType(makeType);
    items.add(NoMakeup);


    switch (makeType){
      case 0:
        HtLipstickConfig lipstickList = HtConfigTools.getInstance().getLipstickList();
        if (lipstickList == null) {
          HtConfigTools.getInstance().getLipsticksConfig(new HtConfigCallBack<List<HtMakeup>>() {
            @Override public void success(List<HtMakeup> list) {
              items.addAll(list);
              initRecyclerView();
            }

            @Override public void fail(Exception error) {
              error.printStackTrace();
              Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
          });
        } else {
          items.addAll(lipstickList.getMakeups());
          initRecyclerView();
        }
        break;
      case 1:

        HtEyebrowConfig eyebrowList = HtConfigTools.getInstance().getEyebrowList();
        if (eyebrowList == null) {
          HtConfigTools.getInstance().getEyebrowsConfig(new HtConfigCallBack<List<HtMakeup>>() {
            @Override public void success(List<HtMakeup> list) {
              items.addAll(list);
              initRecyclerView();
            }

            @Override public void fail(Exception error) {
              error.printStackTrace();
              Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
          });
        } else {
          items.addAll(eyebrowList.getMakeups());
          initRecyclerView();
        }
        break;
      case 2:
        HtBlushConfig blushList = HtConfigTools.getInstance().getBlushList();
        if (blushList == null) {
          HtConfigTools.getInstance().getBlushsConfig(new HtConfigCallBack<List<HtMakeup>>() {
            @Override public void success(List<HtMakeup> list) {
              items.addAll(list);
              initRecyclerView();
            }

            @Override public void fail(Exception error) {
              error.printStackTrace();
              Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
          });
        } else {
          items.addAll(blushList.getMakeups());
          initRecyclerView();
        }
        break;
      case 3:
        HtEyeshadowConfig shadowList = HtConfigTools.getInstance().getEyeshadowList();
        if (shadowList == null) {
          HtConfigTools.getInstance().getEyeshadowsConfig(new HtConfigCallBack<List<HtMakeup>>() {
            @Override public void success(List<HtMakeup> list) {
              items.addAll(list);
              initRecyclerView();
            }

            @Override public void fail(Exception error) {
              error.printStackTrace();
              Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
          });
        } else {
          items.addAll(shadowList.getMakeups());
          initRecyclerView();
        }
        break;
      case 4:
        HtEyelineConfig eyelineList = HtConfigTools.getInstance().getEyelineList();
        if (eyelineList == null) {
          HtConfigTools.getInstance().getEyelinesConfig(new HtConfigCallBack<List<HtMakeup>>() {
            @Override public void success(List<HtMakeup> list) {
              items.addAll(list);
              initRecyclerView();
            }

            @Override public void fail(Exception error) {
              error.printStackTrace();
              Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
          });
        } else {
          items.addAll(eyelineList.getMakeups());
          initRecyclerView();
        }
        break;
      case 5:
        HtEyelashConfig eyelashList = HtConfigTools.getInstance().getEyelashList();
        if (eyelashList == null) {
          HtConfigTools.getInstance().getEyelashsConfig(new HtConfigCallBack<List<HtMakeup>>() {
            @Override public void success(List<HtMakeup> list) {
              items.addAll(list);
              initRecyclerView();
            }

            @Override public void fail(Exception error) {
              error.printStackTrace();
              Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
          });
        } else {
          items.addAll(eyelashList.getMakeups());
          initRecyclerView();
        }
        break;
      case 6:
        HtPupilsConfig pupilsList = HtConfigTools.getInstance().getPupilsList();
        if (pupilsList == null) {
          HtConfigTools.getInstance().getPupilsConfig(new HtConfigCallBack<List<HtMakeup>>() {
            @Override public void success(List<HtMakeup> list) {
              items.addAll(list);
              initRecyclerView();
            }

            @Override public void fail(Exception error) {
              error.printStackTrace();
              Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
          });
        } else {
          items.addAll(pupilsList.getMakeups());
          initRecyclerView();
        }
        break;

    }


  }

  public void initRecyclerView() {
    listAdapter.register(HtMakeup.class, new HtLipstickItemViewBinder());
    listAdapter.setItems(items);
    rvMakeup.setAdapter(listAdapter);
    if(HtUICacheUtils.beautyLipstickPosition(makeType) >= 0){
      rvMakeup.smoothScrollToPosition(HtUICacheUtils.beautyLipstickPosition(makeType));
    }
    //sync progress
    RxBus.get().post(HTEventAction.ACTION_SYNC_PROGRESS, "");
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
      tvTitle.setTextColor(
          ContextCompat.getColorStateList(getContext(),
              R.color.color_selector_tab_dark));
      line.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.gray_line));

    } else {
      container.setBackground(ContextCompat.getDrawable(getContext(),
          R.color.light_background));
      tvTitle.setTextColor(
          ContextCompat.getColorStateList(getContext(),
              R.color.color_selector_tab_light));
      line.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.black_line));
    }
  }

  @Override protected void onFragmentStartLazy() {
    super.onFragmentStartLazy();
    //更新ui状态
    // HtState.currentThirdViewState = HTViewState.MAKEUP_LIPSTICK;
    //同步滑动条
    RxBus.get().post(HTEventAction.ACTION_SYNC_PROGRESS, "");
    listAdapter.notifyDataSetChanged();
  }

}
