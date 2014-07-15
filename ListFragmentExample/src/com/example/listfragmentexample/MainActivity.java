package com.example.listfragmentexample;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListFragment {
	String [] presidents = {"aaaa","bbbbb","ccccc","ddddd"};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle saveInstanceState){
		return inflater.inflate(R.layout.fragment1, container,false);
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,presidents));
        
    }


   public void onListItemClick(ListView parent, View v, int position ,long id)
   {
	   Toast.makeText(getActivity(), "You have selected " + presidents[position],Toast.LENGTH_SHORT).show();
   }
    
}
