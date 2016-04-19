package croft.portfolio.NewsReader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import croft.portfolio.NewsReader.models.Article;
import croft.portfolio.R;
import croft.portfolio.other.SerialBitmap;

/**
 * Created by Michaels on 17/4/2016.
 */
public class NewsAdapter extends BaseAdapter {

    private ArrayList<Article> articles;
    private Context context;

    public NewsAdapter (Context context, ArrayList<Article> articles){
        this.context = context;
        this.articles = articles;
    }

    //THis class is connected to the article list item - it fills that view and returns it to be put into the list
    public static class ViewHolder {

        ImageView icon;
        TextView headline;
        TextView creator;
        TextView date;
        //dont need to show link here, that is stored and retrieved when the column is clicked
        TextView category;

    }

    public View getView(int i, View view, ViewGroup viewGroup){
        ViewHolder holder;

        if (view == null){
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.news_item_list, null);

            holder = new ViewHolder();

            holder.icon = (ImageView) view.findViewById(R.id.iconArticle);
            holder.headline = (TextView) view.findViewById(R.id.headlineText);
            holder.creator = (TextView) view.findViewById(R.id.creatorText);
            holder.date = (TextView) view.findViewById(R.id.dateText);
            holder.category = (TextView) view.findViewById(R.id.categoryText);

            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        SerialBitmap icon = articles.get(i).getIcon();
        String headline = articles.get(i).getHeadline();
        String creator = articles.get(i).getCreator();
        String date   = articles.get(i).getPublishDate().toString();
        String category = articles.get(i).getCategory();

        holder.icon.setImageBitmap(icon.getBitmap());
        holder.headline.setText(headline);
        holder.creator.setText(creator);
        holder.date.setText(date);
        holder.category.setText(category);

        return view;

    }



    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
