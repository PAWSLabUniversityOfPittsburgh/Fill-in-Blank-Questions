package edu.pitt.pawslab.quizjet.edu.pitt.pawslab.quizjet.msg;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;



import java.util.HashMap;

public class ConnectService extends IntentService {
	private static final String ACTION_RECV_MSG = "edu.pitt.pawslab.quizjet.intent.action.RECEIVE_MESSAGE";

	public ConnectService() {
		super("TestIntentService");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		/**
		 *IntentService for time consuming task
		
		 */
		String flag = "";
		String username = intent.getStringExtra("accountID");
		String password = intent.getStringExtra("password");
		String login=intent.getStringExtra("login");
		flag = doLogin(username, password);
		Log.d("log in result", flag.toString());
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction(ACTION_RECV_MSG);  
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		broadcastIntent.putExtra("result", flag.toString());
		sendBroadcast(broadcastIntent);
	}
	
	 // log 
 	private String doLogin(String username, String password)
 	{
 		String strFlag = "";
// 		// put param into map
// 		HashMap<String, String> map = new HashMap<String, String>();
// 		map.put("accountID", username);
// 		map.put("password", password);
//// 		String url = HttpUtil.BASE_URL + "LoginServlet?un=" + username + "&pw=" + password;
// 		//String url = HttpUtil.BASE_URL + "LoginServlet"; //POST
// 		String url = HttpUtil.BASE_URL;
// 		Log.d("url", url);
// 		Log.d("usernameee", username);
// 		Log.d("passworddddd", password);
// 		try {
//			strFlag = HttpUtil.postRequest(url, map);  //
//// 			strFlag = HttpUtil.getRequest(url);  //GE
//			Log.d("server code", "sss");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			Log.d("server code", "ssssss");
//		}
// 		Log.d("server code", "tttttt");
 	 return strFlag;
 		
 	}

}