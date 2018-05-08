package com.openclassrooms.mynews.Views;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.openclassrooms.mynews.Models.Multimedium;
import com.openclassrooms.mynews.Models.Result;
import com.openclassrooms.mynews.R;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.fragment_main_item_section) TextView sectionTv;
    @BindView(R.id.fragment_main_item_title) TextView titleTv;
    @BindView(R.id.fragment_main_item_date) TextView dateTv;
    @BindView(R.id.fragment_main_item_image) ImageView imageIv;

    ResultViewHolder(View itemView){
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateWithResult(Result result,  RequestManager glide, ResultAdapter.Listener callback){
        // Add an arrow head to the section of the article when it has a subsection
        if(!result.getSubsection().isEmpty())
        this.sectionTv.setText(result.getSection() + " > " + result.getSubsection());
        else
            this.sectionTv.setText(result.getSection());

        // Parsing the updated date of the article to a new format
        String dtStart = result.getUpdatedDate();
        dtStart = dtStart.replace("T"," ");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatR = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = format.parse(dtStart);
            dtStart = formatR.format(date);
            this.dateTv.setText(dtStart);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Retrieving the thumbnail picture of the article
        List<Multimedium> images = result.getMultimedia();
        for(int i=0; i<images.size();i++){
            Bitmap bitmap = null;
            try {
                int SDK_INT = android.os.Build.VERSION.SDK_INT;
                if (SDK_INT > 8) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                            .permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    bitmap = BitmapFactory.decodeStream((InputStream) new URL(images.get(1).getUrl()).getContent());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.imageIv.setImageBitmap(bitmap);
        }
        // Setting the title of the article
        this.titleTv.setText(result.getTitle());
        WeakReference<ResultAdapter.Listener> callbackWeakRef = new WeakReference<>(callback);
    }

}
