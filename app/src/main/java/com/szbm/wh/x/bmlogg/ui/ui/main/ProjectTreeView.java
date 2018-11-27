package com.szbm.wh.x.bmlogg.ui.ui.main;


import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.WorkerThread;

/**
 * Created by hzxie on 2017/9/30.
 */

public class ProjectTreeView extends LinearLayout {

    List<ProjectNode> adater;
    HashMap<Integer,CheckBox> relations;

    public ProjectTreeView(Context context) {
        super(context);
    }

    public ProjectTreeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProjectTreeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setAdapter(List<ProjectNode> adapter)
    {
        this.adater = adapter;
        this.relations = new HashMap<>();

        if(adater == null||
                adater.size() == 0)
            return;

        add(this,0);
        addListeners();
    }

    @WorkerThread
    public final List<ProjectNode> getAdapter()
    {
        return this.adater;
    }

    private void add(LinearLayout parent,Integer integer)
    {
        ProjectNode node = adater.get(integer);
        int sp = 14;
        int leftMargin = 32;
        int topMargin = 8;
        switch (node.level)
        {
            case PROJECT:
                sp = 24;
                leftMargin = 8;
                topMargin = 16;
                break;
            case PHASE:
                sp = 20;
                leftMargin = 16;
                topMargin = 8;
                break;
            case AREA:
                leftMargin = 32;
                topMargin = 8;
                sp = 14;
                break;
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(leftMargin,topMargin,0,0);

        LinearLayout region = new LinearLayout(getContext());
        region.setLayoutParams(layoutParams);
        region.setOrientation(VERTICAL);

        LinearLayout row = new LinearLayout(getContext());
        row.setOrientation(HORIZONTAL);

        CheckBox checkBox = new CheckBox(getContext());
        checkBox.setChecked(node.checked);
        relations.put(integer,checkBox);
        row.addView(checkBox);

        TextView textView = new TextView(getContext());
        textView.setText(node.name);
        textView.setTextSize(sp);
        row.addView(textView);
        region.addView(row);

        if(node.childs != null)
        {
            for (Integer child: node.childs
                    ) {
                add(region,child);
            }
        }
        parent.addView(region);
    }
    private void addListeners()
    {
        for (Map.Entry<Integer, CheckBox> entry : relations.entrySet()) {
            entry.getValue().setOnCheckedChangeListener(new CheckChangedListener(entry.getKey()));
        }
    }

    private void removeListeners()
    {
        for (Map.Entry<Integer, CheckBox> entry : relations.entrySet()) {
            entry.getValue().setOnCheckedChangeListener(null);
        }
    }


    class CheckChangedListener implements CheckBox.OnCheckedChangeListener
    {
        Integer position;
        CheckChangedListener(Integer position)
        {
            this.position = position;
        }
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            if (this.position == null || position == -1) return;
            removeListeners();

            ProjectNode projectNode = adater.get(position);
            projectNode.checked = isChecked;
            //先逆向
            if(isChecked)
            {
                int parent = projectNode.parent;
                while (parent != -1)
                {
                    relations.get(parent).setChecked(true);
                    ProjectNode parentNode = adater.get(parent);
                    parentNode.checked = true;
                    parent = parentNode.parent;
                }
            }

            //再子节点
            setChildCheck(position,isChecked);
            addListeners();
        }
    }
    private void setChildCheck(Integer inter,boolean isChecked)
    {
        if(inter == null || inter == -1)return;
        relations.get(inter).setChecked(isChecked);
        ProjectNode projectNode = adater.get(inter);
        projectNode.checked = isChecked;
        if(projectNode.childs != null)
        {
            for (Integer i: projectNode.childs
                    ) {
                setChildCheck(i,isChecked);
            }
        }
    }

    public ProjectNode getProject(ProjectEnum type)
    {
        if(adater == null) return null;
        if(adater == null) return null;
        for(ProjectNode projectNode : adater)
        {
            if(projectNode.level.equals(type))
                return projectNode;
        }
        return null;
    }

}
