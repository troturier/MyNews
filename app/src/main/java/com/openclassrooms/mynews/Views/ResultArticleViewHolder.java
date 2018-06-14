package com.openclassrooms.mynews.Views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.openclassrooms.mynews.Models.Article;
import com.openclassrooms.mynews.Models.Multimedium;
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
public class ResultArticleViewHolder extends RecyclerView.ViewHolder{

    // FOR DESIGN
    @BindView(R.id.fragment_search_item_section) TextView sectionTv;
    @BindView(R.id.fragment_search_item_title) TextView titleTv;
    @BindView(R.id.fragment_search_item_date) TextView dateTv;
    @BindView(R.id.fragment_search_item_image) ImageView imageIv;

    private Context context =  MyApplication.getAppContext();
    private Drawable nytimeslogo = context.getResources().getDrawable(R.drawable.ic_nytimes_logo);
    private List<Multimedium> images;
    private String section;

    ResultArticleViewHolder(View itemView){
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    /**
     * Update the UI with new results
     * @param article Article object
     * @param glide Glide object for loading images
     * @param callback ArticleAdapter.Listener
     */
    public void updateWithResult(Article article, RequestManager glide, ResultArticleAdapter.Listener callback){
        // Add an arrow head to the section of the article when it has a subsection
        section = article.getSectionName();
        if (TextUtils.isEmpty(section)){
            section = article.getNewDesk();
            if(section == null || section.equals("None"))
                section = "News";
        }
        this.sectionTv.setText(section);

        // Parsing the updated date of the article to a new format and setting it the corresponding TextView
        String dtStart = article.getPubDate();
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

        images = article.getMultimedia();

        // If the article does not have any thumbnail : set a default thumbnail in the ImageView
        if (images.isEmpty() || images == null) {
            this.imageIv.setImageDrawable(nytimeslogo);
        }
        // Else set the corresponding thumbnail in the ImageView
        else {
            for (int i = 0; i < images.size(); i++) {
                if (images.get(i).getSubtype().equals("thumbnail")){
                    String url = "https://static01.nyt.com/" + images.get(i).getUrl();
                    glide.load(url).into(this.imageIv);
                }
            }
        }
        // Setting the title of the article
        this.titleTv.setText(article.getSnippet());
        WeakReference<ResultArticleAdapter.Listener> callbackWeakRef = new WeakReference<>(callback);
    }

}
