package com.szbm.wh.x.bmlogg.ui.ui.boreholes;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import kotlin.Unit;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import com.szbm.wh.x.bmlogg.GlideApp;
import com.szbm.wh.x.bmlogg.GlideRequests;
import com.szbm.wh.x.bmlogg.R;
import com.szbm.wh.x.bmlogg.databinding.BoreholesFragmentBinding;
import com.szbm.wh.x.bmlogg.page.Status;
import com.szbm.wh.x.bmlogg.ui.common.InjectFragment;

import javax.inject.Inject;


public class BoreholesFragment extends InjectFragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private BoreholesViewModel mViewModel;
    BoreholesFragmentBinding boreholesFragmentBinding;

    public static BoreholesFragment newInstance() {
        return new BoreholesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        boreholesFragmentBinding =DataBindingUtil.inflate(inflater,R.layout.boreholes_fragment, container, false);
        return boreholesFragmentBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this,viewModelFactory).get(BoreholesViewModel.class);

        initAdapter();
        initSwipeToRefresh();
        initSearch();

        mViewModel.showSubreddit(null);
    }

    private void initAdapter() {
        GlideRequests glide = GlideApp.with(this);
        BoreholesAdapter adapter = new BoreholesAdapter(glide,()->{
            mViewModel.retry();
            return Unit.INSTANCE;
        });

        boreholesFragmentBinding.list.setAdapter(adapter);
        mViewModel.posts.observe(this,
                it->adapter.submitList(it));

        mViewModel.networkState.observe(this,
                it->adapter.setNetworkState(it));
    }

    private void initSwipeToRefresh() {
        mViewModel.refreshState.observe(this,
                it-> boreholesFragmentBinding.swipeRefresh.setRefreshing(
                        it.getStatus() == Status.RUNNING
                ));
        boreholesFragmentBinding.swipeRefresh.setOnRefreshListener(()->{
            mViewModel.refresh();
        });
    }

    private void initSearch() {
        boreholesFragmentBinding.input.setOnEditorActionListener((v,actionId,event)->{
            if(actionId == EditorInfo.IME_ACTION_GO){
                updatedSubredditFromInput();
                return true;
            }else{
                return false;
            }
        });

        boreholesFragmentBinding.input.setOnKeyListener((v,keyCode,event)->{
            if(event.getAction() == KeyEvent.ACTION_DOWN&& keyCode == KeyEvent.KEYCODE_ENTER){
                updatedSubredditFromInput();
                return true;
            }else{
                return false;
            }
        });
    }

    private void updatedSubredditFromInput() {
        if(boreholesFragmentBinding.input.getText() !=null){
            String input = boreholesFragmentBinding.input.getText().toString().trim();
            if(mViewModel.showSubreddit(input)){
                boreholesFragmentBinding.list.scrollToPosition(0);
                ((BoreholesAdapter) boreholesFragmentBinding.list.getAdapter()).submitList(null);
            }
        }
    }

}
