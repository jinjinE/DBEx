package emirim0223.kr.hs.emirim.dbx;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText editName, editCount, editResultName, editResultCount;
    Button butInit, butInsert, butSelect;
    MyDBHelper myDb;
    SQLiteDatabase sqlDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editName = (EditText)findViewById(R.id.edit_group_name);
        editCount = (EditText)findViewById(R.id.edit_result_count);
        editResultName = (EditText)findViewById(R.id.edit_result_name);
        editResultCount = (EditText)findViewById(R.id.edit_result_count);

        butInit = (Button)findViewById(R.id.but_init);
        butInsert = (Button)findViewById(R.id.but_insert);
        butSelect = (Button)findViewById(R.id.but_select);


        //DB생성
        myDb = new MyDBHelper(this);
        //기존의 테이블이 존재하면 삭제하고 테이블을 새로 생성한다.
        butInit.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                sqlDb = myDb.getWritableDatabase();
                String sql = "insert into idalTable values('"+editName.getText()+"', "+editCount.getText()+")";
                myDb.onUpgrade(sqlDb,1,2);
                sqlDb.execSQL(sql);
                sqlDb.close();
                Toast.makeText(MainActivity.this,"저장됨",Toast.LENGTH_LONG).show();
            }
        });
        butSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqlDb=myDb.getReadableDatabase();
                String sql = "select * from idolTable";
                Cursor cursor = sqlDb.rawQuery(sql, null);
                String names = "Idol 이름"+"\r\n"+"==========="+"\r\n";
                String counts = "Idol 인원수"+"\r\n"+"==========="+"\r\n";
                while(cursor.moveToNext()){

                }
            }
        });
    }



    class MyDBHelper extends SQLiteOpenHelper {
        //idolDB라는 이름의 데이터베이스가 생성된다.
        public MyDBHelper(Context context) {
            super(context, "idolDB", null, 1);
        }
        // idolTabel라는 이름의 테이블 생성
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            String sql = "Create table idolTable(idolname text not null primary key, idolCount integer)";
            sqLiteDatabase.execSQL(sql);
        }
        // 이미 idalTable이 존재한다면 기존의 테이블을 삭제하고 새로 테이블을 만들 때 호출
        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            String sql = "drop table if exist idalTable";
            sqLiteDatabase.execSQL(sql);
            onCreate(sqLiteDatabase);
        }


    }
}
