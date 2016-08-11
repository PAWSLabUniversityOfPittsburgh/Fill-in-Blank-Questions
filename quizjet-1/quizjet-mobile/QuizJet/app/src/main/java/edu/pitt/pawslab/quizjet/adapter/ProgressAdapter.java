package edu.pitt.pawslab.quizjet.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import edu.pitt.pawslab.quizjet.QuestionListActivity;
import edu.pitt.pawslab.quizjet.R;

/**
 * Created by changsheng on 1/18/16.
 */
public class ProgressAdapter extends BaseAdapter {

    private Context mContext;
    private String[] topicNames={
            "Variables","Classes","Primitive Data Types","Constants","Strings","Decisions","Switch","Loops","Array","Objectives"
    };
    public ProgressAdapter(Context c) {
        mContext = c;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }





    //This getCount is very important, it will tell the gridview to show how many griditem
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return topicNames.length;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        // TODO Auto-generated method stub
        View view=View.inflate(mContext, R.layout.item_grid_progress, null);
        RelativeLayout r1=(RelativeLayout)view.findViewById(R.id.relaGrid);
        ImageView imgView=(ImageView)r1.findViewById(R.id.chooseImage);
        TextView textView=(TextView) r1.findViewById(R.id.chooseText);
//        ImageRetrieve image = new ImageRetrieve();
//        imgView.setTag("https://www.google.com/images/srpr/logo11w.png");
//        image.execute(imgView);
        if(position <3)
        imgView.setImageResource(R.drawable.success);
        if(position == 3)
            imgView.setImageResource(R.drawable.inprogress);
        if(position >3)
            imgView.setImageResource(R.drawable.todo);
        textView.setText(topicNames[position]);
        final int pos=position;
        imgView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                //Toast.makeText(mContext, ""+pos, Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(mContext, QuestionListActivity.class);
//        EditText editText=(EditText) findViewById(R.id.editMessage);
//        String message=editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);

                mContext.startActivity(intent);
            }});
        return r1;
    }


}
