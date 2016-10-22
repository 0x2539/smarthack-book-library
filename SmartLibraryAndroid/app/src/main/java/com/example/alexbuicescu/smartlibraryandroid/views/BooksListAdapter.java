package com.example.alexbuicescu.smartlibraryandroid.views;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.alexbuicescu.smartlibraryandroid.R;
import com.example.alexbuicescu.smartlibraryandroid.rest.responses.MainBooksResponse;
import com.example.alexbuicescu.smartlibraryandroid.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexbuicescu on Oct 22 - 2016.
 */
public class BooksListAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;

    private ArrayList<MainBooksResponse> currentItems;

    private Context context;

    public BooksListAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        if(currentItems != null) {
            return currentItems.size();
        }
        else{
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return currentItems.get(position);
    }

    public ArrayList<MainBooksResponse> getCurrentItems() {
        return currentItems;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.row_book_layout, parent, false);

            holder = new ViewHolder();
            holder.titleTextView = (TextView) convertView.findViewById(R.id.row_book_layout_title_textview);
            holder.authorTextView = (TextView) convertView.findViewById(R.id.row_book_layout_author_textview);
            holder.yearGenreTextView = (TextView) convertView.findViewById(R.id.row_book_layout_year_genre_textview);
            holder.dueSoonTextView = (TextView) convertView.findViewById(R.id.row_book_layout_due_soon_textview);
            holder.readTextView = (TextView) convertView.findViewById(R.id.row_book_layout_read_textview);
            holder.coverImageView = (AppCompatImageView) convertView.findViewById(R.id.row_book_layout_cover_imageview);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.titleTextView.setText(currentItems.get(position).getBook().getTitle());
        holder.authorTextView.setText(getContext().getResources().getString(R.string.book_author, currentItems.get(position).getBook().getAuthor()));
        holder.yearGenreTextView.setText(
                getContext().getResources().getString(
                        R.string.book_year_genre,
                        currentItems.get(position).getBook().getReleaseDate().substring(0, 4),
                        currentItems.get(position).getBook().getGenre().toString()
                )
        );
        ImageLoader.getInstance().displayImage(currentItems.get(position).getBook().getCoverUrl(), holder.coverImageView);

        holder.dueSoonTextView.setVisibility(View.GONE);
        holder.readTextView.setVisibility(View.GONE);

        if (currentItems.get(position).getBook().isDueSoon()) {
            holder.dueSoonTextView.setVisibility(View.VISIBLE);
        }
        if (Utils.isDateSoon(currentItems.get(position).getBook().getReturnDate())) {
            holder.readTextView.setVisibility(View.VISIBLE);
        }
//        ImageLoader.getInstance().displayImage("http://www.proprofs.com/quiz-school/topic_images/p19c7ebf8va58mc51mdqteg14453.jpg", holder.coverImageView);

        return convertView;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setCurrentItems(ArrayList<MainBooksResponse> items)
    {
        this.currentItems = items;
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        TextView titleTextView;
        TextView authorTextView;
        TextView yearGenreTextView;
        TextView dueSoonTextView;
        TextView readTextView;
        AppCompatImageView coverImageView;
    }
}
