package ua.hanasaka.testestafeta.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

import ua.hanasaka.testestafeta.R;
import ua.hanasaka.testestafeta.model.data.Image;

/**
 * Created by Oleksandr Molodykh on 17.07.2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<Image> imageList = new ArrayList<>();
    private Context ctx;

    public void setImageList(List<Image> repoList) {
        this.imageList = repoList;

        notifyDataSetChanged();
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ctx = parent.getContext();
        View v = LayoutInflater.from(ctx).inflate(R.layout.image_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {
        Image image = imageList.get(position);
        Picasso.with(ctx).load(image.getDisplaySizes().get(0).getUri()).into(holder.imgView);
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgView;
        Target target;

        public ViewHolder(View itemView) {
            super(itemView);
            imgView = (ImageView) itemView.findViewById(R.id.img);
        }
    }
}
