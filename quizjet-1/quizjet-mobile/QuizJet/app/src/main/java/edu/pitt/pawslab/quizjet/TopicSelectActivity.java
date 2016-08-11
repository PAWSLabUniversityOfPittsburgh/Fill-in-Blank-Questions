package edu.pitt.pawslab.quizjet;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import edu.pitt.pawslab.quizjet.adapter.TopicAdapter;
import edu.pitt.pawslab.quizjet.entity.Topic;


public class TopicSelectActivity extends ActionBarActivity {
    private ListView list;
    private TopicAdapter adapter;
    private String[] topicNames={
            "jVariable1","jClass1","jBankAccount","Jba_ques","jBoolean_Var","jBoolean_Method","jIfelse2","jIfelse3","jArray1","jObjectives2"
    };
    private ArrayList<Topic> topicList = new ArrayList<Topic>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_select);
        list = (ListView) findViewById(R.id.topiclist);
        for (int i = 0;i<topicNames.length;i++){
            Topic t = new Topic();
            t.setName(topicNames[i]);
            topicList.add(t);
        }
        adapter=new TopicAdapter(TopicSelectActivity.this,topicList);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                // TODO Auto-generated method stub
                Intent intent=new Intent(TopicSelectActivity.this, QuestionListActivity.class);
//        EditText editText=(EditText) findViewById(R.id.editMessage);
//        String message=editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
                startActivity(intent);


            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_topic_select, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
