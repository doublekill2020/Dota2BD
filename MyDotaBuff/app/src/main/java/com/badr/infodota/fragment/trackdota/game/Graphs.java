package com.badr.infodota.fragment.trackdota.game;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.badr.infodota.api.trackdota.core.CoreResult;
import com.badr.infodota.api.trackdota.live.LiveGame;
import com.badr.infodota.util.Refresher;
import com.badr.infodota.util.Updatable;
import com.badr.infodota.view.GraphMarkerView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ValueFormatter;

import java.util.ArrayList;
import java.util.List;

import cn.edu.mydotabuff.R;

/**
 * Created by ABadretdinov
 * 18.04.2015
 * 11:31
 */
public class Graphs extends Fragment implements Updatable<Pair<CoreResult,LiveGame>> {
    private Refresher refresher;
    private CoreResult coreResult;
    private LiveGame liveGame;
    private Spinner mChartSpinner;
    private LineChart mChart;
    private SwipeRefreshLayout mScrollContainer;
    private LineData mLineData;
    private LineDataSet mLineDataSet;
    private List<Entry> mLineDataSetEntries;
    private ArrayList<String> mTicks;
    int mSelectedStat;

    public static Graphs newInstance(Refresher refresher,CoreResult coreResult,LiveGame liveGame){
        Graphs fragment=new Graphs();
        fragment.refresher=refresher;
        fragment.coreResult=coreResult;
        fragment.liveGame=liveGame;
        return fragment;
    }

    final private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            if(refresher!=null) {
                mScrollContainer.setRefreshing(true);
                refresher.onRefresh();
            }
        }
    };
    @Override
    public void onUpdate(Pair<CoreResult, LiveGame> entity) {
        mScrollContainer.setRefreshing(false);
        this.coreResult=entity.first;
        this.liveGame=entity.second;
        runOnTick();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.trackdota_game_graphs,container,false);

        mScrollContainer = (SwipeRefreshLayout) root.findViewById(R.id.listContainer);
        mScrollContainer.setOnRefreshListener(mOnRefreshListener);
        mScrollContainer.setColorSchemeResources(R.color.primary);

        mChart = (LineChart) root.findViewById(R.id.chart);
        mChartSpinner= (Spinner) root.findViewById(R.id.chart_type);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initChart();
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.trackdota_team_charts));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mChartSpinner.setAdapter(adapter);
        mChartSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(mSelectedStat!=position){
                    mSelectedStat=position;
                    clearChart();
                    runOnTick();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        runOnTick();
    }

    private void runOnTick() {
        if(coreResult!=null&&liveGame!=null&&coreResult.getStatus()>=2) {
            LineData lineData = mChart.getLineData();
            LineDataSet lineDataSet = lineData.getDataSetByIndex(0);
            long[] ticks = liveGame.getStats().get("tick");
            if(ticks!=null) {
                switch (mSelectedStat) {
                    case 0:
                        lineDataSet.setColor(getResources().getColor(R.color.gold));
                        for (int i = lineDataSet.getEntryCount(); i < ticks.length; i++) {
                            mTicks.add(String.valueOf(ticks[i]));
                            lineData.addEntry(
                                    new Entry(
                                            liveGame.getRadiant().getStats().getNetGold()[i] - liveGame.getDire().getStats().getNetGold()[i],
                                            lineDataSet.getEntryCount()),
                                    0);
                        }
                        break;
                    case 1:
                        lineDataSet.setColor(getResources().getColor(R.color.xp));
                        for (int i = lineDataSet.getEntryCount(); i < ticks.length; i++) {
                            mTicks.add(String.valueOf(ticks[i]));
                            lineData.addEntry(
                                    new Entry(
                                            liveGame.getRadiant().getStats().getNetExp()[i] - liveGame.getDire().getStats().getNetExp()[i],
                                            lineDataSet.getEntryCount()),
                                    0);
                        }
                        break;
                }
            }
            mChart.notifyDataSetChanged();
            mChart.invalidate();
        }
    }

    public void initChart(){
        mChart.setDescription("");
        mChart.setDragEnabled(false);
        mChart.setScaleEnabled(false);
        mChart.setPinchZoom(false);

        GraphMarkerView markerView=new GraphMarkerView(mChart.getContext(),R.layout.trackdota_graph_marker);
        mChart.setMarkerView(markerView);
        mChart.setHighlightIndicatorEnabled(false);

        XAxis xAxis=mChart.getXAxis();
        xAxis.removeAllLimitLines();
        xAxis.setDrawLimitLinesBehindData(true);
        xAxis.setDrawGridLines(true);

        YAxis leftAxis=mChart.getAxisLeft();
        leftAxis.removeAllLimitLines();
        leftAxis.setStartAtZero(false);

        LimitLine limitLine=new LimitLine(0f);
        limitLine.setLineColor(Color.WHITE);
        limitLine.setLineWidth(2f);
        leftAxis.addLimitLine(limitLine);

        mChart.setBackgroundColor(Color.BLACK);
        mChart.setDrawBorders(false);
        mChart.getLegend().setEnabled(false);
        mChart.setDoubleTapToZoomEnabled(false);
        mChart.setDrawGridBackground(false);

        leftAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int)value);
            }
        });

        leftAxis.setTextColor(Color.WHITE);

        mChart.getAxisRight().setEnabled(false);
        mChart.animateX(2500);
        clearChart();
    }

    public void clearChart(){
        mLineDataSetEntries=new ArrayList<>();
        mLineDataSet=new LineDataSet(mLineDataSetEntries,"");
        mLineDataSet.setLineWidth(2f);
        mLineDataSet.setCircleSize(0f);
        mLineDataSet.addEntry(new Entry(0f,0));
        mLineDataSet.setDrawValues(false);
        mTicks=new ArrayList<>();
        mTicks.add("0");
        List list=new ArrayList();
        list.add(mLineDataSet);
        mLineData=new LineData(mTicks,list);
        mChart.setData(mLineData);
    }
}
