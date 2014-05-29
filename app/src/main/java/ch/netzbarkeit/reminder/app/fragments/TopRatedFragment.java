package ch.netzbarkeit.reminder.app.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.netzbarkeit.reminder.app.R;
import ch.netzbarkeit.reminder.db.DtoFactory;

public class TopRatedFragment extends Fragment {

    private DtoFactory dtoFactory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_top_rated, container, false);

        return rootView;
    }


}