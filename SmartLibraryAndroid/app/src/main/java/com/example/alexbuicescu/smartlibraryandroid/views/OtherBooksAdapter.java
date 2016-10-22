package com.example.alexbuicescu.smartlibraryandroid.views;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alexbuicescu.smartlibraryandroid.R;
import com.example.alexbuicescu.smartlibraryandroid.rest.responses.MainBooksResponse;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by alexbuicescu on Oct 22 - 2016.
 */
public class OtherBooksAdapter extends RecyclerView.Adapter<OtherBooksAdapter.ViewHolder> {

    private ArrayList<MainBooksResponse> items;

    public OtherBooksAdapter() {
        items = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_other_books_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(rootView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        ImageLoader.getInstance().displayImage(items.get(position).getBook().getCoverUrl(), holder.bookImageView);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setNewItems(ArrayList<MainBooksResponse> items) {
        if (items == null) {
            items = new ArrayList<>();
        }
        this.items = items;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView bookImageView;

        ViewHolder(View itemView) {
            super(itemView);
            bookImageView = (AppCompatImageView) itemView.findViewById(R.id.row_other_books_layout_imageview);
        }
    }
}
