package com.example.myapplication.base;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/9/6
 * <p>
 * Summary:
 */
public abstract class BaseRecyclerAdapter<T, ViewHolder extends BaseViewHolder> extends RecyclerView.Adapter<ViewHolder> {

    private final List<T> mList = new ArrayList<>();
    protected Context mContext;

    public BaseRecyclerAdapter(Context context) {
        mContext = context;
    }

    public final void setList(List<T> list) {
        mList.clear();
        if (list != null)
            mList.addAll(list);
        notifyDataSetChanged();
    }

    public final void addData(T data){
        mList.add(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public final void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        onBindItemView(holder, mList.get(position), position);
    }

    protected abstract void onBindItemView(ViewHolder holder, T t, int position);

    @Override
    public final int getItemCount() {
        return mList.size();
    }

    public boolean isLastPosition(int position) {
        return position == mList.size() - 1;
    }

    public int getLastIndex(){
        return mList.size()-1;
    }
}
