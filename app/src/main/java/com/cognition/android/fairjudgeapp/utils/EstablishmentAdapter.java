package com.cognition.android.fairjudgeapp.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.PopupMenu;

import com.cognition.android.fairjudgeapp.R;
import com.cognition.android.fairjudgeapp.models.DatabaseHelper;
import com.cognition.android.fairjudgeapp.models.Establishment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EstablishmentAdapter extends RecyclerView.Adapter<EstablishmentAdapter.EstablishmentViewHolder> implements Filterable {

    private Context mContext;
    private List<Establishment> establishmentList;
    private List<Establishment> establishmentListFiltered;

    public EstablishmentAdapter(Context context, List<Establishment> establishmentList) {
        this.mContext = context;
        this.establishmentListFiltered = establishmentList;
        this.establishmentList = establishmentList;
    }

    class EstablishmentViewHolder extends RecyclerView.ViewHolder {

        CardView cardItem;
        AppCompatTextView txtEstablishmentName;
        AppCompatImageButton btnOptions;
        AppCompatTextView txtEstablishmentType, txtFoodType, txtLocation;
        AppCompatImageView imgStar1, imgStar2, imgStar3, imgStar4, imgStar5;
        AppCompatTextView txtReviewerId, txtTime;

        EstablishmentViewHolder(View itemView) {
            super(itemView);

            cardItem = itemView.findViewById(R.id.cardItem);
            txtEstablishmentName = itemView.findViewById(R.id.txtEstablishmentName);
            btnOptions = itemView.findViewById(R.id.btnOptions);
            txtEstablishmentType = itemView.findViewById(R.id.txtEstablishmentType);
            txtFoodType = itemView.findViewById(R.id.txtFoodType);
            txtLocation = itemView.findViewById(R.id.txtLocation);
            imgStar1 = itemView.findViewById(R.id.imgStar1);
            imgStar2 = itemView.findViewById(R.id.imgStar2);
            imgStar3 = itemView.findViewById(R.id.imgStar3);
            imgStar4 = itemView.findViewById(R.id.imgStar4);
            imgStar5 = itemView.findViewById(R.id.imgStar5);
            txtReviewerId = itemView.findViewById(R.id.txtReviewerId);
            txtTime = itemView.findViewById(R.id.txtTime);
        }
    }

    @NonNull
    @Override
    public EstablishmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_establishment, parent, false);

        return new EstablishmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final EstablishmentViewHolder holder, int position) {
        final Establishment establishment = establishmentListFiltered.get(position);

        holder.txtEstablishmentName.setText(establishment.getEstablishmentName());

        holder.btnOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(mContext, v);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.menu_establishment_item, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.deleteItem:
                                new DatabaseHelper(mContext).deleteEstablishment(establishment.getId());
                                establishmentListFiltered.remove(holder.getAdapterPosition());
                                notifyItemRemoved(holder.getAdapterPosition());
                                return true;

                            default:
                                return false;
                        }
                    }
                });
                popupMenu.show();
            }
        });

        holder.txtEstablishmentType.setText(establishment.getEstablishmentType());

        if (establishment.getFoodType() != null)
            holder.txtFoodType.setText(establishment.getFoodType());
        else
            holder.txtFoodType.setText("");

        if (establishment.getLocation() != null)
            holder.txtLocation.setText(establishment.getLocation());
        else
            holder.txtLocation.setText("");

        setRating(holder, establishment.getRating());
        holder.txtReviewerId.setText(establishment.getReviewerId());
        holder.txtTime.setText(formatDate(establishment.getTimeStamp()));
    }

    @Override
    public int getItemCount() {
        return establishmentListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String searchText = constraint.toString().trim().toLowerCase();

                if (searchText.isEmpty()) {
                    establishmentListFiltered = establishmentList;
                } else {
                    List<Establishment> newEstablishmentList = new ArrayList<>();
                    for (Establishment establishment : establishmentList)
                        if (establishment.getEstablishmentName().toLowerCase().contains(searchText)
                                || establishment.getEstablishmentType().toLowerCase().contains(searchText)
                                || (establishment.getFoodType() != null && establishment.getFoodType().toLowerCase().contains(searchText))
                                || (establishment.getLocation() != null && establishment.getLocation().toLowerCase().contains(searchText))
                                || establishment.getReviewerId().toLowerCase().contains(searchText))
                            newEstablishmentList.add(establishment);

                    establishmentListFiltered = newEstablishmentList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = establishmentListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                establishmentListFiltered = (ArrayList<Establishment>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    private void setRating(EstablishmentViewHolder holder, int rating) {
        switch (rating) {
            case 5:
                holder.imgStar1.setImageResource(R.drawable.ic_star_gold_48dp);
                holder.imgStar2.setImageResource(R.drawable.ic_star_gold_48dp);
                holder.imgStar3.setImageResource(R.drawable.ic_star_gold_48dp);
                holder.imgStar4.setImageResource(R.drawable.ic_star_gold_48dp);
                holder.imgStar5.setImageResource(R.drawable.ic_star_gold_48dp);
                break;

            case 4:
                holder.imgStar1.setImageResource(R.drawable.ic_star_gold_48dp);
                holder.imgStar2.setImageResource(R.drawable.ic_star_gold_48dp);
                holder.imgStar3.setImageResource(R.drawable.ic_star_gold_48dp);
                holder.imgStar4.setImageResource(R.drawable.ic_star_gold_48dp);
                holder.imgStar5.setImageResource(R.drawable.ic_star_border_gold_48dp);
                break;

            case 3:
                holder.imgStar1.setImageResource(R.drawable.ic_star_gold_48dp);
                holder.imgStar2.setImageResource(R.drawable.ic_star_gold_48dp);
                holder.imgStar3.setImageResource(R.drawable.ic_star_gold_48dp);
                holder.imgStar4.setImageResource(R.drawable.ic_star_border_gold_48dp);
                holder.imgStar5.setImageResource(R.drawable.ic_star_border_gold_48dp);
                break;

            case 2:
                holder.imgStar1.setImageResource(R.drawable.ic_star_gold_48dp);
                holder.imgStar2.setImageResource(R.drawable.ic_star_gold_48dp);
                holder.imgStar3.setImageResource(R.drawable.ic_star_border_gold_48dp);
                holder.imgStar4.setImageResource(R.drawable.ic_star_border_gold_48dp);
                holder.imgStar5.setImageResource(R.drawable.ic_star_border_gold_48dp);
                break;

            case 1:
                holder.imgStar1.setImageResource(R.drawable.ic_star_gold_48dp);
                holder.imgStar2.setImageResource(R.drawable.ic_star_border_gold_48dp);
                holder.imgStar3.setImageResource(R.drawable.ic_star_border_gold_48dp);
                holder.imgStar4.setImageResource(R.drawable.ic_star_border_gold_48dp);
                holder.imgStar5.setImageResource(R.drawable.ic_star_border_gold_48dp);
                break;

            case 0:
                holder.imgStar1.setImageResource(R.drawable.ic_star_border_gold_48dp);
                holder.imgStar2.setImageResource(R.drawable.ic_star_border_gold_48dp);
                holder.imgStar3.setImageResource(R.drawable.ic_star_border_gold_48dp);
                holder.imgStar4.setImageResource(R.drawable.ic_star_border_gold_48dp);
                holder.imgStar5.setImageResource(R.drawable.ic_star_border_gold_48dp);
                break;
        }
    }

    private String formatDate(String dateStr) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            Date date = fmt.parse(dateStr);
            SimpleDateFormat fmtOut = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
            return fmtOut.format(date);
        } catch (ParseException e) {
            Log.w("FairJudgeApp", e);
        }

        return "";
    }

}
