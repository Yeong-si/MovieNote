package com.example.movienote;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class ChartFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Fragment의 레이아웃을 inflate
        View view = inflater.inflate(R.layout.activity_chart, container, false);

        // 버튼 클릭 이벤트 처리
//        Button chartButton = view.findViewById(R.id.your_chart_button_id);
//        chartButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // 부모 액티비티에서 Fragment 전환을 처리하는 메서드 호출
//                ((YourParentActivity) getActivity()).replaceFragment(new PieChartFragment());
//            }
//        });

        return view;
    }
}

