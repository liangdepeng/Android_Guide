package com.example.myapplication.base;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/9/2
 * <p>
 * Summary: BaseFragment
 */
public class BaseFragment extends Fragment {

    private final String tag = "BaseFragments";

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        Log.e(tag, "-- onAttach -- " + getClass().getSimpleName());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(tag, "-- onCreate -- " + getClass().getSimpleName());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(tag, "-- onActivityCreated -- " + getClass().getSimpleName());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(tag, "-- onCreateView -- " + getClass().getSimpleName());
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e(tag, "-- onViewCreated -- " + getClass().getSimpleName());
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.e(tag, "-- onStart -- " + getClass().getSimpleName());
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(tag, "-- onResume -- " + getClass().getSimpleName());
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(tag, "-- onPause -- " + getClass().getSimpleName());
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(tag, "-- onStop -- " + getClass().getSimpleName());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(tag, "-- onDestroyView -- " + getClass().getSimpleName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(tag, "-- onDestroy -- " + getClass().getSimpleName());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e(tag, "-- onDetach -- " + getClass().getSimpleName());
    }

}
