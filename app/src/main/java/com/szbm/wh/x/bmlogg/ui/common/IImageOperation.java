package com.szbm.wh.x.bmlogg.ui.common;

import android.net.Uri;

import com.szbm.wh.x.bmlogg.vo.Bh_imagesinfo;

import java.util.List;

/*
adapter  - viewmodel - repository
 */
public interface IImageOperation {
    /*
    from ViewModel to ui
     */
    void onPhotoChanged(Bh_imagesinfo result);
    void onPhotoAdded(Bh_imagesinfo result);
    void onPhotoSaved(Bh_imagesinfo result);
    void onPhotoDeleted();

    /*
    invoke ViewModel from ui
     */
    void deletePhoto(Bh_imagesinfo bh_imagesinfo);
    void addPhoto(Uri fileUri);
    void changePhoto(Bh_imagesinfo bh_imagesinfo, Uri uri);
    void savePhoto(Bh_imagesinfo bh_imagesinfo);


}
