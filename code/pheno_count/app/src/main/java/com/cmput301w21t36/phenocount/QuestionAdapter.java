package com.cmput301w21t36.phenocount;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class QuestionAdapter extends ArrayAdapter<Question> {
    private ArrayList<Question> questions;
    private Context context;


    public QuestionAdapter(Context context, ArrayList<Question> questions) {
        super(context,0,questions);
        this.questions= questions;
        this.context = context;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.content_question,parent,false);
        }

        Question question = questions.get(position);

        TextView queText = view.findViewById(R.id.question_text_view_in_list);

        queText.setText(question.getText());


        return view;
    }
}
