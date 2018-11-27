package com.szbm.wh.x.bmlogg.worker;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkContinuation;
import androidx.work.WorkManager;

public class DBDownloadOperation {

    public static final String DB_DOWNLOAD_WORK_NAME = "bmlg.db.down.work";
    private final WorkContinuation mContinuation;

    private DBDownloadOperation(@NonNull WorkContinuation continuation) {
        mContinuation = continuation;
    }

    public WorkContinuation getContinuation() {
        return mContinuation;
    }

    public static class Builder {

        private int iid;

        public Builder(@NonNull int iid) {
            this.iid = iid;
        }

        public DBDownloadOperation build() {

            Data data = createInputData();

            WorkContinuation continuation = WorkManager.getInstance()
                    .beginUniqueWork(DB_DOWNLOAD_WORK_NAME,
                            ExistingWorkPolicy.KEEP,
                            new OneTimeWorkRequest
                                    .Builder(ProjectWorker.class)
                                    .setInputData(data)
                                    .addTag(Constants.TAG_PROJECT)
                                    .build());

            OneTimeWorkRequest bh =
                    new OneTimeWorkRequest
                            .Builder(BoreholeWorker.class)
                            .setInputData(data)
                            .addTag(Constants.TAG_BH)
                            .build();
            continuation = continuation.then(bh);


            return new DBDownloadOperation(continuation);
        }

        private Data createInputData() {
            return new Data.Builder()
                    .putInt(Constants.DATA_KEY, iid).build();
        }
    }


}
