package com.example.android.miwok;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<word> {

    private static final String LOG_TAG = WordAdapter.class.getSimpleName();

   private int color;
    public WordAdapter(Activity context, ArrayList<word> words,int colorId) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, words);
        color=colorId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        word currentPair = getItem(position);
        TextView mivokTextView = (TextView) listItemView.findViewById(R.id.mivok);
        mivokTextView.setText(currentPair.getMivokTranslation());
        TextView englishTextView = (TextView) listItemView.findViewById(R.id.english);
        englishTextView.setText(currentPair.getEnglishTranslation());

        View textContainer=listItemView.findViewById((R.id.text_container));
        int color1=ContextCompat.getColor(getContext(),color);
        textContainer.setBackgroundColor(color1);
        boolean HasImage=currentPair.hasImage();

        ImageView imgview = (ImageView) listItemView.findViewById(R.id.image);
        if(HasImage==true){
        imgview.setImageResource(currentPair.getResourceId());
        imgview.setVisibility(View.VISIBLE);}
        else
        {
            imgview.setVisibility(View.GONE);
        }

        return listItemView;
    }

}