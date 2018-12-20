package com.szbm.wh.x.bmlogg.ui.ui.bh;

import android.view.KeyEvent;

import com.afollestad.materialdialogs.MaterialDialog;
import com.szbm.wh.x.bmlogg.pojo.LoggingListItem;
import com.szbm.wh.x.bmlogg.ui.common.ILoggingListOperation;
import com.szbm.wh.x.bmlogg.ui.common.InjectFragment;
import com.szbm.wh.x.bmlogg.ui.common.LoggingListAdapter;

import java.util.List;

public abstract class LoggingListFragment extends InjectFragment implements ILoggingListOperation {
    private LoggingListAdapter loggingListAdapter;
    private LoggingListItem current;
    private void createAdapter(){
        loggingListAdapter = new LoggingListAdapter(
                loggingListItem -> {
                    current = loggingListItem;
                },
                loggingListItem -> {
                    current = loggingListItem;

                    new MaterialDialog.Builder(getContext())
                            .title("删除")
                            .content("该记录未上传，删除后无法找回！")
                            .positiveText("删除")
                            .onNegative(((dialog, which) -> {

                            }))
                            .negativeText("取消")
                            .onPositive(((dialog, which) -> {
                                delete(current);
                            }))
                            .build().show();
                },
                loggingListItem -> {
                    current = loggingListItem;

                    new MaterialDialog.Builder(getContext())
                            .title("上传")
                            .content("只允许上传一次，上传成功后，无法再次上传！")
                            .onNegative(((dialog, which) -> {

                            }))
                            .negativeText("取消")
                            .onPositive(((dialog, which) -> {
                                uploading(current);
                            }))
                            .build().show();
                }
        );
    }
    public final LoggingListAdapter getLoggingListAdapter(){
        if(this.loggingListAdapter == null)
            this.createAdapter();
        return this.loggingListAdapter;
    }
    public final void submit(List<LoggingListItem> items){
        getLoggingListAdapter().submit(items);
    }
    public final LoggingListItem getLast(){
        if(this.loggingListAdapter.getItemCount() == 0)
            return null;
        else
            return this.loggingListAdapter.getItem(this.loggingListAdapter.getItemCount()-1);
    }

    @Override
    public void onLoggingDeleted() {
            loggingListAdapter.removeItem(current);
    }

    @Override
    public void onUploaded(LoggingListItem loggingListItem) {
        loggingListAdapter.updateItem(current,loggingListItem);
    }
    @Override
    public void onLoggingAdded(LoggingListItem loggingListItem) {
        loggingListAdapter.addItem(loggingListItem);
    }

    @Override
    public abstract void logging(LoggingListItem loggingListItem) ;

    @Override
    public abstract void uploading(LoggingListItem loggingListItem);

    @Override
    public abstract  void delete(LoggingListItem loggingListItem) ;

    @Override
    public abstract void add(LoggingListItem last);
}
