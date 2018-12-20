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

        private long iid;
        private long number;

        public Builder(@NonNull long iid,long number) {
            this.iid = iid;
            this.number = number;
        }

        public DBDownloadOperation build() {

//            Data data = new Data.Builder()
//                    .putInt(Constants.DATA_PROJECT_KEY, iid).build();
//
//            WorkContinuation continuation = WorkManager.getInstance()
//                    .beginUniqueWork(DB_DOWNLOAD_WORK_NAME,
//                            ExistingWorkPolicy.KEEP,
//                            new OneTimeWorkRequest
//                                    .Builder(ProjectWorker.class)
//                                    .setInputData(data)
//                                    .addTag(Constants.TAG_PROJECT)
//                                    .build());


            Data data = new Data.Builder()
                    .putLong(Constants.DATA_PROJECT_KEY, iid)
                    .putLong(Constants.DATA_NUMBER_KEY,number).build();
            OneTimeWorkRequest bh =
                    new OneTimeWorkRequest
                            .Builder(BoreholeWorker.class)
                            .setInputData(data)
                            .addTag(Constants.TAG_BH)
                            .build();

            WorkContinuation continuation = WorkManager.getInstance()
                    .beginUniqueWork(DB_DOWNLOAD_WORK_NAME,
                            ExistingWorkPolicy.KEEP,
                            bh);
//            continuation = continuation.then(bh);

            return new DBDownloadOperation(continuation);
        }
    }


}
