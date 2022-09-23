package com.texeljoy.ht_effect.utils;

public interface HtConfigCallBack<T> {

  void success(T list);

  void fail(Exception error);

}
