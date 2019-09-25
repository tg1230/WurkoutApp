package com.example.wurkout.Custom_RecyclerView;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.wurkout.Custom_RecyclerView.Custom_ItemTouchHelper.ItemTouchHelperAdapter;
import com.example.wurkout.Custom_RecyclerView.Custom_ItemTouchHelper.ItemTouchHelperViewHolder;
import com.example.wurkout.Custom_RecyclerView.Custom_ItemTouchHelper.OnStartDragListener;
import com.example.wurkout.R;

import java.util.ArrayList;
import java.util.Collections;

// adapters connect the ui (file_paths files) with data (user or predefined) in lists
// and sets up list functionality
public class RecyclerView_Adapter extends RecyclerView.Adapter<RecyclerView_Adapter.zViewHolder>
        implements ItemTouchHelperAdapter {
    private ArrayList<RecyclerView_Items> recyclerView_items;

    private OnItemClickListener zListener;

    private final OnStartDragListener mDragStartListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDeleteClick(int position);
    }

    public void OnItemClickListener(OnItemClickListener listener) {
        zListener = listener;
    }

    public class zViewHolder extends RecyclerView.ViewHolder implements
    ItemTouchHelperViewHolder {
        private ImageView zImageView1;
        private ImageView zRemoveIcon;

        private TextView zTextView1;
        private TextView zTextView2;
        private CardView handleView;

        public zViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            zImageView1 = itemView.findViewById(R.id.imageView1);
            zRemoveIcon = itemView.findViewById(R.id.imageView2);
            zTextView1 = itemView.findViewById(R.id.textView1);
            zTextView2 = itemView.findViewById(R.id.textView2);
            handleView = itemView.findViewById(R.id.itemHandleView);

            itemView.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            handleView.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            zRemoveIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });


        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }

    }

    // list object
    public RecyclerView_Adapter(ArrayList<RecyclerView_Items> z_recyclerView_items, OnStartDragListener dragStartListener) {
        recyclerView_items = z_recyclerView_items;
        mDragStartListener = dragStartListener;

    }

    @NonNull
    @Override
    public zViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // builds view object from file_paths file (list_items)
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent, false);
        zViewHolder zvh = new zViewHolder(v, zListener);
        return zvh;
    }

    // recycle views that aren't on screen
    @Override
    public void onBindViewHolder(@NonNull final zViewHolder holder, int position) {
        RecyclerView_Items currentItem = recyclerView_items.get(position);

        holder.zImageView1.setImageResource(currentItem.getImage1());
        holder.zRemoveIcon.setImageResource(currentItem.getImage2());
        holder.zTextView1.setText(currentItem.getText1());
        holder.zTextView2.setText(currentItem.getText2());

        // Start a drag whenever the handle view is touched
        holder.handleView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                mDragStartListener.onStartDrag(holder);
                return true;
            }

        });

    }

    @Override
    public void onItemDismiss(int position) {
        recyclerView_items.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(recyclerView_items, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public int getItemCount() {
        return recyclerView_items.size();
    }

}
