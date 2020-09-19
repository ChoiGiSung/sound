package com.example.sound_mainpage.SoundCollection;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sound_mainpage.Api.ApiService;
import com.example.sound_mainpage.Api.ApiDto.DataDto;
import com.example.sound_mainpage.R;
import com.example.sound_mainpage.SuperUser;

import java.util.ArrayList;

public class Sound_collection extends AppCompatActivity {
    private ApiService apiService=new ApiService();

    ListView listView;
    MyListAdapter myListAdapter;
    ArrayList<list_item> list_itemArrayList;
    Button btn_makelist;
    String push="";//db에 넣을 값

    //다이어로그
    EditText edit_btnname;
    SeekBar edit_seek;
    public void init(){
        listView=findViewById(R.id.listview);
        btn_makelist=findViewById(R.id.btn_makelist);
        list_itemArrayList=new ArrayList<>();
        list_itemArrayList.clear();
        //소리객체 만들기

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sound_collection);

        SuperUser superUser=SuperUser.getSuperUser();
        Log.i("수퍼유조 컬렉션",superUser.getUser_id());


        init();
        //어뎁터에 item의 정보를 넣어주면 어댑터에서 가공해서 리스트에 뿌려준다
        //returnDB();//db에서 값 받아서 리스트에 넣기
        returnApi();

        myListAdapter=new MyListAdapter(Sound_collection.this,list_itemArrayList);
        listView.setAdapter(myListAdapter);

        //길게 누르면 삭제
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                myListAdapter.removeself(position);
                myListAdapter.notifyDataSetChanged();//notifyDataSetChanged만 하는게 아닌 setAdapter를 다시 넣어 줘야지 update가 된다
                listView.setAdapter(myListAdapter);

                String push= insertDB();//아이템이 삭제되면 string에 넣기

                return false;
            }
        });

        btn_makelist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dig=new AlertDialog.Builder(Sound_collection.this);
                dig.setTitle("사용자 버튼 생성");
                final View dialogView = View.inflate(Sound_collection.this,R.layout.dialog_makelist,null);
                dig.setView(dialogView);

//                확인 버튼이 눌리면
                dig.setPositiveButton("만들기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        edit_btnname=dialogView.findViewById(R.id.edit_btnname);
                        edit_seek=dialogView.findViewById(R.id.edit_seek);

                        String edit_list_btbname =edit_btnname.getText().toString();
                        int edit_list_seek =edit_seek.getProgress()/6;//소리의 맥스값이 15라서 6으로 나눠서 넣기
                        //리스트에 추가
                        list_itemArrayList.add(new list_item(edit_list_btbname,edit_list_seek));

                       String push= insertDB();//아이템이 추가되면 string에 넣기

                        //일단 확인버튼이 눌리면 db에 값 올리기
                        //sendSetingDB();
                        sendSetingApi();
                    }
                });
                dig.show();
            }
        });
    }


//    @Override
//    protected void onPause() {
//        super.onPause();
//        insertDB();
//        sendSetingApi();
//        //sendSetingDB();
//    }

    @Override
    protected void onStop() {
        super.onStop();
        insertDB();
        //sendSetingDB();
        sendSetingApi();
    }

    public String insertDB(){
        //db에 넣을 값 최신화
         push="";
        for(list_item a :list_itemArrayList ){
            push+=a.getBtn_name()+",";
            push+=a.getSeek_bar()+"/";
        }
        return push;
    }

    //db에 설정 보내기
//    public void sendSetingDB(){
//        Intent intent= getIntent();
//        String userid=intent.getStringExtra("userid");
//
//        try{
//
//            String result = new CustomTask().execute(userid,push,"seting","").get();
//            result=result.trim();
//            //Toast.makeText(Sound_collection.this,result,Toast.LENGTH_SHORT).show();
//            Log.i("디비로",result);
//
//        }catch (Exception e){e.printStackTrace(); }
//    }

    //db에 api보내기
    public void sendSetingApi(){
        Intent intent= getIntent();
        String userid=intent.getStringExtra("userid");
        try{

            new ApiService().execute("updateSeting",userid,push).get();
            Log.i("컬렉션",push);
        }catch (Exception e){e.printStackTrace(); }
    }


    //api 사용해서 값 얻어오기
    private void returnApi() {
        Intent intent = getIntent();
        String userid = intent.getStringExtra("userid");
        DataDto findData = null;
        try {
            findData = apiService.execute("getUserData", userid).get();
            Log.i("리절트", findData.getData().get(0).getUser_seting());
            String result = findData.getData().get(0).getUser_seting();
            //여기서 "" 가 늘어난다
            Log.i("리절트", result);
            if (!result.equals("0")) {
                String[] result_split = result.split("/"); //db값을 하나의 문자열로 받아서 자른다
                for (String a : result_split) {
                    String[] result2 = a.split(",");
                    list_itemArrayList.add(new list_item(result2[0], Integer.parseInt(result2[1])));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private void returnDB(){
//        Intent intent= getIntent();
//        String userid=intent.getStringExtra("userid");
//        //db로 보내기
//        String result=null;
//        try {
//            result = new CustomTask().execute(userid,"getseting","","","").get();
//           // Log.i("갔냐dgetsetin?","ㅇ");
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        result=result.trim();
//
//        if(!result.equals("0")){
//            String [] result_split =result.split("/"); //db값을 하나의 문자열로 받아서 자른다
//        try{
//            for(String a: result_split){
//                String[] result2= a.split(",");
//                    list_itemArrayList.add(new list_item(result2[0],Integer.parseInt(result2[1])));
//            }
//        }catch (Exception e){
//
//        }
//        }
//
//
//      //  Log.i("왔다 성공",result);
//       // Toast.makeText(getApplication(),result,Toast.LENGTH_SHORT).show();
//    }
}

