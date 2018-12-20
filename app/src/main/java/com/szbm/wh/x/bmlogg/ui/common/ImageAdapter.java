package com.szbm.wh.x.bmlogg.ui.common;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.szbm.wh.x.bmlogg.R;
import com.szbm.wh.x.bmlogg.databinding.ImageCardBinding;
import com.szbm.wh.x.bmlogg.pojo.ImageInfo;
import com.szbm.wh.x.bmlogg.ui.ui.project.ProjectAdapter;
import com.szbm.wh.x.bmlogg.util.StringPreder;
import com.szbm.wh.x.bmlogg.vo.Bh_imagesinfo;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class ImageAdapter  extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder>{

    List<Bh_imagesinfo> imageInfoList;
    ClickListener takephotoListener;
    ClickListener deleteListener;
    ClickListener saveListener;
    public ImageAdapter(ClickListener takePhotoClickListener, ClickListener deleteClickListener,ClickListener saveClickListener) {
        super();
        this.imageInfoList = new ArrayList<>();
        this.takephotoListener = takePhotoClickListener;
        this.deleteListener = deleteClickListener;
        this.saveListener = saveClickListener;
    }
    public void submit(List<Bh_imagesinfo> bh_imagesinfos){
        this.imageInfoList = bh_imagesinfos;
        this.notifyDataSetChanged();
    }

    public void addItem(Bh_imagesinfo bh_imagesinfo){
        if(bh_imagesinfo != null)
        {
            int p = this.getItemCount();
            this.imageInfoList.add(bh_imagesinfo);
            this.notifyItemInserted(p);
        }
    }

    public void removeItem(Bh_imagesinfo bh_imagesinfo){
        if(bh_imagesinfo != null){
            int index = this.imageInfoList.indexOf(bh_imagesinfo);
            this.imageInfoList.remove(index);
            this.notifyItemRemoved(index);
        }
    }

    public void updateItem(Bh_imagesinfo current, Bh_imagesinfo bh_imagesinfo){
        if(bh_imagesinfo != null){
            current.setUrl(bh_imagesinfo.getUrl());
            this.notifyItemChanged(this.imageInfoList.indexOf(current));
        }
    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_card,parent,false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        holder.bind(imageInfoList.get(position),"记录"+String.valueOf(position));
    }

    @Override
    public int getItemCount() {
        return imageInfoList.size();
    }

    public List<Bh_imagesinfo> getImageInfoList(){
        return this.imageInfoList;
    }

    public boolean isCanAdd(int last, RecyclerView.ViewHolder viewHolder){
        if(last < 0) return true;
        if(last >= getItemCount()) return false;
        ImageViewHolder imageViewHolder = ((ImageViewHolder) viewHolder);
        if(imageViewHolder == null) return  false;

        Bh_imagesinfo imageInfo = this.imageInfoList.get(last);
        if(StringPreder.isNullOrEmpty(imageInfo.getUrl()) ){
            imageViewHolder.binding.imageButton.requestFocus();
            imageViewHolder.binding.textView9.setVisibility(View.VISIBLE);
            imageViewHolder.binding.textView9.setText("请添加照片!");
            return false;
        }

//        if(StringPreder.isNullOrEmpty(imageInfo.getName())){
//            imageViewHolder.binding.imagegName.requestFocus();
//            imageViewHolder.binding.textView9.setVisibility(View.VISIBLE);
//            imageViewHolder.binding.textView9.setText("照片名不能为空!");
//            return false;
//        }

//        if(StringPreder.isNullOrEmpty(imageInfo.getDesc())){
//            imageViewHolder.binding.imageDesc.requestFocus();
//            imageViewHolder.binding.textView9.setText("照片名不能为空!");
//            return false;
//        }
        imageViewHolder.binding.textView9.setVisibility(View.GONE);
        return true;
    }

    class ImageViewHolder extends RecyclerView.ViewHolder{
        ImageCardBinding binding;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            binding.executePendingBindings();
        }

        public void bind(Bh_imagesinfo imagesinfo,String title){
            binding.setImage(imagesinfo);
            binding.setDelete(new DeleteClickListener());
            binding.setTakephoto(new TakePhotoClickListener());
            binding.setSave(new SaveClickListener());
            binding.textView20.setText(title);
            binding.imageButton.setTag(imagesinfo);
            binding.imageButton2.setTag(imagesinfo);
            binding.imageButton1.setTag(imagesinfo);
        }
    }

    class TakePhotoClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if(takephotoListener != null)
                takephotoListener.onClick((Bh_imagesinfo)v.getTag());
        }
    }
    class SaveClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if(saveListener != null)
                saveListener.onClick((Bh_imagesinfo)v.getTag());
        }
    }

    class DeleteClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if(deleteListener != null)
                deleteListener.onClick((Bh_imagesinfo)v.getTag());
        }
    }

    public interface ClickListener{
        void onClick(Bh_imagesinfo imagesinfo);
    }
}
