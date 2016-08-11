package edu.pitt.pawslab.quizjet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import edu.pitt.pawslab.quizjet.R;
import edu.pitt.pawslab.quizjet.entity.Topic;

/**
 * Created by changsheng on 1/17/16.
 */
public class TopicAdapter extends ArrayAdapter<Topic> {


    public TopicAdapter(Context context, List<Topic> objects) {
        super(context, R.layout.item_topic, objects);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Topic topic = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_topic, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.topicname);
        // Populate the data into the template view using the data object
        tvName.setText(topic.getName());
        return convertView;
    }
}
