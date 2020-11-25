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
import java.util.logging.Level;


public class MainActivity extends Activity {
    JSONObject Shoot=null;
    JSONObject yourShoot=null;
    JSONObject matchDetail=null;
    JSONArray matchInfo=null;
    String div="_______________________________\n";
    String ShootTotal="";
    String shootOutPenalty="";
    String shootInPenalty="";
    String shootHeading="";
    String longshoot1="";
    String shortshoot1="";
    String Possesion="";
    String Possesion1="";
    String goalInPenalty1="";
    String goalOutPenalty1="";
    String goalSet="";
    String Heading1="";
    String myNick="";
    String otherNick="";
    String myGoal="";
    String yourGoal="";
    String division="";
    String achievementDate="";
    String level="";
    String controller="";
    String goalInPenalty="";
    String goalOutPenalty="";
    int st=0;
    int sop=0;
    int si=0;
    int ps=0;
    int hd=0;
    int intMyGoal=0;
    int intYourGoal=0;
    int myGoalSum=0;
    int yourGoalSum=0;
    int hitPost=0;
    int myGoalInPenalty=0;
    int myGoalOutPenalty=0;
    int inShoot=0;
    int inNormalShoot=0;
    int inZdShoot=0;
    int inHeadingShoot=0;
    int outShoot=0;
    int outNormalShoot=0;
    int outZdShoot=0;
    int outHeadingShoot=0;
    int inGoal=0;
    int inNormaGoal=0;
    int inZdGoal=0;
    int inHeadingGoal=0;
    int outGoal=0;
    int outNormalGoal=0;
    int outZdGoal=0;
    int outHeadingGoal=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        TextView txt1 = new TextView(this);
        txt1.setText("지피지기 백전백승\n");
        txt1.setTextSize(18);
        layout.addView(txt1);

        final EditText txt2 = new EditText(this);
        txt2.setHint("구단주 명을 입력하세요.");
        layout.addView(txt2);

