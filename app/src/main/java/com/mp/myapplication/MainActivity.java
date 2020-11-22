package com.mp.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends Activity {
    JSONObject Shoot=null;
    JSONObject matchDetail=null;
    JSONObject Pass=null;
    JSONArray matchInfo=null;
    String div="--------------------------------------------\n";
    String ShootTotal="";
    String shootOutPenalty="";
    String shootHeading="";
    String longshoot1="";
    String Possesion="";
    String Possesion1="";
    String Heading1="";
    String shortPass1="";
    String longPass1="";
    String drivenPass1="";
    String throughPass1="";
    String passTry="";
    String shortPassTry="";
    String longPassTry="";
    String drivenGroundPassTry="";
    String throughPassTry="";
    int st=0;
    int sop=0;
    int ps=0;
    int hd=0;
    int pt=0;
    int spt=0;
    int lpt=0;
    int dgpt=0;
    int tpt=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        TextView txt1 = new TextView(this);
        txt1.setText("닉네임");
        txt1.setTextSize(18);
        layout.addView(txt1);

        final EditText txt2 = new EditText(this);
        txt2.setHint("구단주 명을 입력하세요...");
        layout.addView(txt2);

        Button btn = new Button(this);
        btn.setText("일대일 정보");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load1vs1Info(txt2.getText().toString());
            }
        });

        Button btn1 = new Button(this);
        btn1.setText("이대이 정보");
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load2vs2Info(txt2.getText().toString());
            }
        });

        Button btn2 = new Button(this);
        btn2.setText("감독모드 정보");
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadManageInfo(txt2.getText().toString());
            }
        });

        /*Button btn3 = new Button(this);
        btn3.setText("유저 정보");
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadUserId(txt2.getText().toString());
            }
        });*/

        Button btn4 = new Button(this);
        btn4.setText("플레이 스타일");
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadMatchId(txt2.getText().toString());
            }
        });

        /*Button btn5 = new Button(this);
        btn5.setText("매치 정보");
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadMatchInfo(txt2.getText().toString());
            }
        });*/

        layout.addView(btn);
        layout.addView(btn1);
        layout.addView(btn2);
        //layout.addView(btn3);
        layout.addView(btn4);

        TextView maker = new TextView(this);
        maker.setText("\n© 2020 HottJ, All rights reserved.\n");
        maker.setTextSize(13);
        maker.setGravity(Gravity.CENTER);
        layout.addView(maker);

        int pad = dip2px(20);
        layout.setPadding(pad, pad, pad, pad);
        setContentView(layout);
    }

    private void load1vs1Info(final String input) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String result = get1vs1Info(input);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result == null) {}//toast("불러오기 실패");
                        else showDialog(input + " 구단주 정보", result);
                    }
                });
            }
        }).start();
    }

    private void load2vs2Info(final String input) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String result = get2vs2Info(input);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result == null) {}//toast("불러오기 실패");
                        else showDialog(input + " 구단주 정보", result);
                    }
                });
            }
        }).start();
    }

    private void loadManageInfo(final String input) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String result = getManageInfo(input);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result == null) {}//toast("불러오기 실패");
                        else showDialog(input + " 구단주 정보", result);
                    }
                });
            }
        }).start();
    }

    private void loadUserId(final String input) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String result = getUserId(input);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result == null) {}//toast("불러오기 실패");
                        else showDialog(input + " 구단주 정보", result);
                    }
                });
            }
        }).start();
    }

    private void loadMatchId(final String input) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String result = getMatchId(getUserId(input));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result == null) {}//toast("불러오기 실패");
                        else showDialog(input + " 구단주 정보", result);
                    }
                });
            }
        }).start();
    }

    /*private void loadMatchInfo(final String input) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String result = getMatchInfo(getMatchId(getUserId(input)));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result == null) {}//toast("불러오기 실패");
                        else showDialog(input + " 구단주 정보", result);
                    }
                });
            }
        }).start();
    }*/

    private String get1vs1Info(String pos) {
        try {
            String url = "http://fifaonline4.nexon.com/datacenter/rank?strCharacterName=" + pos.replace(" ", "+");
            Document data = Jsoup.connect(url).get();
            if (!data.text().contains("포함되어")) {
                String rank = data.select("span.td.rank_no").text();
                //Log.d("rank", rank);
                String price = data.select("span.price").text();
                String winRate = data.select("span.td.rank_r_rate").text();
                String winPoint = data.select("span.td.rank_r_win_point").text();
                String rankPer = data.select("span.td.rank_percent").text();
                //System.out.println(rank.getClass().getName());
                return "랭킹 : " + rank + "\n" + "구단가치 : " + price + "\n" + "승률 : " + winRate + "\n" + "점수 : " + winPoint + "\n" + "백분위 : " + rankPer;
            }
            else{
                return "10000등안에 못들었군요!";
            }
        } catch (Exception e) {
            //Log.d("url","error");
            toast(e.toString());
            return null;
        }
    }
    private String get2vs2Info(String pos) {
        try {
            String url = "http://fifaonline4.nexon.com/datacenter/rank_2vs?strCharacterName=" + pos.replace(" ", "+");
            Document data = Jsoup.connect(url).get();
            if (!data.text().contains("포함되어")) {
                String rank = data.select("span.td.rank_no").text();
                //Log.d("rank", rank);
                String price = data.select("span.price").text();
                String winRate = data.select("span.td.rank_r_rate").text();
                String winPoint = data.select("span.td.rank_r_win_point").text();
                String rankPer = data.select("span.td.rank_percent").text();
                //System.out.println(rank.getClass().getName());
                return "랭킹 : " + rank + "\n" + "구단가치 : " + price + "\n" + "승률 : " + winRate + "\n" + "점수 : " + winPoint + "\n" + "백분위 : " + rankPer;
            }
            else{
                return "10000등안에 못들었군요!";
            }
        } catch (Exception e) {
            //Log.d("url","error");
            toast(e.toString());
            return null;
        }
    }
    private String getManageInfo(String pos) {
        try {
            String url = "http://fifaonline4.nexon.com/datacenter/rank_m?strCharacterName=" + pos.replace(" ", "+");
            Document data = Jsoup.connect(url).get();
            if (!data.text().contains("포함되어")) {
                String rank = data.select("span.td.rank_no").text();
                //Log.d("rank", rank);
                String price = data.select("span.price").text();
                String winRate = data.select("span.td.rank_r_rate").text();
                String winPoint = data.select("span.td.rank_r_win_point").text();
                String rankPer = data.select("span.td.rank_percent").text();
                //System.out.println(rank.getClass().getName());
                return "랭킹 : " + rank + "\n" + "구단가치 : " + price + "\n" + "승률 : " + winRate + "\n" + "점수 : " + winPoint + "\n" + "백분위 : " + rankPer;
            }
            else{
                return "10000등안에 못들었군요!";
            }
        } catch (Exception e) {
            //Log.d("url","error");
            toast(e.toString());
            return null;
        }
    }
    public String getUserId(String pos) {
        String result=null;
        String UserID=null;
        Integer UserLevel=0;
        String NexonKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2NvdW50X2lkIjoiMTA0MDI0NzkxOSIsImF1dGhfaWQiOiIyIiwidG9rZW5fdHlwZSI6IkFjY2Vzc1Rva2VuIiwic2VydmljZV9pZCI6IjQzMDAxMTQ4MSIsIlgtQXBwLVJhdGUtTGltaXQiOiIyMDAwMDoxMCIsIm5iZiI6MTU5NTI1MTYxMSwiZXhwIjoxNjEwODAzNjExLCJpYXQiOjE1OTUyNTE2MTF9.W4K3QQa57KhYUnsIcGyCK-jD_oBXMq1ZMKuN8KXZGO0";
        StringBuffer sb = new StringBuffer();
        try {
            //String text = URLEncoder.encode(pos, "UTF-8");
            String apiURL = "https://api.nexon.co.kr/fifaonline4/v1.0/users?nickname=" + pos.replace(" ", "+");

            URL url = new URL(apiURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", NexonKey);
            InputStream is = conn.getInputStream();

            // Get the stream
            StringBuilder builder = new StringBuilder();

            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            // Set the result
            result = builder.toString();
            JSONObject jsonObject=new JSONObject(result);
            UserID=jsonObject.getString("accessId");
            //UserLevel=jsonObject.getInt("level");
        }
        catch (Exception e) {
            // Error calling the rest api
            Log.e("REST_API", "GET method failed: " + e.getMessage());
            e.printStackTrace();
        }
        return UserID;
    }
    public String getMatchId(String ID){
        String result=null;
        String result1="";
        String result2="";
        JSONArray json_array = null;
        String NexonKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2NvdW50X2lkIjoiMTA0MDI0NzkxOSIsImF1dGhfaWQiOiIyIiwidG9rZW5fdHlwZSI6IkFjY2Vzc1Rva2VuIiwic2VydmljZV9pZCI6IjQzMDAxMTQ4MSIsIlgtQXBwLVJhdGUtTGltaXQiOiIyMDAwMDoxMCIsIm5iZiI6MTU5NTI1MTYxMSwiZXhwIjoxNjEwODAzNjExLCJpYXQiOjE1OTUyNTE2MTF9.W4K3QQa57KhYUnsIcGyCK-jD_oBXMq1ZMKuN8KXZGO0";
        StringBuffer sb = new StringBuffer();
        try {
            //String text = URLEncoder.encode(pos, "UTF-8");
            String apiURL = "https://api.nexon.co.kr/fifaonline4/v1.0/users/"+ID+"/matches?matchtype=50&offset=0&limit=10";
            URL url = new URL(apiURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", NexonKey);
            InputStream is = conn.getInputStream();
            // Get the stream
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            // Set the result
            result = builder.toString();
            json_array = new JSONArray(result);
            //Log.d("test", "json_array.get(0) = " + json_array.get(0));
            int shootnum=0;
            int sopnum=0;
            int matchnum=0;
            int psnum=0;
            int hdnum=0;
            int passnum=0;
            int shortpassnum=0;
            int longpassnum=0;
            int drivenpassnum=0;
            int throughpassnum=0;
            for (int i=0;i<10;i++){
                //Log.d("json","j"+json_array.get(i));
                result1=json_array.get(i).toString();
                getMatchInfo(result1,ID);
                matchnum+=1;
                shootnum+=st;
                sopnum+=sop;
                psnum+=ps;
                hdnum+=hd;
                passnum+=pt;
                shortpassnum+=spt;
                longpassnum+=lpt;
                drivenpassnum+=dgpt;
                throughpassnum+=tpt;
                Log.d("psnum","msg"+psnum);
            }

            Log.d("ps","ms"+psnum+" "+passnum+" "+shortpassnum+" "+shootnum);
            double longshoot=(sopnum/(double)shootnum)*100;
            double possession=(psnum/(double)matchnum);
            double heading=(hdnum/(double)shootnum)*100;
            double shortpass=(shortpassnum/(double)passnum)*100;
            //Log.d("short","dfa"+shortpass);
            double longpass=(longpassnum/(double)passnum)*100;
            double drivenpass=(drivenpassnum/(double)passnum)*100;
            double throughpass=(throughpassnum/(double)passnum)*100;
            longshoot1 = String.format("%.2f", longshoot);
            Possesion1=String.format("%.2f",possession);
            Heading1=String.format("%.2f",heading);
            shortPass1=String.format("%.2f",shortpass);
            longPass1=String.format("%.2f",longpass);
            drivenPass1=String.format("%.2f",drivenpass);
            throughPass1=String.format("%.2f",throughpass);
        }

        catch (Exception e) {
            // Error calling the rest api
            Log.e("REST_API", "GET method failed: " + e.getMessage());
            e.printStackTrace();
            return "해당 닉네임은 존재하지 않습니다.";
        }

        return "평균 점유율 : "+Possesion1+"%\n"+div+"중거리 찰 확률 : "+longshoot1+"%\n"+"헤딩할 확률 : "+Heading1+"%\n"+div+"짧은 패스할 확률 : "+shortPass1+"%\n"+"긴 패스할 확률 : "+longPass1+"%\n"
                +"ZW할 확률 : "+drivenPass1+"%\n"+"스루패스할 확률 : "+throughPass1+"%";
    }

    public void getMatchInfo(String MatchID, String UserID){
        String result=null;
        String longshoot1="";
        String NexonKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2NvdW50X2lkIjoiMTA0MDI0NzkxOSIsImF1dGhfaWQiOiIyIiwidG9rZW5fdHlwZSI6IkFjY2Vzc1Rva2VuIiwic2VydmljZV9pZCI6IjQzMDAxMTQ4MSIsIlgtQXBwLVJhdGUtTGltaXQiOiIyMDAwMDoxMCIsIm5iZiI6MTU5NTI1MTYxMSwiZXhwIjoxNjEwODAzNjExLCJpYXQiOjE1OTUyNTE2MTF9.W4K3QQa57KhYUnsIcGyCK-jD_oBXMq1ZMKuN8KXZGO0";
        StringBuffer sb = new StringBuffer();
        try {
            //String text = URLEncoder.encode(pos, "UTF-8");
            String apiURL = "https://api.nexon.co.kr/fifaonline4/v1.0/matches/" + MatchID;
            URL url = new URL(apiURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", NexonKey);
            InputStream is = conn.getInputStream();
            // Get the stream
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            // Set the result
            result = builder.toString();
            JSONObject jsonObject = new JSONObject(result);
            matchInfo = jsonObject.getJSONArray("matchInfo");
            JSONObject home=matchInfo.getJSONObject(0);
            JSONObject away=matchInfo.getJSONObject(1);
            //Log.d("home",home.getString("accessId"));
            //Log.d("U",UserID);
            if (home.getString("accessId").equals(UserID)) {
                //Log.d("hi","hi");
                Shoot=home.getJSONObject("shoot");
                ShootTotal=Shoot.getString("shootTotal");
                shootOutPenalty=Shoot.getString("shootOutPenalty");
                shootHeading=Shoot.getString("shootHeading");
                st=Integer.parseInt(ShootTotal);
                sop=Integer.parseInt(shootOutPenalty);
                hd=Integer.parseInt(shootHeading);
                //double longshoot=(sop/(double)st)*100;
                //longshoot1 = String.format("%.2f", longshoot);
                //Log.d("test","+"+st+" "+sop+ " "+longshoot);
                matchDetail=home.getJSONObject("matchDetail");
                Possesion=matchDetail.getString("possession");
                Log.d("poss",ShootTotal);
                ps=Integer.parseInt(Possesion);
                Pass=home.getJSONObject("pass");
                passTry=Pass.getString("passTry");
                //Log.d("pass",passTry);
                shortPassTry=Pass.getString("shortPassTry");
                longPassTry=Pass.getString("longPassTry");
                drivenGroundPassTry=Pass.getString("drivenGroundPassTry");
                throughPassTry=Pass.getString("throughPassTry");
                pt=Integer.parseInt(passTry);
                spt=Integer.parseInt(shortPassTry);
                lpt=Integer.parseInt(longPassTry);
                dgpt=Integer.parseInt(drivenGroundPassTry);
                tpt=Integer.parseInt(throughPassTry);
            }
            else {
                //Log.d("hi","hi");
                Shoot=away.getJSONObject("shoot");
                ShootTotal=Shoot.getString("shootTotal");
                shootOutPenalty=Shoot.getString("shootOutPenalty");
                shootHeading=Shoot.getString("shootHeading");
                st=Integer.parseInt(ShootTotal);
                sop=Integer.parseInt(shootOutPenalty);
                hd=Integer.parseInt(shootHeading);
                //double longshoot=(sop/(double)st)*100;
                //longshoot1 = String.format("%.2f", longshoot);
                //Log.d("test","+"+st+" "+sop+ " "+longshoot);
                matchDetail=away.getJSONObject("matchDetail");
                Possesion=matchDetail.getString("possession");
                ps=Integer.parseInt(Possesion);
                Pass=away.getJSONObject("pass");
                passTry=Pass.getString("passTry");
                shortPassTry=Pass.getString("shortPassTry");
                longPassTry=Pass.getString("longPassTry");
                drivenGroundPassTry=Pass.getString("drivenGroundPassTry");
                throughPassTry=Pass.getString("throughPassTry");
                pt=Integer.parseInt(passTry);
                spt=Integer.parseInt(shortPassTry);
                lpt=Integer.parseInt(longPassTry);
                dgpt=Integer.parseInt(drivenGroundPassTry);
                tpt=Integer.parseInt(throughPassTry);
                Log.d("poss",Possesion);
            }

            /*for (int i = 0; i < matchInfo.length(); i++) {
                JSONObject jsonArray = matchInfo.getJSONObject(i);

                matchDetail=jsonArray.getJSONObject("matchDetail");
                Log.d("acc","+");
            }*/
            //Log.d("test","+"+matchDetail);
        }
        catch (Exception e) {
            // Error calling the rest api
            Log.e("REST_API", "GET method failed: " + e.getMessage());
            e.printStackTrace();
        }
        //return longshoot1;
    }
    public void showDialog(String title, String msg) {
        try {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle(title);
            dialog.setMessage(msg);
            dialog.setNegativeButton("닫기", null);
            dialog.show();
        } catch (Exception e) {
            toast(e.toString());
        }
    }
    private void toast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private int dip2px(int dips) {
        return (int) Math.ceil(dips * this.getResources().getDisplayMetrics().density);
    }
}