package com.example.myapplication;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import com.example.myapplication.Class.MyAdapter;
import com.example.myapplication.Class.Notas;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class notas extends Fragment {

    private NotasViewModel mViewModel;



    public static notas newInstance() {
        return new notas();
    }

    //TextView textView;
    private Button button;
    private NotasViewModel notasViewModel;
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private FloatingActionButton floatingActionButton;
    private LiveData<List<Notas>> filterNotas;
    private List<Notas> allNotas;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_notas, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu,inflater);
        //getMenuInflater().inflate(R.menu.main_menu,menu);
        inflater.inflate(R.menu.main_menu,menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setMaxWidth(1000);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                String patten= s.trim();
                filterNotas.removeObservers(getViewLifecycleOwner());
                filterNotas=notasViewModel.findnotasWithPatten(patten);

                filterNotas.observe(getViewLifecycleOwner(), new Observer<List<Notas>>() {
                    @Override
                    public void onChanged(List<Notas> notas) {
                        allNotas=notas;
                        int temp = myAdapter.getItemCount();
                        myAdapter.setAllNotas(notas);
                        if (temp!=notas.size()){
                            myAdapter.notifyDataSetChanged();
                        }
                    }
                });

                return true;
            }
        });
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        floatingActionButton=getView().findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Bundle
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_fragment_notas_to_addNotas);
            }
        });
        recyclerView=getView().findViewById(R.id.recycleViewNotas);
        myAdapter=new MyAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(myAdapter);
        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Notas notas2 = new Notas("click", "Word");
                notasViewModel.insertNotas(notas2);
            }
        });

        notasViewModel= new ViewModelProvider(this).get(NotasViewModel.class);
        filterNotas=notasViewModel.getAllNotasLive();
        filterNotas.observe(getViewLifecycleOwner(), new Observer<List<Notas>>() {
            @Override
            public void onChanged(List<Notas> notas) {
                allNotas=notas;
                myAdapter.setAllNotas(notas);

                myAdapter.notifyDataSetChanged();
            }
        });
        //deslizamiento
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.START | ItemTouchHelper.END) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final Notas notasToDelete= allNotas.get(viewHolder.getAdapterPosition());
                notasViewModel.deleteNotas(notasToDelete);
                Snackbar.make(requireActivity().findViewById(R.id.recycleViewNotas),"Delete a Nota", BaseTransientBottomBar.LENGTH_SHORT )
                        .setAction("Deshacer", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                notasViewModel.insertNotas(notasToDelete);
                            }
                        }).show();
            }
            Drawable icon= ContextCompat.getDrawable(requireActivity(),R.drawable.ic_baseline_delete_forever_24);
            Drawable background = new ColorDrawable(Color.LTGRAY);
            //imagen de deslizamiento
            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState,
                                    boolean isCurrentlyActive) {
                super.onChildDraw(c,recyclerView,viewHolder,dX,dY,actionState,isCurrentlyActive);
                View itemView = viewHolder.itemView;
                int iconMargin = (itemView.getHeight()-icon.getIntrinsicHeight())/2;
                int iconLeft,iconRight,iconTop,iconBottom;
                int backTop,backButtom,backLeft,backRight;
                backTop = itemView.getTop();
                backButtom=itemView.getBottom();
                iconTop=itemView.getTop()+(itemView.getHeight()-icon.getIntrinsicHeight())/2;
                iconBottom=iconTop+icon.getIntrinsicHeight();
                if(dX>0){
                    backLeft=itemView.getLeft();
                    backRight=itemView.getLeft()+(int) dX;
                    background.setBounds(backLeft,backTop,backRight,backButtom);
                    iconLeft = itemView.getLeft()+iconMargin;
                    iconRight= iconLeft+icon.getIntrinsicWidth();
                    icon.setBounds(iconLeft,iconTop,iconRight,iconBottom);
                }else if(dX<0){
                    backRight = itemView.getRight();
                    backLeft=itemView.getRight()+(int) dX;
                    background.setBounds(backLeft,backTop,backRight,backButtom);
                    iconRight=itemView.getRight()-iconMargin;
                    iconLeft=iconRight-icon.getIntrinsicWidth();
                    icon.setBounds(iconLeft,iconTop,iconRight,iconBottom);
                }else{
                    background.setBounds(0,0,0,0);
                    icon.setBounds(0,0,0,0);
                }
                background.draw(c);
                icon.draw(c);

            }

        }).attachToRecyclerView(recyclerView);



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.clearData:
                AlertDialog.Builder builder= new AlertDialog.Builder(requireActivity());
                builder.setTitle("clearData");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        notasViewModel.deleteAllNotas();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.create();
                builder.show();
                break;
            case R.id.autores:
                NavController navController = Navigation.findNavController(getView());
                navController.navigate(R.id.autores);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}