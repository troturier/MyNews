package com.openclassrooms.mynews.Views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.openclassrooms.mynews.Models.Multimedium;
import com.openclassrooms.mynews.Models.Result;
import com.openclassrooms.mynews.R;
import com.openclassrooms.mynews.Utils.MyApplication;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Class used to create a new result item in the UI
 */
public class ResultViewHolder extends RecyclerView.ViewHolder{

    // FOR DESIGN
    @BindView(R.id.fragment_main_item_section) TextView sectionTv;
    @BindView(R.id.fragment_main_item_title) TextView titleTv;
    @BindView(R.id.fragment_main_item_date) TextView dateTv;
    @BindView(R.id.fragment_main_item_image) ImageView imageIv;

    private Context context =  MyApplication.getAppContext();
    private Drawable nytimeslogo = context.getResources().getDrawable(R.drawable.ic_nytimes_logo);

    ResultViewHolder(View itemView){
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    /**
     * Update the UI with new results
     * @param result Result object
     * @param glide Glide object for loading images
     * @param callback ResultAdapter.Listener
     */
    public void updateWithResult(Result result,  RequestManager glide, ResultAdapter.Listener callback){
        // Add an arrow head to the section of the article when it has a subsection
        if(!result.getSubsection().isEmpty())
        this.sectionTv.setText(result.getSection() + " > " + result.getSubsection());
        else
            this.sectionTv.setText(result.getSection());

        // Parsing the updated date of the article to a new format and setting it the corresponding TextView
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
        // If the article does not have any thumbnail : set a default thumbnail in the ImageView
        if(result.getMultimedia().isEmpty() || result.getMultimedia() == null){
            this.imageIv.setImageDrawable(nytimeslogo);
        }
        // Else set the corresponding thumbnail in the ImageView
        else {
            for(int i=0; i<images.size();i++) {
                glide.load(images.get(0).getUrl()).into(this.imageIv);
            }
        }

        // Setting the title of the article
        this.titleTv.setText(result.getTitle());
        WeakReference<ResultAdapter.Listener> callbackWeakRef = new WeakReference<>(callback);
    }

}
