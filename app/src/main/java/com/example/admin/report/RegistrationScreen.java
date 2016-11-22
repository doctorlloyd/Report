package com.example.admin.report;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.admin.report.pojo.Student;

import java.util.ArrayList;

public class RegistrationScreen extends Activity implements View.OnClickListener{
    private HelperDb helper;
    private Spinner spinner;
    private Button btnRegister;
    private Bundle savedInstanceState;
    private ArrayList<String> listOfStudents = new ArrayList<String>();
    private EditText etName,etSurname,etID,etCourse,etMark1,etMark2,etMark3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_screen);
        this.savedInstanceState = savedInstanceState;
        createDatabase();
        spinner = (Spinner) findViewById(R.id.spGender);
        //Creating an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.gender_array, android.R.layout.simple_spinner_item);
        //Specifying the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Applying the adapter to the spinner
        spinner.setAdapter(adapter);
        btnRegister = (Button)findViewById(R.id.adminRegister);
        btnRegister.setOnClickListener(this);

    }
    public void createDatabase(){
        helper = new HelperDb(this);
    }
    public void insertStud(String gender)
    {
        initialize();
        /*
        ***
         */
        Student student = new Student();
        try{
            student.setCourse(etCourse.getText().toString());
            student.setGender(gender);
            student.setName(etName.getText().toString());
            student.setSurname(etSurname.getText().toString());
            student.setUser_ID(Integer.parseInt(etID.getText().toString()));
            student.setSubject1(Integer.parseInt(etMark1.getText().toString()));
            student.setSubject2(Integer.parseInt(etMark2.getText().toString()));
            student.setSubject3(Integer.parseInt(etMark3.getText().toString()));

            long insert = helper.insertStud(student);
            if(insert >0)
            {
                Toast.makeText(getBaseContext(),"data is inserted",Toast.LENGTH_LONG).show();
                onCreate(savedInstanceState);
            }else {
                Toast.makeText(getBaseContext(),"data Not inserted",Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            Toast.makeText(getBaseContext(),"please verify your inputs",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.adminRegister:
                String gender = (String) spinner.getSelectedItem();
                insertStud(gender);
                break;
        }
    }
    public void initialize()
    {
        etCourse = (EditText)findViewById(R.id.Course);
        etID = (EditText)findViewById(R.id.idNo);
        etMark1 = (EditText)findViewById(R.id.subject1);
        etMark2 = (EditText)findViewById(R.id.subject2);
        etMark3 = (EditText)findViewById(R.id.subject3);
        etName = (EditText)findViewById(R.id.etName);
        etSurname = (EditText)findViewById(R.id.etSurname);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater showMe = getMenuInflater();
        showMe.inflate(R.menu.registration_menu,menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        super.onMenuItemSelected(featureId, item);

        Cursor cursor;
        Intent intent;
        String info = "";

        switch (item.getItemId()){
            case R.id.action_viewAll:
                /*
                ***
                 */
                cursor = helper.getAllData();
                while(cursor.moveToNext())
                {
                    info += "Name: "+cursor.getString(2)+"\n"
                                +"Surname: "+cursor.getString(3)+"\n"
                                +"ID no: "+cursor.getLong(0)+"\n"
                                +"Student no: "+cursor.getInt(1)+"\n"
                                +"Gender: "+cursor.getString(5)+"\n"
                                +"Course: "+cursor.getString(4)+"\n"
                                +"Subject 1: "+cursor.getInt(6)+"\n"
                                +"Subject 2: "+cursor.getInt(7)+"\n"
                                +"Subject 3: "+cursor.getInt(8)+"\n";
                    listOfStudents.add(info);
                    info = null;
                }
                /*
                ***
                 */
                intent = new Intent("android.intent.action.RESULT");
                intent.putStringArrayListExtra("list",listOfStudents);
                startActivity(intent);
                listOfStudents.clear();
                break;
            case R.id.action_update:
                /*
                ***
                 */
                intent = new Intent("android.intent.action.UPDATE");
                startActivity(intent);
                break;
            case R.id.action_delete_all:
                /*
                ***
                 */
                long bb = helper.deleteAllData();
                if(bb >0)
                {
                    Toast.makeText(getBaseContext(),"data is deleted",Toast.LENGTH_LONG).show();
                    onCreate(savedInstanceState);
                }else {
                    Toast.makeText(getBaseContext(),"data Not deleted",Toast.LENGTH_LONG).show();
                }
                break;
        }
        return false;
    }
}
