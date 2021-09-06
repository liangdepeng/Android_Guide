package com.example.myapplication.base;

import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/9/6
 * <p>
 * Summary:
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {

    private final SparseArray<View> array;

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
        array = new SparseArray<>();
    }

    public <T extends View> T getView(int viewId) {
        View view = array.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            array.put(viewId, view);
        }
        return ((T) view);
    }

    public BaseViewHolder setText(int viewId, CharSequence text) {
        TextView textView = getView(viewId);
        if (textView != null) {
            textView.setText(text);
        }
        return this;
    }

    public BaseViewHolder setVisibility(int viewId, int visibility) {
        View view = getView(viewId);
        if (view != null) {
            view.setVisibility(visibility);
        }
        return this;
    }

}
