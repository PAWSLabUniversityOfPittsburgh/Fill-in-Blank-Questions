package edu.pitt.pawslab.quizjet;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;



import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import edu.pitt.pawslab.quizjet.edu.pitt.pawslab.quizjet.msg.ConnectService;

public class LoginActivity extends Activity {
    private static final String ACTION_RECV_MSG = "edu.pitt.pawslab.quizjet.intent.action.RECEIVE_MESSAGE";
    private static ProgressBar loginpb;
    private static MessageReceiver receiver ;
    private static LoginActivity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = this.getSharedPreferences("login", 0);
        if(prefs.getBoolean("logflag", false)==true){
            Intent nextIntent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(nextIntent);
            this.finish();
        }
        setContentView(R.layout.activity_login);
        context=this;
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment()).commit();
        }
    }

    @Override
    protected void onResume(){
        IntentFilter filter = new IntentFilter(ACTION_RECV_MSG);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new MessageReceiver();
        registerReceiver(receiver, filter);
        super.onResume();
    }
    @Override
    protected void onStop(){
        try{
            unregisterReceiver(receiver);
        }
        catch (Exception e){
        }
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private EditText et_username ;
        private EditText et_password;
        private TextView register;


        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_login, container, false);
            et_username = (EditText)rootView.findViewById(R.id.userName);
            et_password = (EditText)rootView.findViewById(R.id.password);
            loginpb= (ProgressBar)rootView.findViewById(R.id.loginProgress);
            register=(TextView) rootView.findViewById(R.id.registere);
            register.setText(Html.fromHtml ("<a href=\"www.lumoq.com\"> Sign up with QuizJet today </ a>"));
            Button submit = (Button)rootView.findViewById(R.id.myButton);
            submit.setOnClickListener(new OnClickListener(){
                public void onClick(View v){
                    if (validate()){
                        // success login
                        loginpb.setVisibility(View.VISIBLE);
                        Intent msgIntent = new Intent(context, ConnectService.class);
                        msgIntent.putExtra("accountID", et_username.getText().toString().trim());
                        msgIntent.putExtra("password", et_password.getText().toString().trim());
                        msgIntent.putExtra("login", "true");
                        context.startService(msgIntent);
                    }
                }
            });
            register.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    Intent reg=new Intent(getActivity(),RegisterActivity.class);
                    context.startActivity(reg);
                }
            });
            return rootView;
        }

        private boolean validate()
        {
            String username = et_username.getText().toString().trim();
            if (username.equals(""))
            {
                Toast.makeText(context,  "username cannot be empty", Toast.LENGTH_SHORT).show();
                return false;
            }
            String pwd = et_password.getText().toString().trim();
            if (pwd.equals(""))
            {
                Toast.makeText(context,  "password cannot be empty", Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        }
    }

    public class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context ct, Intent intent) {
            String message = intent.getStringExtra("result");
            Log.d("MessageReceiver", message);
            // if login success
            if (true){
                // start main activity
                Intent nextIntent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(nextIntent);
                // store login info
                SharedPreferences prefs = ct.getSharedPreferences("login", 0);
                if (prefs.getBoolean("logflag", false)==false) {
                    SharedPreferences.Editor editor = prefs.edit();
                    // Increment launch counter
                    editor.putBoolean("logflag", true);
                    editor.putString("USERID", "1");
                    editor.commit();
                }
                //finish itself
                finish();
                //unregister
                ct.unregisterReceiver(this);
            }else{
                loginpb.setVisibility(View.INVISIBLE);
                Toast.makeText(context,  message, Toast.LENGTH_SHORT).show();
            }

        }
    }

}
