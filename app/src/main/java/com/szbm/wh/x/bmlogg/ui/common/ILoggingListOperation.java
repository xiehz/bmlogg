package com.szbm.wh.x.bmlogg.ui.common;

import com.szbm.wh.x.bmlogg.pojo.LoggingListItem;

public interface ILoggingListOperation {
    void onLoggingDeleted();
    void onUploaded(LoggingListItem loggingListItem);
    void onLoggingAdded(LoggingListItem loggingListItem);

    void logging(LoggingListItem loggingListItem);
    void uploading(LoggingListItem loggingListItem);
    void delete(LoggingListItem loggingListItem);
    void add(LoggingListItem last);
}
