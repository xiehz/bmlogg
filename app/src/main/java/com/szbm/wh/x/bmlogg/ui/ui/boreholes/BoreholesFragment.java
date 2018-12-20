package com.szbm.wh.x.bmlogg.ui.ui.boreholes;

import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import kotlin.Unit;

import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.szbm.wh.x.bmlogg.R;
import com.szbm.wh.x.bmlogg.databinding.BoreholesFragmentBinding;
import com.szbm.wh.x.bmlogg.page.Status;
import com.szbm.wh.x.bmlogg.ui.BhActivityArgs;
import com.szbm.wh.x.bmlogg.ui.common.InjectFragment;
import com.szbm.wh.x.bmlogg.util.StringPreder;

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
        setHasOptionsMenu(true);
        boreholesFragmentBinding =DataBindingUtil.inflate(inflater,R.layout.boreholes_fragment, container, false);
        return boreholesFragmentBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this,viewModelFactory).get(BoreholesViewModel.class);

        mViewModel.current.observe(this,projectInfo -> {
            if(projectInfo != null)
                mViewModel.showBorehole(null);
        });
        initAdapter();
        initSwipeToRefresh();
        mViewModel.makeCurrent();
    }

    private void initAdapter() {
        BoreholesAdapter adapter = new BoreholesAdapter(()->{
            mViewModel.retry();
            return Unit.INSTANCE;
        },(bh_boreholeInfo -> {
            BhActivityArgs args = new BhActivityArgs
                    .Builder(bh_boreholeInfo.getCode())
                    .setComSzbmWhXBmloggUiBhIid(bh_boreholeInfo.getIid())
                    .build();
            Navigation.findNavController(boreholesFragmentBinding.getRoot()).navigate(
                    R.id.action_boreholesFragment_to_bhActivity,args.toBundle()
            );
            return Unit.INSTANCE;
        }));

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

    private void updatedBoreholeFromInput(String text) {
        String input =text == null? null: text.trim();
        if(mViewModel.showBorehole(input)){
            boreholesFragmentBinding.list.scrollToPosition(0);
            ((BoreholesAdapter) boreholesFragmentBinding.list.getAdapter()).submitList(null);
        }
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.boreholes_menu,menu);
        MenuItem searchItem = menu.findItem(R.id.action_search_view);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setInputType(InputType.TYPE_CLASS_NUMBER);
        searchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnCloseListener(()->{
            updatedBoreholeFromInput(null);
            searchView.clearFocus();
            searchView.onActionViewCollapsed();
            return true;
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                updatedBoreholeFromInput(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.setQueryHint("请输入钻孔名");
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_search_view:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
