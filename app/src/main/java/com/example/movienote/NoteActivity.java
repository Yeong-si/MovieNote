package com.example.movienote;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RatingBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.movienote.databinding.ActivityNoteBinding;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class NoteActivity extends AppCompatActivity {

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    CollectionReference noteReference;
    FirebaseFirestore db;
    SharedPreferences shDb;
    SharedPreferences.Editor editor;


    private AlertDialog listDialog;
    private DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (dialog == listDialog) {
                //목록 dialog의 항목이 선택되었을 때 항목 문자열 획득
                String[] data = getResources().getStringArray(R.array.dialog_array);
                showToast(data[which] + " 선택 하셨습니다.");
            }
        }
    };
        ActivityNoteBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        NoteDBHelper helper = new NoteDBHelper(this);
        SQLiteDatabase SQLitedb = helper.getReadableDatabase();

        binding = ActivityNoteBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        String check = intent.getStringExtra("check");
        String posterUrl = intent.getStringExtra("image");
        String movieTitle = intent.getStringExtra("title");
        String calendar = intent.getStringExtra("calendar");
        float rating = intent.getFloatExtra("rating",0);
        boolean visible = intent.getBooleanExtra("visible",true);
        String genre = intent.getStringExtra("genre");
        String noteTitle = intent.getStringExtra("NoteTitle");
        String comment = intent.getStringExtra("comment");
        String note = intent.getStringExtra("note");

        binding.moviename.setText(movieTitle);
        Glide.with(this)
                .load(posterUrl)
                .into(binding.poster);

        //어댑터에서 왔을 경우
        if (check.equals("modify")){
            binding.date.setText(calendar);
            binding.ratingStar.setRating(rating);
            if(visible == true){
                //비공개
                binding.openNote.setVisibility(View.INVISIBLE);
                binding.closedNote.setVisibility(View.VISIBLE);
            }else {
                //공개
                binding.openNote.setVisibility(View.VISIBLE);
                binding.closedNote.setVisibility(View.INVISIBLE);
            }
            binding.genreChoicebtn.setText(genre);
            binding.noteTitle.setText(noteTitle);
            binding.comment.setText(comment);
            binding.note.setText(note);
        }

        //평점 변경
        binding.ratingStar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingBar.setRating(rating);
            }
        });

        //캘린더 날짜 선택
        binding.dateBtn.setOnClickListener(view -> {
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dateDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear,
                                              int dayOfMonth) {
                           binding.date.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                        }
                    }, year, month, day);

            dateDialog.show();
        });

        //공개 비공개 버튼
        //추후 공개.비공개 여부로 sns 검색창에 뜰지말지 결정하기.
        //노트가 파이어베이스로 넘어가서 서버에 저장가능하도록 해보기

        binding.openBtn.setOnClickListener(view -> {
            if(binding.openNote.getVisibility() == View.VISIBLE){
                binding.openNote.setVisibility(View.INVISIBLE);
                binding.closedNote.setVisibility(View.VISIBLE);
            }else {
                binding.openNote.setVisibility(View.VISIBLE);
                binding.closedNote.setVisibility(View.INVISIBLE);
            }
        });

        binding.genreChoicebtn.setOnClickListener(view -> {
            // 목록 다이얼로그 띄우기
            listDialog = new AlertDialog.Builder(this)
                    .setTitle("장르 선택")
                    .setSingleChoiceItems(R.array.dialog_array, 0, dialogListener)
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 선택한 항목의 인덱스 가져오기
                            int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();

                            // 리소스에서 항목 배열 가져오기
                            String[] genres = getResources().getStringArray(R.array.dialog_array);

                            // 선택한 항목을 기반으로 genre 텍스트 업데이트
                            if (selectedPosition >= 0 && selectedPosition < genres.length) {
                                binding.genreChoicebtn.setText(genres[selectedPosition]);
                                Log.d("MyApp", "Genre updated: " + genres[selectedPosition]);
                            } else {
                                Log.e("MyApp", "Invalid position: " + selectedPosition);
                            }

                            // 다이얼로그 닫기
                            listDialog.dismiss();
                        }
                    })
                    .setNegativeButton("취소", null)
                    .create();

            listDialog.show();
        });
        Log.d("LSY","어디서 온 : "+getIntent().getStringExtra("what"));


        //업로드 후 노트화면으로 가기(업로드부분 짜야함)
        binding.upload.setOnClickListener(new View.OnClickListener() {
            Note note;
            @Override
            public void onClick(View v) {
                Log.d("LSY","어디서 온 : "+getIntent().getStringExtra("what"));

                SQLitedb.execSQL("DELETE FROM tb_memo WHERE title=? AND poster=?", new String[]{movieTitle, posterUrl});

                boolean visible;
                shDb = getSharedPreferences("FinishedNote",MODE_PRIVATE);
                editor = shDb.edit();

                //1번이 비어있으면 = 아무것도 없다는 소리 -> 1번에 넣고 끝내기
                //2번만 비어있으면 = 2번에 넣으면 됨
                // 둘 다 안 비어있다면 2번을 1번으로 넘기고 새로 업로드할 것을 2번에 넣기
                String data1Title = shDb.getString("data1Title", "none");
                String data1Image = shDb.getString("data1Image", "none");
                String data2Title = shDb.getString("data2Title","none");
                String data2Image = shDb.getString("data2Image", "none");
//                String data3Title = shDb.getString("data3Title","none");
//                String data3Image = shDb.getString("data3Image", "none");
                if (data1Title.equals("none")){
                    editor.putString("data1Title",binding.moviename.getText().toString());
                    editor.putString("data1Image",posterUrl);
                    editor.commit();
                    Log.d("LSY", "데이터1실행");
                    Log.d("LSY", shDb.getString("data1Title", "none"));

                } else if (data2Title.equals("none")) {
                    editor.putString("data2Title",binding.moviename.getText().toString());
                    editor.putString("data2Image",posterUrl);
                    editor.commit();
                    Log.d("LSY", "데이터2실행");
                    Log.d("LSY", shDb.getString("data1Title", "none"));
                    Log.d("LSY", shDb.getString("data2Title", "none"));
                } else {
                    /*editor.putString("data3Title",data1Title);
                    editor.putString("data3Image",data1Image);*/

                    editor.putString("data1Title",data2Title);
                    editor.putString("data1Image",data2Image);

                    editor.putString("data2Title",binding.moviename.getText().toString());
                    editor.putString("data2Image",posterUrl);
                    editor.commit();
                    Log.d("LSY", "데이터3실행");
                }


                if (binding.openNote.getVisibility() == View.VISIBLE){
                    visible = true;
                }else {
                    visible =false;
                }

                note = new Note(posterUrl,currentUser.getUid().toString(),currentUser.getDisplayName(),binding.moviename.getText().toString(),binding.date.getText().toString(),
                        binding.ratingStar.getRating(),!visible,binding.noteTitle.getText().toString(),
                        binding.comment.getText().toString(),binding.note.getText().toString(), LocalDateTime.now(), binding.genreChoicebtn.getText().toString());

                //note.setNoteTitle(binding.moviename1.getText().toString());
                //note.set
                noteReference = FirebaseFirestore.getInstance().collection("Note");
                Map<String, Object> data1 = new HashMap<>();
                data1.put("poster",posterUrl);
                data1.put("uid",currentUser.getUid());
                data1.put("writer", note.getWriter());
                data1.put("movieTitle", note.getMovieTitle());
                data1.put("note", note.getNote());
                data1.put("noteTitle", note.getNoteTitle());
                data1.put("comment",note.getComment());
                data1.put("rating",note.getRating());
                data1.put("calendar",note.getCalendar());
                data1.put("invisible",note.isVisible());
                data1.put("genre",note.getGenre());

                noteReference.document(note.getTimestamp().toString()).set(data1);

                startActivity(new Intent(NoteActivity.this, FinishedMovieActivity.class));
                //finish();
            }
        });


    }
    //public void addNote(String writer, String movieTitle, String calendar, float rating, boolean visible, String noteTitle, String comment, String note){
    //}
        //Note note = new Note();

    private void showToast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }
}