package com.chrnie.various;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

public final class Various {

  private Various() {
    throw new IllegalStateException("none constructor");
  }

  public static Builder of(List<?> dataList) {
    return Various.of(dataList, new DefaultItemMatcherFactory());
  }

  public static Builder of(List<?> dataList, ItemMatcher.Factory factory) {
    return new Builder(dataList, factory);
  }

  public interface OnCreateCallback<VH extends RecyclerView.ViewHolder> {

    VH onCreate(LayoutInflater inflater, ViewGroup container);
  }

  public interface OnBindCallback<VH extends RecyclerView.ViewHolder, T> {

    void onBind(VH holder, T data, List<Object> payloads);
  }

  public static class Builder {

    final List<?> dataList;
    final ItemMatcher.Factory factory;
    final List<Item> itemList = new ArrayList<>(2);

    Builder(List<?> dataList, ItemMatcher.Factory factory) {
      this.dataList = dataList;
      this.factory = factory;
    }

    public <VH extends RecyclerView.ViewHolder, T> Builder register(Class<T> dateType,
        OnCreateCallback<VH> onCreateCallback) {
      return register(dateType, onCreateCallback, null);
    }

    public <VH extends RecyclerView.ViewHolder, T> Builder register(Class<T> dateType,
        OnCreateCallback<VH> onCreateCallback, OnBindCallback<VH, T> onBindCallback) {
      itemList.add(new Item(dateType, onCreateCallback, onBindCallback));
      return this;
    }

    public <T, VH extends ViewHolder<T>> Builder register(Class<T> dateType,
        ViewHolderFactory<T, VH> factory) {
      return register(dateType, factory::create, ViewHolder::bind);
    }

    public RecyclerView.Adapter<RecyclerView.ViewHolder> build() {
      return new Adapter(this);
    }
  }

  public abstract class ViewHolder<T> extends RecyclerView.ViewHolder {

    public ViewHolder(View itemView) {
      super(itemView);
    }

    protected abstract void bind(T date, List<Object> payloads);

    public Context getContext() {
      return itemView.getContext();
    }

    protected boolean onFailedToRecycleView() {
      return false;
    }

    protected void onViewAttachedToWindow() {

    }

    protected void onViewDetachedFromWindow() {

    }

    protected void onViewRecycled() {

    }
  }
}
