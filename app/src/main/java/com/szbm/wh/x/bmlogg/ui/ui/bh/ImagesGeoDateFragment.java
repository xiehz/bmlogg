package com.szbm.wh.x.bmlogg.ui.ui.bh;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.afollestad.materialdialogs.MaterialDialog;
import com.amap.api.maps.model.LatLng;
import com.szbm.wh.x.bmlogg.ui.common.IImageOperation;
import com.szbm.wh.x.bmlogg.ui.common.ImageAdapter;
import com.szbm.wh.x.bmlogg.ui.location.SignIn;
import com.szbm.wh.x.bmlogg.ui.ui.map.CheckPermissionsFragment;
import com.szbm.wh.x.bmlogg.vo.Bh_imagesinfo;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.core.content.FileProvider;

public abstract class ImagesGeoDateFragment
        extends CheckPermissionsFragment
        implements SignIn.SignInListener ,IImageOperation {
    private ImageAdapter imageAdapter;
    private Uri fileUri;
    private Bh_imagesinfo current;
    private LatLng latLng;

    private  void createAdapter(){
        imageAdapter = new ImageAdapter(p -> {
            current = p;
            new MaterialDialog.Builder(getContext())
                    .title("重新拍照")
                    .content("重新拍照会替换当前照片,当前照片无法再次添加！")
                    .positiveText("确定")
                    .negativeText("取消")
                    .onNegative((dialog, which) -> {})
                    .onPositive(((dialog, which) ->{
                        chPhoto();
                    }))
                    .build().show();
        }, p -> {
            current = p;
            new MaterialDialog.Builder(getContext())
                    .title("删除照片")
                    .content("删除照片记录后无法找回！")
                    .positiveText("确定")
                    .negativeText("取消")
                    .onNegative((dialog, which) -> {})
                    .onPositive(((dialog, which) ->{
                        deletePhoto(current);
                    }))
                    .build().show();
        },p->{
            current = p;
            savePhoto(current);
        }
        );
    }

    protected void setBorehole(LatLng la){
        this.latLng = la;
    }

    public ImageAdapter getImageAdapter(){
        if(this.imageAdapter == null)
            createAdapter();
        return this.imageAdapter;
    }
    @Override
    public final void onPhotoChanged(Bh_imagesinfo result) {
        imageAdapter.updateItem(current,result);
    }

    @Override
    public final void onPhotoAdded(Bh_imagesinfo result) {
        imageAdapter.addItem(result);
    }

    @Override
    public final void onPhotoDeleted() {
        imageAdapter.removeItem(current);
    }

    @Override
    public final void onPhotoSaved(Bh_imagesinfo result) {

    }

    protected final void submitPhotos(List<Bh_imagesinfo> bh_imagesinfos) {
        imageAdapter.submit(bh_imagesinfos);
    }


    @Override
    public final void goToMap(LatLng latLng, String code) {

    }



    @Override
    public final void startLT(Context context) {
        SignIn.getInstance().create(getActivity(),latLng,this);
        SignIn.getInstance().startSignIn();
    }

    @Override
    public void onSignInSuccess(double lng, double lat, String time, String info) {
        SignIn.getInstance().destory();
    }

    protected void takePhoto(){
        int requestCode = 1;
        photo(requestCode);
    }
    private void chPhoto(){
        int requestCode = 2;
        photo(requestCode);

    }
    public void photo(int requestCode) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "bmlogg");

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("bmlogg", "failed to create directory");
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        String path = mediaStorageDir.getPath() + File.separator + timeStamp + ".jpg";
        String authority = getActivity().getPackageName() + ".fileprovider";
        fileUri = FileProvider.getUriForFile(getActivity(),
                authority,
                new File(path));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        // start the image capture Intent
        startActivityForResult(intent, requestCode);
    }

    @Override
    public final void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode)
            {
                case 1:
                    Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    intent.setData(fileUri);
                    getActivity().sendBroadcast(intent);
                    addPhoto(fileUri);
                    break;
                case 2:
                    Intent intent1 = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    intent1.setData(fileUri);
                    getActivity().sendBroadcast(intent1);
                    changePhoto(current,fileUri);
                    break;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
