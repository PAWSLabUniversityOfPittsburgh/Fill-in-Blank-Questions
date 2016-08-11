package edu.pitt.pawslab.quizjet;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void startTopic(View v){
        Intent intent=new Intent(MainActivity.this, TopicSelectActivity.class);
//        EditText editText=(EditText) findViewById(R.id.editMessage);
//        String message=editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void showProgress(View v){
        Intent intent=new Intent(MainActivity.this, ProgressActivity.class);
//        EditText editText=(EditText) findViewById(R.id.editMessage);
//        String message=editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void showHelp(View v){
        showCustomDialog(this);
    }

    public  void showCustomDialog(Context ctx) {

        final Dialog m_dialog = new Dialog(ctx, R.style.Dialog_No_Border);
        m_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        final Context lctx= ctx;

        LayoutInflater m_inflater = LayoutInflater.from(ctx);
        View m_view = m_inflater.inflate(R.layout.custom_dialog, null);
        LinearLayout m_llMain = (LinearLayout) m_view.findViewById(R.id.cadllMain);

        //Change the background of the dialog according to the layout.
       /* if (p_index == BORDER_DIALOG) {
            m_llMain.setBackgroundResource(R.drawable.btn_style_border);
        } else if (p_index == ROUNDE_CORNER_DIALOG) {
            m_llMain.setBackgroundResource(R.drawable.btn_style_roundcorner);
        } else if (p_index == ROUNDE_CORNER_BORDER_DIALOG) {
            m_llMain.setBackgroundResource(R.drawable.btn_style_border_roundcorner);
        }*/

        Button m_btnOk = (Button) m_view.findViewById(R.id.cadbtnOk);
        Button m_btnCancel = (Button) m_view.findViewById(R.id.cadbtnCancel);
        TextView tv=(TextView) m_view.findViewById(R.id.msg);
        ImageView mimg=(ImageView) m_view.findViewById(R.id.msgimage);
        mimg.setImageResource(R.drawable.icon);

        View.OnClickListener m_clickListener = new View.OnClickListener() {

            @Override
            public void onClick(View p_v) {
                //Toast.makeText(lctx, "Pressed  " + ((Button) p_v).getText(), Toast.LENGTH_SHORT).show();


                        m_dialog.dismiss();

                }

        };

        m_btnOk.setOnClickListener(m_clickListener);
        m_btnCancel.setOnClickListener(m_clickListener);

        m_dialog.setContentView(m_view);
        m_dialog.show();
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
