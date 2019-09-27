package pavankreddy.blogspot.com;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import pavankreddy.blogspot.com.models.TrendingRepo;


public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.RecyclerViewholder> {

    Context context;
    List<TrendingRepo> trendingRepos;

    public RecyclerviewAdapter(Context context, List<TrendingRepo> trendingRepos) {
        this.context = context;
        this.trendingRepos = trendingRepos;
    }

    @NonNull
    @Override
    public RecyclerViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_design,parent,false);
        return new RecyclerViewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewholder holder, int position) {
        holder.repo_name.setText(trendingRepos.get(position).getName());
        holder.repo_author.setText(trendingRepos.get(position).getAuthor());
        holder.repo_discription.setText(trendingRepos.get(position).getDescription());
        holder.repo_link.setText(trendingRepos.get(position).getUrl());
        Glide.with(context).load(trendingRepos.get(position).getAvatar()).into(holder.repo_avatar);
    }

    @Override
    public int getItemCount()
    {
        return trendingRepos.size();
    }

    public class RecyclerViewholder extends RecyclerView.ViewHolder{

        TextView repo_name,repo_author,repo_discription,repo_link;
        ImageView repo_avatar;

        public RecyclerViewholder(@NonNull View itemView) {
            super(itemView);

            repo_name = itemView.findViewById(R.id.repo_name);
            repo_author = itemView.findViewById(R.id.author_name);
            repo_discription = itemView.findViewById(R.id.repo_description);
            repo_avatar = itemView.findViewById(R.id.avatar);
            repo_discription = itemView.findViewById(R.id.repo_description);
            repo_link = itemView.findViewById(R.id.github_url);
        }
    }
}
