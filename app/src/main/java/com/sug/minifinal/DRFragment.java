package com.sug.minifinal;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class DRFragment extends Fragment implements View.OnClickListener{



    public DRFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dr, null);
        Button b = view.findViewById(R.id.button7);
        Button b1 = view.findViewById(R.id.button8);

        b.setOnClickListener(this);
        b1.setOnClickListener(this);



        return view;
    }

    private int getFragCount(){
        return getFragmentManager().getBackStackEntryCount();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.button7:
                don();
                break;
            case R.id.button8:
                rec();
                break;
            default:
                break;
        }
    }
    public void don()
    {
        Fragment x=new Donate();
        FragmentTransaction trans=getFragmentManager().beginTransaction();
        trans.replace(R.id.fr1,x, Integer.toString(getFragCount()));
        trans.addToBackStack("DR");
        trans.commit();
    }

    private Fragment getFragAt(int index){
        return getFragCount()>0? getFragmentManager().findFragmentByTag(Integer.toString(index)) : null;
    }
    public void rec()
    {
        Fragment adcfrag=new R_Slot_12();
        FragmentTransaction trans=getFragmentManager().beginTransaction();
        trans.replace(R.id.fr1,adcfrag);
        trans.addToBackStack("DR");
        trans.commit();
    }

}
