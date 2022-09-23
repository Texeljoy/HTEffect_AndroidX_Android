package com.texeljoy.ht_effect.base;

import android.os.Bundle;
import android.view.View;
import com.hwangjr.rxbus.RxBus;
import com.shizhefei.fragment.LazyFragment;

/**
 * 封装的懒加载的Fragment
 */
public abstract class HtBaseLazyFragment extends LazyFragment {

  @Override protected void onCreateViewLazy(Bundle savedInstanceState) {
    super.onCreateViewLazy(savedInstanceState);
    RxBus.get().register(this);
    setContentView(getLayoutId());
    initView(getView(), savedInstanceState);
  }

  protected abstract int getLayoutId();

  protected abstract void initView(View view, Bundle savedInstanceState);

  @Override protected void onDestroyViewLazy() {
    super.onDestroyViewLazy();
    RxBus.get().unregister(this);
  }
}
