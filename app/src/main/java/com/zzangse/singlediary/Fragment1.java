package com.zzangse.singlediary;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import lib.kingja.switchbutton.SwitchMultiButton;

public class Fragment1 extends Fragment {
    RecyclerView recyclerView;
    NoteAdapter adapter;
    Context context;
    OnTabItemSelectedListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        if (context instanceof OnTabItemSelectedListener) {
            listener=(OnTabItemSelectedListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        if (context != null) {
            context=null;
            listener=null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment1,container,false);

        initUI(rootView);
        return rootView;
    }
    public void initUI(ViewGroup viewGroup){
        Button todayWriteBtn = viewGroup.findViewById(R.id.fragment1_Btn);
        todayWriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onTabSelected(1);
                }
            }
        });
        SwitchMultiButton switchMultiButton = viewGroup.findViewById(R.id.fragment1_switchBtn);
        switchMultiButton.setOnSwitchListener(new SwitchMultiButton.OnSwitchListener() {
            @Override
            public void onSwitch(int position, String tabText) {
                Toast.makeText(getContext(),tabText,Toast.LENGTH_SHORT).show();
                adapter.switchLayout(position);
                adapter.notifyDataSetChanged();
            }
        });
        recyclerView = viewGroup.findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new NoteAdapter();
        adapter.addItem(new Note(0,"0","서북구 불당동","","",
                "오늘 토요일","0", "capture1.jpg","12월23일"));
        adapter.addItem(new Note(1,"1","서북구 두정동","","",
                "오늘 일요일","0", "capture1.jpg","12월24일"));
        adapter.addItem(new Note(2,"0","서북구 신부동","","",
                "오늘 월요일","0", "capture1.jpg","12월25일"));

        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnNoteItemClickListener() {
            @Override
            public void onItemClick(NoteAdapter.ViewHolder holder, View view, int position) {
                Note item = adapter.getItem(position);
                Toast.makeText(getContext(),"아이템 선택됨: "+item.getContents(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}