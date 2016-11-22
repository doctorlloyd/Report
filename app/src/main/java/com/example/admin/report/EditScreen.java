package com.example.admin.report;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.admin.report.pojo.Student;

import java.util.ArrayList;

/**
 * Created by Doc on 11/12/2016.
 */

public class EditScreen extends Activity implements SearchView.OnQueryTextListener{
    private HelperDb helper;
    private Button btnUpdate;
    private Intent intent;
    private SearchView search;
    private int searchId;
    private int update = -1;
    private ArrayList<String> listOfStudents = new ArrayList<String>();
    private Bundle savedInstanceState;
    private EditText etName,etSurname,etID,etCourse,etGender,etMark1,etMark2,etMark3,stdNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_screen);

        initialize();
        createDatabase();

        search.setSubmitButtonEnabled(true);
        search.setOnQueryTextListener(this);
    }
    public void createDatabase(){
        helper = new HelperDb(this);
    }

    public void initialize()
    {
        btnUpdate = (Button)findViewById(R.id.updateStudent);
        btnUpdate.setEnabled(false);
        search = (SearchView)findViewById(R.id.searchView);
        etCourse = (EditText)findViewById(R.id.Course);
        etID = (EditText)findViewById(R.id.idNo);
        etMark1 = (EditText)findViewById(R.id.subject1);
        etMark2 = (EditText)findViewById(R.id.subject2);
        etMark3 = (EditText)findViewById(R.id.subject3);
        etName = (EditText)findViewById(R.id.etName);
        etSurname = (EditText)findViewById(R.id.etSurname);
        etGender = (EditText)findViewById(R.id.etGender);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater showMe = getMenuInflater();
        showMe.inflate(R.menu.updating_menu,menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        super.onMenuItemSelected(featureId, item);
        Cursor cursor;
        String info = "";
        switch (item.getItemId()){
            case R.id.action_viewAll:
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
            case R.id.action_delete_all:
                long bb = helper.deleteAllData();
                if(bb >0)
                {
                    Toast.makeText(getBaseContext(),"data is deleted",Toast.LENGTH_LONG).show();
                    onCreate(savedInstanceState);
                }else {
                    Toast.makeText(getBaseContext(),"data Not deleted",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.action_delete:
                try{
                    searchId = Integer.parseInt(String.valueOf(search.getQuery()));
                    int delete = helper.deleteStudent(searchId);
                    if(delete == -1)
                        Toast.makeText(getBaseContext(),"Student is removed",Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getBaseContext(),"Student was not removed",Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    Toast.makeText(getBaseContext(),"Search the student first before you can delete it",Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.action_view:
                try{
                    searchId = Integer.parseInt(String.valueOf(search.getQuery()));
                    ArrayList<String> student = helper.getStudent(searchId);
                    if(student!=null){
                        info += "Name: "+student.get(0)+"\n"
                                +"Surname: "+student.get(1)+"\n"
                                +"ID no: "+student.get(7)+"\n"
                                +"Student no: "+student.get(8)+"\n"
                                +"Gender: "+student.get(6)+"\n"
                                +"Course: "+student.get(2)+"\n"
                                +"Subject 1: "+student.get(3)+"\n"
                                +"Subject 2: "+student.get(4)+"\n"
                                +"Subject 3: "+student.get(5)+"\n";
                        listOfStudents.add(info);
                        intent = new Intent("android.intent.action.RESULT");
                        intent.putStringArrayListExtra("list",listOfStudents);
                        startActivity(intent);
                        listOfStudents.clear();
                    }
                }catch (Exception e){
                    Toast.makeText(getBaseContext(),"Search the student first before you can delete it",Toast.LENGTH_LONG).show();
                }
                break;
        }
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String s){

        searchId = Integer.parseInt(s);
        try{
            ArrayList<String> cursor = helper.getStudent(searchId);
            if(cursor!=null){
                etCourse.setText(cursor.get(2));
                etSurname.setText(cursor.get(1));
                etName.setText(cursor.get(0));
                etMark1.setText(cursor.get(3));
                etMark2.setText(cursor.get(4));
                etMark3.setText(cursor.get(5));
                etGender.setText(cursor.get(6));
                etID.setText(cursor.get(7));


                etCourse.setEnabled(true);
                etSurname.setEnabled(true);
                etName.setEnabled(true);
                etMark1.setEnabled(true);
                etMark2.setEnabled(true);
                etMark3.setEnabled(true);
                btnUpdate.setEnabled(true);

                btnUpdate.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View view)
                    {
                        Student student = new Student();
                        student.setStd_Number(searchId);
                        student.setCourse(etCourse.getText().toString());
                        student.setGender(etGender.getText().toString());
                        student.setName(etName.getText().toString());
                        student.setSurname(etSurname.getText().toString());
                        student.setUser_ID(Integer.parseInt(etID.getText().toString()));
                        student.setSubject1(Integer.parseInt(etMark1.getText().toString()));
                        student.setSubject2(Integer.parseInt(etMark2.getText().toString()));
                        student.setSubject3(Integer.parseInt(etMark3.getText().toString()));
                        update = helper.updateStudent(student);
                        if(update != -1)
                        {
                            Toast.makeText(getApplicationContext(),"Student information was updated successfully",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"Information no updated",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                return true;
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"No student was found",Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }
}