        Button btn = new Button(this);
        btn.setText("공식경기 순위");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load1vs1Info(txt2.getText().toString());
            }
        });

        Button btn3 = new Button(this);
        btn3.setText("구단주 정보");
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadUserData(txt2.getText().toString());
            }
        });

        Button btn4 = new Button(this);
        btn4.setText("플레이 스타일");
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadMatchId(txt2.getText().toString());
            }
        });

        Button btn5 = new Button(this);
        btn5.setText("최근 전적");
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadMatch(txt2.getText().toString());
            }
        });

        layout.addView(btn);
        layout.addView(btn3);
        layout.addView(btn4);
        layout.addView(btn5);

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
                        else showDialog(input + "님의 공식경기 순위", result);
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
                        else showDialog(input + "님의 플레이 스타일", result);
                    }
                });
            }
        }).start();
    }

    private void loadMatch(final String input) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String result = getMatch(getUserId(input));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result == null) {}//toast("불러오기 실패");
                        else showDialog(input + "님의 최근 전적", result);
                    }
                });
            }
        }).start();
    } //최근 전적 가져옴

    private void loadUserData(final String input) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String result = getUserData(getUserId(input));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result == null) {}//toast("불러오기 실패");
                        else showDialog(input + "님의 구단주 정보", result);
                    }
                });
            }
        }).start();
    } //최근 전적 가져옴

    private String get1vs1Info(String pos) {
        String result="";
        try {
            String url = "http://fifaonline4.nexon.com/datacenter/rank?strCharacterName=" + pos.replace(" ", "+");
            Document data = Jsoup.connect(url).get();
            if (!data.text().contains("포함되어")) {
                String rank = data.select("span.td.rank_no").text();
                String price = data.select("span.price").text();
                String winRate = data.select("span.td.rank_r_rate").text();
                String winPoint = data.select("span.td.rank_r_win_point").text();
                String match=data.select("span.td.rank_before").text();
                String[] matchSplit=match.split(" | ");
                String win=matchSplit[0];
                String draw=matchSplit[2];
                String lose=matchSplit[4];
                result+="< 1ON1 순위 >\n"+"랭킹 : " + rank + "위\n" + "구단가치 : " + price + "\n" +  "전적 : "+win+"승 "+draw+"무 " +lose+"패\n"+"승률 : " + winRate + "\n" + "점수 : " + winPoint + "점\n";
            }
            else{
                result+= "< 1ON1 순위 >\n"+"10000등안에 못들었군요!\n";
            }
        } catch (Exception e) {
            toast(e.toString());
            return "다시 입력해주세요.";
        }
        try {
            String url = "http://fifaonline4.nexon.com/datacenter/rank_2vs?strCharacterName=" + pos.replace(" ", "+");
            Document data = Jsoup.connect(url).get();
            if (!data.text().contains("포함되어")) {
                String rank = data.select("span.td.rank_no").text();
                String price = data.select("span.price").text();
                String winRate = data.select("span.td.rank_r_rate").text();
                String winPoint = data.select("span.td.rank_r_win_point").text();
                String match=data.select("span.td.rank_before").text();
                String[] matchSplit=match.split(" | ");
                String win=matchSplit[0];
                String draw=matchSplit[2];
                String lose=matchSplit[4];
                result+= "\n< 2ON2 순위 >\n"+"랭킹 : " + rank + "위\n" + "구단가치 : " + price + "\n" +  "전적 : "+win+"승 "+draw+"무 " +lose+"패\n"+"승률 : " + winRate + "\n" + "점수 : " + winPoint + "점\n";
            }
            else{
                result+= "\n< 2ON2 순위 >\n"+"10000등안에 못들었군요!\n";
            }
        } catch (Exception e) {
            //Log.d("url","error");
            toast(e.toString());
            return "다시 입력해주세요.";
        }
        try {
            String url = "http://fifaonline4.nexon.com/datacenter/rank_m?strCharacterName=" + pos.replace(" ", "+");
            Document data = Jsoup.connect(url).get();
            if (!data.text().contains("포함되어")) {
                String rank = data.select("span.td.rank_no").text();
                String price = data.select("span.price").text();
                String winRate = data.select("span.td.rank_r_rate").text();
                String winPoint = data.select("span.td.rank_r_win_point").text();
                String match=data.select("span.td.rank_before").text();
                String[] matchSplit=match.split(" | ");
                String win=matchSplit[0];
                String draw=matchSplit[2];
                String lose=matchSplit[4];
                result+= "\n< 감독모드 순위 >\n"+"랭킹 : " + rank + "위\n" + "구단가치 : " + price + "\n" +  "전적 : "+win+"승 "+draw+"무 " +lose+"패\n"+"승률 : " + winRate + "\n" + "점수 : " + winPoint + "점";
            }
            else{
                result+= "\n< 감독모드 순위 >\n"+"10000등안에 못들었군요!";
            }
        } catch (Exception e) {
            //Log.d("url","error");
            toast(e.toString());
            return "다시 입력해주세요.";
        }
        return result;
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
            level=jsonObject.getString("level");
            //UserLevel=jsonObject.getInt("level");
        }
        catch (Exception e) {
            // Error calling the rest api
            //Log.e("REST_API", "GET method failed: " + e.getMessage());
            e.printStackTrace();
        }
        return UserID;
    }

    public String getMatchId(String ID){
        String result=null;
        String result1="";
        String inNormal1="";
        String inZd1="";
        String inHeader1="";
        String outNormal1="";
        String outZd1="";
        String outHeader1="";
        String inNormalG1="";
        String inZdG1="";
        String inHdG1="";
        String outNormalG1="";
        String outZdG1="";
        String outHdG1="";
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
            int shootnum=0;
            int sopnum=0;
            int sinum=0;
            int matchnum=0;
            int psnum=0;
            int hdnum=0;
            int gip=0;
            int gop=0;
            int goalnum=0;
            inShoot=0;
            outShoot=0;
            inGoal=0;
            outGoal=0;
            inNormalShoot=0;
            inZdShoot=0;
            inHeadingShoot=0;
            outNormalShoot=0;
            outZdShoot=0;
            outHeadingShoot=0;
            inZdGoal=0;
            inHeadingGoal=0;
            inNormaGoal=0;
            outNormalGoal=0;
            outZdGoal=0;
            outHeadingGoal=0;
            for (int i=0;i<10;i++){
                result1=json_array.get(i).toString();
                getMatchInfo(result1,ID);
                matchnum+=1;
                shootnum+=st;
                sopnum+=sop;
                sinum+=si;
                psnum+=ps;
                hdnum+=hd;
                gip+=myGoalInPenalty;
                gop+=myGoalOutPenalty;
                goalnum+=intMyGoal;
            }
            double longshoot=(sopnum/(double)shootnum)*100;
            double shortshoot=(sinum/(double)shootnum)*100;
            double possession=(psnum/(double)matchnum);
            double heading=(hdnum/(double)shootnum)*100;
            double inPenalty=(gip/(double)goalnum)*100;
            double outPenalty=(gop/(double)goalnum)*100;
            double set=100-inPenalty-outPenalty;
            double inNormal=(inNormalShoot/(double)inShoot)*100;
            double inZd=(inZdShoot/(double)inShoot)*100;
            double inHeader=(inHeadingShoot/(double)inShoot)*100;
            double outNormal=(outNormalShoot/(double)outShoot)*100;
            double outZd=(outZdShoot/(double)outShoot)*100;
            double outHeader=(outHeadingShoot/(double)outShoot)*100;
            double inNormalG=(inNormaGoal/(double)inGoal)*100;
            double inZdG=(inZdGoal/(double)inGoal)*100;
            double inHdG=(inHeadingGoal/(double)inGoal)*100;
            double outNormalG=(outNormalGoal/(double)outGoal)*100;
            double outZdG=(outZdGoal/(double)outGoal)*100;
            double outHdG=(outHeadingGoal/(double)outGoal)*100;
            longshoot1 = String.format("%.2f", longshoot);
            shortshoot1=String.format("%.2f",shortshoot);
            Possesion1=String.format("%.2f",possession);
            Heading1=String.format("%.2f",heading);
            goalInPenalty1=String.format("%.2f",inPenalty);
            goalOutPenalty1=String.format("%.2f",outPenalty);
            goalSet=String.format("%.2f",set);
            inNormal1=String.format("%.2f",inNormal);
            inZd1=String.format("%.2f",inZd);
            inHeader1=String.format("%.2f",inHeader);
            outNormal1=String.format("%.2f",outNormal);
            outZd1=String.format("%.2f",outZd);
            outHeader1=String.format("%.2f",outHeader);
            inNormalG1=String.format("%.2f",inNormalG);
            inZdG1=String.format("%.2f",inZdG);
            inHdG1=String.format("%.2f",inHdG);
            outNormalG1=String.format("%.2f",outNormalG);
            outZdG1=String.format("%.2f",outZdG);
            outHdG1=String.format("%.2f",outHdG);
        }
        catch (Exception e) {
            // Error calling the rest api
            //Log.e("REST_API", "GET method failed: " + e.getMessage());
            e.printStackTrace();
            return "해당 닉네임은 존재하지 않습니다.";
        }
        return "< 전체 유형 >\n"+"컨트롤러 : "+controller+"\n"+"평균 점유율 : "+Possesion1+"%\n"+div+"< 슈팅 유형 >\n1. 패널티 안 슈팅 : "+shortshoot1+"%\n"+"* D / DD : "+inNormal1+"%\n"+"* ZD : "+inZd1+"%\n"+"* 헤더 : "+inHeader1+"%\n"+
                "2. 패널티 밖 슈팅 : "+longshoot1+"%\n"+"* D / DD : "+outNormal1+"%\n"+"* ZD : "+outZd1+"%\n"+"* 헤더 : "+outHeader1+"%\n"+div+
                "< 골 유형 >\n1. 패널티 안 골 : "+ goalInPenalty1+"%\n"+"* D / DD : "+inNormalG1+"%\n"+"* ZD : "+inZdG1+"%\n"+"* 헤더 : "+inHdG1+"%\n"+
                "2. 패널티 밖 골 : "+goalOutPenalty1+"%\n"+"* D / DD : "+outNormalG1+"%\n"+"* ZD : "+outZdG1+"%\n"+"* 헤더 : "+outHdG1+"%\n"+"3. 프리킥 / PK : "+goalSet+"%";
    }

    public String getUserData(String ID){
        String result=null;
        String result1="";
        String NexonKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2NvdW50X2lkIjoiMTA0MDI0NzkxOSIsImF1dGhfaWQiOiIyIiwidG9rZW5fdHlwZSI6IkFjY2Vzc1Rva2VuIiwic2VydmljZV9pZCI6IjQzMDAxMTQ4MSIsIlgtQXBwLVJhdGUtTGltaXQiOiIyMDAwMDoxMCIsIm5iZiI6MTU5NTI1MTYxMSwiZXhwIjoxNjEwODAzNjExLCJpYXQiOjE1OTUyNTE2MTF9.W4K3QQa57KhYUnsIcGyCK-jD_oBXMq1ZMKuN8KXZGO0";
        StringBuffer sb = new StringBuffer();
        try {
            String apiURL = "https://api.nexon.co.kr/fifaonline4/v1.0/users/" + ID + "/maxdivision";
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
            JSONArray jsonArray = new JSONArray(result);
            try{
                JSONObject oneVsone = jsonArray.getJSONObject(0);
                division = oneVsone.getString("division");
                achievementDate = oneVsone.getString("achievementDate");
                if (division.equals("800")) {
                    division="슈퍼챔피언스";
                }
                else if (division.equals("900")) {
                    division="챔피언스";
                }
                else if (division.equals("1100")) {
                    division="챌린지";
                }
                else if (division.equals("2000")) {
                    division="월드클래스1부";
                }
                else if (division.equals("2100")) {
                    division="월드클래스2부";
                }
                else if (division.equals("2200")) {
                    division="월드클래스3부";
                }
                else if (division.equals("2300")) {
                    division="프로1부";
                }
                else if (division.equals("2400")) {
                    division="프로2부";
                }
                else if (division.equals("2500")) {
                    division="프로3부";
                }
                else if (division.equals("2600")) {
                    division="세미프로1부";
                }
                else if (division.equals("2700")) {
                    division="세미프로2부";
                }
                else if (division.equals("2800")) {
                    division="세미프로3부";
                }
                else if (division.equals("2900")) {
                    division="유망주1부";
                }
                else if (division.equals("3000")) {
                    division="유망주2부";
                }
                else if (division.equals("3100")) {
                    division="유망주3부";
                }
                result1+="레벨 : "+level+"\n\n"+"< 1ON1 >\n"+"최고 등급 : "+division+"\n"+"달성 시각 : "+achievementDate+"\n\n";
            }
            catch (Exception e){
                result1+="1ON1을 진행하지 않았습니다.";
            }

            try{
                JSONObject managemode = jsonArray.getJSONObject(1);
                division = managemode.getString("division");
                achievementDate = managemode.getString("achievementDate");
                if (division.equals("800")) {
                    division="슈퍼챔피언스";
                }
                else if (division.equals("900")) {
                    division="챔피언스";
                }
                else if (division.equals("1100")) {
                    division="챌린지";
                }
                else if (division.equals("2000")) {
                    division="월드클래스1부";
                }
                else if (division.equals("2100")) {
                    division="월드클래스2부";
                }
                else if (division.equals("2200")) {
                    division="월드클래스3부";
                }
                else if (division.equals("2300")) {
                    division="프로1부";
                }
                else if (division.equals("2400")) {
                    division="프로2부";
                }
                else if (division.equals("2500")) {
                    division="프로3부";
                }
                else if (division.equals("2600")) {
                    division="세미프로1부";
                }
                else if (division.equals("2700")) {
                    division="세미프로2부";
                }
                else if (division.equals("2800")) {
                    division="세미프로3부";
                }
                else if (division.equals("2900")) {
                    division="유망주1부";
                }
                else if (division.equals("3000")) {
                    division="유망주2부";
                }
                else if (division.equals("3100")) {
                    division="유망주3부";
                }
                result1+="< 감독모드 >\n"+"최고 등급 : "+division+"\n"+"달성 시각 : "+achievementDate+"\n\n";
            }
            catch (Exception e){
                result1+="< 감독모드 >\n"+"감독모드를 진행하지 않았습니다.";
            }

            try{
                JSONObject volta = jsonArray.getJSONObject(2);
                division = volta.getString("division");
                achievementDate = volta.getString("achievementDate");
                if (division.equals("1100")) {
                    division="월드 스타";
                }
                else if (division.equals("2000")) {
                    division="내셔널 스타1부";
                }
                else if (division.equals("2100")) {
                    division="내셔널 스타2부";
                }
                else if (division.equals("2200")) {
                    division="내셔널 스타3부";
                }
                else if (division.equals("2300")) {
                    division="라이징 스타1부";
                }
                else if (division.equals("2400")) {
                    division="라이징 스타2부";
                }
                else if (division.equals("2500")) {
                    division="라이징 스타3부";
                }
                else if (division.equals("2600")) {
                    division="슈퍼 루키1부";
                }
                else if (division.equals("2700")) {
                    division="슈퍼 루키2부";
                }
                else if (division.equals("2800")) {
                    division="슈퍼 루키3부";
                }
                else if (division.equals("2900")) {
                    division="루키1부";
                }
                else if (division.equals("3000")) {
                    division="루키2부";
                }
                else if (division.equals("3100")) {
                    division="루키3부";
                }
                result1+="< 볼타모드 >\n"+"최고 등급 : "+division+"\n"+"달성 시각 : "+achievementDate+"\n";
            }
            catch (Exception e){
                result1+="< 볼타모드 >\n"+"볼타모드를 진행하지 않았습니다.";
                //return result1;
            }
        }
        catch (Exception e) {
            // Error calling the rest api
            //Log.e("REST_API", "GET method failed: " + e.getMessage());
            e.printStackTrace();
            return "다시 입력해주세요.";
        }

        return result1;
    }

    public String getMatch(String ID){
        String result=null;
        String result1="";
        String result2="";
        int win=0;
        int draw=0;
        int lose=0;
        int matchNum=0;
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
            hitPost=0;
            myGoalSum=0;
            yourGoalSum=0;
            for (int i=0;i<10;i++){
                //Log.d("json","j"+json_array.get(i));
                result1=json_array.get(i).toString();
                getMatchNickGoal(result1,ID);
                matchNum+=1;
                myGoalSum+=intMyGoal;
                yourGoalSum+=intYourGoal;
                if (intMyGoal>intYourGoal){
                    win+=1;
                }
                else if(intMyGoal==intYourGoal){
                    draw+=1;
                }
                else{
                    lose+=1;
                }
                result2+="<"+myNick+"> "+myGoal+" : "+yourGoal+" <"+otherNick+">\n";
            }
        }

        catch (Exception e) {
            // Error calling the rest api
            //Log.e("REST_API", "GET method failed: " + e.getMessage());
            e.printStackTrace();
            return "해당 구단주는 존재하지 않습니다.";
        }
        return result2+"\n"+"최근 "+matchNum+"경기 "+win+"승"+draw+"무"+lose+"패\n"+myGoalSum+"득점 "+yourGoalSum+"실점 "+hitPost+"골대";
    }

    public String getMatchNickGoal(String MatchID, String UserID){
        String result=null;
        //String longshoot1="";
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
            if (home.getString("accessId").equals(UserID)) {
                myNick=home.getString("nickname");
                otherNick=away.getString("nickname");
                Shoot=home.getJSONObject("shoot");
                yourShoot=away.getJSONObject("shoot");
                myGoal=Shoot.getString("goalTotal");
                yourGoal=yourShoot.getString("goalTotal");
                intMyGoal=Integer.parseInt(myGoal);
                intYourGoal=Integer.parseInt(yourGoal);
                JSONArray shootDetail=home.getJSONArray("shootDetail");
                for(int i=0;i<shootDetail.length();i++) {
                    JSONObject shootDTL = shootDetail.getJSONObject(i);
                    if(shootDTL.getString("hitPost").equals("true")) {
                        hitPost+=1;
                    }
                }
            }
            else {
                myNick=away.getString("nickname");
                otherNick=home.getString("nickname");
                Shoot=away.getJSONObject("shoot");
                yourShoot=home.getJSONObject("shoot");
                myGoal=Shoot.getString("goalTotal");
                yourGoal=yourShoot.getString("goalTotal");
                intMyGoal=Integer.parseInt(myGoal);
                intYourGoal=Integer.parseInt(yourGoal);
                JSONArray shootDetail=away.getJSONArray("shootDetail");
                for(int i=0;i<shootDetail.length();i++) {
                    JSONObject shootDTL = shootDetail.getJSONObject(i);
                    if(shootDTL.getString("hitPost").equals("true")) {
                        hitPost+=1;
                    }
                }
            }
        }
        catch (Exception e) {
            // Error calling the rest api
            //Log.e("REST_API", "GET method failed: " + e.getMessage());
            e.printStackTrace();
        }
        return "<"+myNick+"> "+myGoal+" : "+yourGoal+" <"+otherNick+">";
    }

    public void getMatchInfo(String MatchID, String UserID){
        String result=null;
        //String longshoot1="";
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
            if (home.getString("accessId").equals(UserID)) {
                myNick=home.getString("accessId");
                otherNick=away.getString("accessId");
                Shoot=home.getJSONObject("shoot");
                yourShoot=away.getJSONObject("shoot");
                ShootTotal=Shoot.getString("shootTotal");
                shootOutPenalty=Shoot.getString("shootOutPenalty");
                shootInPenalty=Shoot.getString("shootInPenalty");
                shootHeading=Shoot.getString("shootHeading");
                myGoal=Shoot.getString("goalTotal");
                intMyGoal=Integer.parseInt(myGoal);
                yourGoal=yourShoot.getString("goalTotal");
                st=Integer.parseInt(ShootTotal);
                sop=Integer.parseInt(shootOutPenalty);
                si=Integer.parseInt(shootInPenalty);
                hd=Integer.parseInt(shootHeading);
                matchDetail=home.getJSONObject("matchDetail");
                Possesion=matchDetail.getString("possession");
                ps=Integer.parseInt(Possesion);
                controller=matchDetail.getString("controller");
                goalInPenalty=Shoot.getString("goalInPenalty");
                goalOutPenalty=Shoot.getString("goalOutPenalty");
                myGoalInPenalty=Integer.parseInt(goalInPenalty);
                myGoalOutPenalty=Integer.parseInt(goalOutPenalty);
                JSONArray shootDetail=home.getJSONArray("shootDetail");
                for(int i=0;i<shootDetail.length();i++) {
                    JSONObject shootDTL = shootDetail.getJSONObject(i);
                    if(shootDTL.getString("inPenalty").equals("true")) {
                        inShoot+=1;
                        if (shootDTL.getString("type").equals("1")) {
                            inNormalShoot += 1;
                            if(shootDTL.getString("result").equals("3")){
                                inGoal+=1;
                                inNormaGoal+=1;
                            }
                        } else if (shootDTL.getString("type").equals("2")) {
                            inZdShoot += 1;
                            if(shootDTL.getString("result").equals("3")){
                                inGoal+=1;
                                inZdGoal+=1;
                            }
                        } else if (shootDTL.getString("type").equals("3")){
                            inHeadingShoot += 1;
                            if(shootDTL.getString("result").equals("3")){
                                inGoal+=1;
                                inHeadingGoal+=1;
                            }
                        }
                    }
                    else if(shootDTL.getString("inPenalty").equals("false")) {
                        outShoot+=1;
                        if (shootDTL.getString("type").equals("1")) {
                            outNormalShoot += 1;
                            if(shootDTL.getString("result").equals("3")){
                                outGoal+=1;
                                outNormalGoal+=1;
                            }
                        } else if (shootDTL.getString("type").equals("2")) {
                            outZdShoot += 1;
                            if(shootDTL.getString("result").equals("3")){
                                outGoal+=1;
                                outZdGoal+=1;
                            }
                        } else if (shootDTL.getString("type").equals("3")){
                            outHeadingShoot += 1;
                            if(shootDTL.getString("result").equals("3")){
                                outGoal+=1;
                                outHeadingGoal+=1;
                            }
                        }
                    }
                }
            }
            else {
                myNick=away.getString("accessId");
                otherNick=home.getString("accessId");
                Shoot=away.getJSONObject("shoot");
                yourShoot=home.getJSONObject("shoot");
                myGoal=Shoot.getString("goalTotal");
                yourGoal=yourShoot.getString("goalTotal");
                intMyGoal=Integer.parseInt(myGoal);
                ShootTotal=Shoot.getString("shootTotal");
                shootOutPenalty=Shoot.getString("shootOutPenalty");
                shootInPenalty=Shoot.getString("shootInPenalty");
                shootHeading=Shoot.getString("shootHeading");
                st=Integer.parseInt(ShootTotal);
                sop=Integer.parseInt(shootOutPenalty);
                si=Integer.parseInt(shootInPenalty);
                hd=Integer.parseInt(shootHeading);
                matchDetail=away.getJSONObject("matchDetail");
                Possesion=matchDetail.getString("possession");
                ps=Integer.parseInt(Possesion);
                controller=matchDetail.getString("controller");
                goalInPenalty=Shoot.getString("goalInPenalty");
                goalOutPenalty=Shoot.getString("goalOutPenalty");
                myGoalInPenalty=Integer.parseInt(goalInPenalty);
                myGoalOutPenalty=Integer.parseInt(goalOutPenalty);
                JSONArray shootDetail=away.getJSONArray("shootDetail");
                for(int i=0;i<shootDetail.length();i++) {
                    JSONObject shootDTL = shootDetail.getJSONObject(i);
                    if(shootDTL.getString("inPenalty").equals("true")) {
                        inShoot+=1;
                        if (shootDTL.getString("type").equals("1")) {
                            inNormalShoot += 1;
                            if(shootDTL.getString("result").equals("3")){
                                inGoal+=1;
                                inNormaGoal+=1;
                            }
                        } else if (shootDTL.getString("type").equals("2")) {
                            inZdShoot += 1;
                            if(shootDTL.getString("result").equals("3")){
                                inGoal+=1;
                                inZdGoal+=1;
                            }
                        } else if (shootDTL.getString("type").equals("3")){
                            inHeadingShoot += 1;
                            if(shootDTL.getString("result").equals("3")){
                                inGoal+=1;
                                inHeadingGoal+=1;
                            }
                        }
                    }
                    else if(shootDTL.getString("inPenalty").equals("false")) {
                        outShoot+=1;
                        if (shootDTL.getString("type").equals("1")) {
                            outNormalShoot += 1;
                            if(shootDTL.getString("result").equals("3")){
                                outGoal+=1;
                                outNormalGoal+=1;
                            }
                        } else if (shootDTL.getString("type").equals("2")) {
                            outZdShoot += 1;
                            if(shootDTL.getString("result").equals("3")){
                                outGoal+=1;
                                outZdGoal+=1;
                            }
                        } else if (shootDTL.getString("type").equals("3")){
                            outHeadingShoot += 1;
                            if(shootDTL.getString("result").equals("3")){
                                outGoal+=1;
                                outHeadingGoal+=1;
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            // Error calling the rest api
            //Log.e("REST_API", "GET method failed: " + e.getMessage());
            e.printStackTrace();
        }
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