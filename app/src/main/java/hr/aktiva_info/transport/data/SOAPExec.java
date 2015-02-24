package hr.aktiva_info.transport.data;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.net.SocketTimeoutException;

import hr.aktiva_info.transport.MainActivity;

/**
 * Created by radovan on 24.2.2015..
 */
public class SOAPExec
 extends AsyncTask<String, Void, String> {
    private final Context _context;
    private String _executeString = "";
    private String _method ="";
    ProgressDialog _dialog;

    public SOAPExec(String executeString, String method, Context context) {
        this._executeString = executeString;
        this._method = method;
        this._context = context;
    }
    @Override
    protected void onPreExecute() {
        //if you want, start progress dialog here
        if (isNetworkAvailable()) {
            Log.d("TRANSPORT", "Mreža postoji");
            _dialog = new ProgressDialog(_context);
            _dialog.setMessage("Dohvaćam podatke sa servera...");
            _dialog.show();
        }

    }

    //http://ai-virtual-01.cloudapp.net:6555/DBInterfaces/DatabaseInterface.asmx
    @Override
    protected String doInBackground(String... urls) {
        String webResponse = "";
        try{
            final String NAMESPACE = "http://www.aktiva-info.hr/";
            final String URL = "http://ai-virtual-01.cloudapp.net:6555/DBInterfaces/DatabaseInterface.asmx";
            //final String URL = "http://www.aktiva-info.hr/DBI/databaseInterface.asmx";
            final String SOAP_ACTION = "http://www.aktiva-info.hr/ExecuteSQLJSON";
            final String METHOD_NAME = "ExecuteSQLJSON";

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            PropertyInfo server =new PropertyInfo();
            server.setName("serverName");
            //gets the first element from urls array
            server.setValue("ai-virtual-01.cloudapp.net,11967");
            //server.setValue("10.20.0.11");
            server.setType(String.class);
            request.addProperty(server);

            PropertyInfo dbname =new PropertyInfo();
            dbname.setName("dbName");
            //gets the first element from urls array
            dbname.setValue("izleti_marinero");
            dbname.setType(String.class);
            request.addProperty(dbname);

            PropertyInfo username =new PropertyInfo();
            username.setName("userName");
            //gets the first element from urls array
            username.setValue("mobile_user_izleti");
            username.setType(String.class);
            request.addProperty(username);

            PropertyInfo pwd =new PropertyInfo();
            pwd.setName("pwd");
            //gets the first element from urls array
            pwd.setValue("mobile5");
            pwd.setType(String.class);
            request.addProperty(pwd);

            PropertyInfo exec =new PropertyInfo();
            exec.setName("sqlToExecute");
            //gets the first element from urls array
            int random = (int )(Math.random() * 1000 + 1);
            String executeString = _executeString + ",'"+random+"'";
            exec.setValue(executeString);
            exec.setType(String.class);
            request.addProperty(exec);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            Log.d("TRANSPORT", "SOAP-Poziv");
            androidHttpTransport.call(SOAP_ACTION, envelope);
            SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
            webResponse = response.toString();
        }
//        catch(Exception e){
//
//            Log.d("TRANSPORT", "SOAP-Pogreška");
//            _dialog.dismiss();
//        }

     catch (SocketTimeoutException t) {
         Log.d("TRANSPORT", "SOAP-Pogreška t");
        t.printStackTrace();
    } catch (IOException i) {
            Log.d("TRANSPORT", "SOAP-Pogreška i");
        i.printStackTrace();
    } catch (Exception q) {
            Log.d("TRANSPORT", "SOAP-Pogreška q");
        q.printStackTrace();
    }
        return webResponse;
    }

    @Override
    protected void onPostExecute(String result) {
        _dialog.dismiss();
        Log.d("TRANSPORT", "SOAP- Ok");
        if(_method.equals("test")) {
            Log.d("TRANSPORT", "SOAP- Ok-method test");
            Log.d("TRANSPORT", "SOAP- " + result);
            MainActivity ma = (MainActivity) _context;
            ma.do_soap_postback(result);
        }

          // Context ma = (Context) _context;
          // ma.dataPostback(result);}
//        if(_method.equals("printPostback")){
//            MainActivity ma = (MainActivity) _context;
//            ma.printPostback(result);}
//        if(_method.equals("logInPostback")){
//            LogIn li = (LogIn) _context;
//            li.logInPostback(result);}
//        if(_method.equals("racunPostback")){
//            BlagajnaActivity ba = (BlagajnaActivity) _context;
//            ba.racunPostback(result);}
//        if(_method.equals("racunPrintPostback")){
//            BlagajnaActivity ba = (BlagajnaActivity) _context;
//            ba.racunPrintPostback(result);}
//        if(_method.equals("uplataIsplataPostback")){
//            BlagajnaActivity ba = (BlagajnaActivity) _context;
//            ba.uplataIsplataPostback(result);}
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

