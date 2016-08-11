package edu.pitt.pawslab.quizjet;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;

public class RegisterActivity extends Activity {
    private static final String ACTION_RECV_MSG = "com.Illmassa.lumoqandroid.intent.action.RECEIVE_MESSAGE";
    private static ProgressBar loginpb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment()).commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.register, menu);
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
        private EditText et_repreatPassword;
        private ProgressBar pbar;
        private static Handler handler;
        private Thread registerThread;
        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_register,
                    container, false);
            et_username = (EditText)rootView.findViewById(R.id.userNameR);
            et_password = (EditText)rootView.findViewById(R.id.passwordR);
            et_repreatPassword = (EditText)rootView.findViewById(R.id.passwordRR);
            pbar= (ProgressBar)rootView.findViewById(R.id.loginProgressR);
            Button register = (Button)rootView.findViewById(R.id.myButtonR);
	         /*When register result return*/
            handler=new Handler(){
                @Override
                public void handleMessage(Message msg){
                    String message = msg.getData().getString("result");
                    if (message!=null&&message.startsWith("-")){
                        SharedPreferences prefs = getActivity().getSharedPreferences("login", 0);
                        if (prefs.getBoolean("logflag", false)==false) {
                            SharedPreferences.Editor editor = prefs.edit();
                            // Increment launch counter
                            editor.putBoolean("logflag", true);
                            editor.putString("USERID", message.substring(1, message.length()));
                            editor.commit();
                        }
                        Intent nextIntent = new Intent(getActivity(), MainActivity.class);
                        nextIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(nextIntent);
                        getActivity().finish();

                    }else{
                        pbar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    }
                }
            };
            register.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    if(!validate())return;
                    pbar.setVisibility(View.VISIBLE);
                    registerThread=new Thread(){
                        @Override
                        public void run() {
                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put("accountID", et_username.getText().toString().trim());
                            map.put("password", et_password.getText().toString().trim());
                            map.put("passwordRetype", et_password.getText().toString().trim());
                            String result="";
                            try {
                               // result = HttpUtil.postRequest(HttpUtil.BASE_URL_Register, map);
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                                result="Network error";
                            }
                            Message msg = new Message();
                            Bundle bun = new Bundle();
                            bun.putString("result",result); // for example
                            msg.setData(bun);
                            handler.sendMessage(msg);
                        }
                    };
                    registerThread.start();
                }});

            Spinner spinner = (Spinner) rootView.findViewById(R.id.education);
// Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                    R.array.educationlist, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
            spinner.setAdapter(adapter);

            Spinner spinner2 = (Spinner) rootView.findViewById(R.id.gender);
// Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(),
                    R.array.genderlist, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
            spinner2.setAdapter(adapter2);

            return rootView;
        }

        private boolean validate()
        {
            String username = et_username.getText().toString().trim();
            if (username.equals(""))
            {
                Toast.makeText(getActivity(),  "username cannot be empty", Toast.LENGTH_SHORT).show();
                return false;
            }
            String pwd = et_password.getText().toString().trim();
            if (pwd.equals(""))
            {
                Toast.makeText(getActivity(),  "password cannot be empty", Toast.LENGTH_SHORT).show();
                return false;
            }
            String repwd = et_repreatPassword.getText().toString().trim();
            if (repwd.equals(""))
            {
                Toast.makeText(getActivity(),  "confirm password cannot be empty", Toast.LENGTH_SHORT).show();
                return false;
            }

            if(!pwd.equals(repwd))
            {
                Toast.makeText(getActivity(),  "confirm password is different with password", Toast.LENGTH_SHORT).show();
                return false;
            }

            return true;
        }
    }



}
