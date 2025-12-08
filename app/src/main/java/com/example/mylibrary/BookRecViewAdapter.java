package com.example.mylibrary;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionManager;

import com.bumptech.glide.Glide;
import com.example.mylibrary.data.model.Book;
import com.example.mylibrary.data.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;

public class BookRecViewAdapter extends RecyclerView.Adapter<BookRecViewAdapter.ViewHolder>{
    private static final String TAG = "BookRecViewAdapter";

    private List<Book> books = new ArrayList<>();

    private Context mContext;
    private String currentParent;
    private BookRepository bookRepository;

    public BookRecViewAdapter(Context context, String parent, BookRepository bookRepository){
        this.mContext = context;
        this.currentParent = parent;
        this.bookRepository = bookRepository;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_book,
                parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: Called");
        holder.txtName.setText(books.get(position).getName());
        Glide.with(mContext)
                .asBitmap()
                .load(books.get(position).getImageUrl())
                .into((holder.imgBook));

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, BookActivity.class);
                intent.putExtra("bookId",books.get(position).getId());
                mContext.startActivity(intent);
                Log.d(TAG, "onClick: "+intent);
            }
        });


        holder.txtAuthor.setText(books.get(position).getAuthor());
        holder.txtShortDesc.setText(books.get(position).getShortDesc());
        if(books.get(position).isExpanded()){
            TransitionManager.beginDelayedTransition(holder.parent);
            holder.notCollapseRelLayout.setVisibility(VISIBLE);
            holder.btnDownArrow.setVisibility(GONE);
        }else{
            TransitionManager.beginDelayedTransition(holder.parent);
            holder.notCollapseRelLayout.setVisibility(GONE);
            holder.btnDownArrow.setVisibility(VISIBLE);
        }

        if(currentParent == "AllBooks"){
            holder.btnDelete.setVisibility(VISIBLE);
            holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: deleted the book in allbooks");
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setMessage("Are you sure you want to delete " + books.get(position).getName());
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Log.d(TAG, "onClick: deleted the book in allbooks");
                            Book book = books.get(holder.getBindingAdapterPosition());
                            Log.d(TAG, book.getName().toString());
                            bookRepository.deleteBook(book);
                            Toast.makeText(mContext, "The book selected has removed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

                    builder.show();

                }
            });

        }else if(currentParent == "CurrentReads"){
            holder.btnDelete.setVisibility(VISIBLE);
            holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setMessage("Are you sure you want to delete " + books.get(position).getName());
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Book book = books.get(holder.getBindingAdapterPosition());
                            bookRepository.deleteCurrentReads(book);
                            notifyItemChanged(holder.getBindingAdapterPosition());
                            Toast.makeText(mContext, "The book selected has removed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

                    builder.create().show();
                }
            });


        }else if (currentParent == "FavoriteBooks"){
        holder.btnDelete.setVisibility(VISIBLE);
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("Are you sure you want to delete " + books.get(position).getName());
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Book book = books.get(holder.getBindingAdapterPosition());
                        // TO DO:Utils.getInstance().removeFavorites(book);
                        notifyItemChanged(holder.getBindingAdapterPosition());
                        Toast.makeText(mContext, "The book selected has removed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.create().show();
            }
        });


    }else if (currentParent == "Wishlist"){
            holder.btnDelete.setVisibility(VISIBLE);
            holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("Are you sure you want to delete " + books.get(position).getName());
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Book book = books.get(holder.getBindingAdapterPosition());
                        // TO DO:Utils.getInstance().removeWantToReadBooks(book);
                        notifyItemChanged(holder.getBindingAdapterPosition());
                        Toast.makeText(mContext, "The book selected has removed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.create().show();
            }
        });
    }else if (currentParent == "AlreadyReadBooks"){
            holder.btnDelete.setVisibility(VISIBLE);
            holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("Are you sure you want to delete " + books.get(position).getName());
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Book book = books.get(holder.getBindingAdapterPosition());
                        // TO DO:Utils.getInstance().removeToAlreadyRead(book);
                        notifyItemChanged(holder.getBindingAdapterPosition());
                        Toast.makeText(mContext, "The book selected has removed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.create().show();

            }
        });
    }



    }

    @Override
    public int getItemCount() {
        return books.size();
    }


    public void setBooks(List<Book> books) {
        this.books = books;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CardView parent;
        private ImageView imgBook, btnDownArrow, btnUpArrow, btnDelete;
        private TextView txtName, txtAuthor,txtShortDesc;
        private RelativeLayout notCollapseRelLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            imgBook = itemView.findViewById(R.id.imgBook);
            txtName = itemView.findViewById(R.id.txtName);

            btnDownArrow = itemView.findViewById(R.id.btnDownArrow);
            btnUpArrow = itemView.findViewById(R.id.btnUpArrow);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            txtAuthor = itemView.findViewById(R.id.txtAuthorLbl);
            txtShortDesc = itemView.findViewById(R.id.txtShortDesc);
            notCollapseRelLayout = itemView.findViewById(R.id.notCollapseRelLayout);


            btnDownArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Book book = books.get(getBindingAdapterPosition());
                    book.setExpanded(!book.isExpanded());
                    notifyItemChanged(getBindingAdapterPosition());
                }
            });


            btnUpArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Book book = books.get(getBindingAdapterPosition());
                    book.setExpanded(!book.isExpanded());
                    notifyItemChanged(getBindingAdapterPosition());
                }

            });

        }
    }
}
