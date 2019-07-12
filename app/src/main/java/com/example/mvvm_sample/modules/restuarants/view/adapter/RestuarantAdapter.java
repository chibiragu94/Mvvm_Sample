package com.example.mvvm_sample.modules.restuarants.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvm_sample.R;
import com.example.mvvm_sample.databinding.ItemRestuarantBinding;
import com.example.mvvm_sample.modules.restuarants.data.model.RestuarantDb;
import com.example.mvvm_sample.modules.restuarants.listener.RestuarantItemClickListener;

import java.util.List;


/**
 * @author chibi_ragu
 * RestuarantAdapter holds the restuarant itemview and pagination loading views
 */
public class RestuarantAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements RestuarantItemClickListener {

    @Override
    public void onRestuarantClicked(View view, int restuarantId) {
        Toast.makeText(view.getContext(), "restuarantId :" + restuarantId, Toast.LENGTH_SHORT).show();
    }


    // View Types
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private List<RestuarantDb> restuarantDbList;

    public RestuarantAdapter(List<RestuarantDb> restuarantDbList) {
        this.restuarantDbList = restuarantDbList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == ITEM) {
            ItemRestuarantBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_restuarant,
                    viewGroup, false);
            return new RestuarantAdapter.Holder(binding);
        } else {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            View viewLoading = inflater.inflate(R.layout.item_pagination_loading, viewGroup, false);
            return new LoadingViewHolder(viewLoading);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder.getItemViewType() == ITEM) {
            Holder holder1 = (Holder) holder;
            RestuarantDb restuarantDb = restuarantDbList.get(position);
            holder1.bindings.setRestuarant(restuarantDb);
            holder1.bindings.setItemClickListener(this);
            holder1.bindings.tvCost.setText("$ " + restuarantDbList.get(position).getCostForTwo() + " For two");
        } else {
            LoadingViewHolder loadinHolder = (LoadingViewHolder) holder;
            loadinHolder.loadmoreProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return restuarantDbList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == restuarantDbList.size() - 1) {
            return LOADING;
        } else {
            return ITEM;
        }
    }

    // Add pagination footer to the list as last item
    public void addLoadingFooter() {
        restuarantDbList.add(new RestuarantDb());
        notifyItemInserted(restuarantDbList.size() - 1);
    }

    // Remove last item in the list if the RestuarantDb is NULL
    public void removeLoadingFooter() {
        if (restuarantDbList.size() > 1) {
            int position = restuarantDbList.size() - 1;

            RestuarantDb restuarantDb = getItem(position);

            if (restuarantDb != null && restuarantDb.getAddress() == null) {
                restuarantDbList.remove(position);
                notifyItemRemoved(position);
            }
        }
    }



    private RestuarantDb getItem(int position) {
        return restuarantDbList.get(position);
    }



    /*
    * Holder class holds the itemview of all restuarant
    * */
    class Holder extends RecyclerView.ViewHolder {
        ItemRestuarantBinding bindings;

        Holder(ItemRestuarantBinding binding) {
            super(binding.getRoot());
            bindings = binding;
        }
    }

    /*
    * LoadingViewHolder holds the item view of pagination loading
    * */
    class LoadingViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar loadmoreProgressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            loadmoreProgressBar = itemView.findViewById(R.id.load_more_progress);
        }
    }

}
