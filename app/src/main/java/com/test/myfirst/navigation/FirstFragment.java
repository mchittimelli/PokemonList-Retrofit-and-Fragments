package com.test.myfirst.navigation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FirstFragment extends Fragment {
    PokemonRecycleAdapter adapter;
    List poke;
    int position;
    ArrayList pokeArray;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        Call<List<Pokemon>> call = service.getPokemons();

        call.enqueue(new Callback<List<Pokemon>>() {
            @Override
            public void onResponse(Call<List<Pokemon>> call, Response<List<Pokemon>> response) {
                final List<Pokemon> poke = response.body();

               pokeArray = new ArrayList<>(poke);

               // generateData(pokeArray);
                adapter=new PokemonRecycleAdapter(pokeArray,getActivity().getApplicationContext());
                @SuppressLint("WrongConstant") LinearLayoutManager linearLayout=new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.VERTICAL,false);
                RecyclerView recyclerView=getView().findViewById(R.id.recycle_poke);
                recyclerView.setLayoutManager(linearLayout);
                recyclerView.setAdapter(adapter);/*
                RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) getView().getTag();
                position = viewHolder.getAdapterPosition();

                poke.get(position).getImage();
                poke.get(position).getHeight();
                poke.get(position).getWeight();
                poke.get(position).getType();
                poke.get(position).getAbility();
                poke.get(position).getDescription();*/



                adapter.setOnItemClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
                          position = viewHolder.getAdapterPosition();
                         // Toast.makeText(getActivity().getApplicationContext(), poke.get(position).getName(), Toast.LENGTH_LONG).show();
                       //Navigation.createNavigateOnClickListener(R.id.action_firstFragment_to_pokemoninfo);
                         Bundle pokemonData=new Bundle();
                         pokemonData.putString("Name",poke.get(position).getName());
                         pokemonData.putString("Image",poke.get(position).getImage());
                         pokemonData.putString("Height",poke.get(position).getHeight());
                         pokemonData.putString("Weight",poke.get(position).getWeight());
                         pokemonData.putString("Ability",poke.get(position).getAbility());
                         pokemonData.putString("Type",poke.get(position).getType());
                         pokemonData.putString("Description",poke.get(position).getDescription());
                            Navigation.findNavController(view).navigate(R.id.action_firstFragment_to_pokemoninfo,pokemonData);
                         //adapter.setOnItemClickListener(Navigation.createNavigateOnClickListener(R.id.action_firstFragment_to_pokemoninfo,pokemonData));

                     }
                 });

            }

            @Override
            public void onFailure(Call<List<Pokemon>>  call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), "Try it later..!", Toast.LENGTH_LONG).show();
            }

        });


    return inflater.inflate(R.layout.fragment_first, container, false);
    }


    /*public void generateData(ArrayList<Pokemon> pokemonsList){

        *//*   ArrayList<Pokemon> pokes=(ArrayList<Pokemon>) pokemonsList;*//*
        adapter=new PokemonRecycleAdapter(pokemonsList,getActivity().getApplicationContext());
        @SuppressLint("WrongConstant") LinearLayoutManager linearLayout=new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        RecyclerView recyclerView=getView().findViewById(R.id.recycle_poke);
        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setAdapter(adapter);
    //adapter.setOnItemClickListener(onMyItemClick);
   // Bundle bundle=new Bundle();
        //RecyclerView.ViewHolder myview= (RecyclerView.ViewHolder) getView().getTag();
       // int position=myview.getAdapterPosition();
        //pokeArray.get(position);
        // RecyclerView.ViewHolder viewHolder=(RecyclerView.ViewHolder) view.getTag();
     // adapter.setOnItemClickListener(Navigation.createNavigateOnClickListener(R.id.action_firstFragment_to_pokemoninfo));

    }*/
}
