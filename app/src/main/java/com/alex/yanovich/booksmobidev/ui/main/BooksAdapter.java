package com.alex.yanovich.booksmobidev.ui.main;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alex.yanovich.booksmobidev.R;
import com.alex.yanovich.booksmobidev.data.model.Item;
import com.alex.yanovich.booksmobidev.injection.ApplicationContext;
import com.alex.yanovich.booksmobidev.util.SundryUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ItemsViewHolder> {

    private List<Item> mItems;
    private Context mContext;

    @Inject
    public BooksAdapter(@ApplicationContext Context context) {
        mItems = new ArrayList<>();
        mContext = context;
    }

    public void setItems(List<Item> items) {
        mItems = items;
    }

    @Override
    public ItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyler_view_item, parent, false);
        return new ItemsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemsViewHolder holder, int position) {
        Item item = mItems.get(position);
        Uri uri;
        try {
            uri = Uri.parse(item.getVolumeInfo().getImageLinks().getSmallThumbnail());
        }catch (NullPointerException e){
           uri = SundryUtils.getUriToDrawable(mContext, R.drawable.ic_local_florist);
        }

        Glide.with(mContext).load(uri).centerCrop().diskCacheStrategy(DiskCacheStrategy.SOURCE).error(R.drawable.ic_local_florist).into(holder.imageView);

        holder.nameTextView.setText(item.getVolumeInfo().getTitle());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ItemsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.book_photo)
        ImageView imageView;

        @BindView(R.id.book_name)
        TextView nameTextView;


        public ItemsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
