package com.example.admin.report;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.admin.report.pojo.Student;

import java.util.ArrayList;

/**
 * Created by Admin on 2016/11/10.
 */

public class HelperDb extends SQLiteOpenHelper {
    public static final String USER_NAME = "name";
    public static final String USER_SURNAME = "surname";
    public static final String USER_ID = "User_ID";
    public static final String STUDENT_NUMBER = "STD_Number";
    public static final String USER_GENDER = "gender";
    public static final String COURSE_NAME = "Course";
    public static final String SUBJECT_MARK2 = "Mark2";
    public static final String SUBJECT_MARK1 = "Mark1";
    public static final String SUBJECT_MARK3 = "Mark3";

    private static final String DATABASE = "database";
    private static final String USER_DATABASE_TABLE = "Student_details";
    private static final int DATABASE_VERSION = 1;
    private int counter = 0;

    public HelperDb(Context context) {
        super(context,DATABASE,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+USER_DATABASE_TABLE + "("+USER_ID +" NUMBER NOT NULL, "+ STUDENT_NUMBER +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                USER_NAME+" TEXT NOT NULL, "+ USER_SURNAME+" TEXT NOT NULL, " + COURSE_NAME +" TEXT NOT NULL, " + USER_GENDER +" TEXT NOT NULL, "+ SUBJECT_MARK1+" INTEGER NOT NULL, "
                +SUBJECT_MARK2 +" INTEGER NOT NULL, "+SUBJECT_MARK3+" INTEGER NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIT "+ USER_DATABASE_TABLE);
        onCreate(db);
    }
    public long insertStud(Student student)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(USER_NAME,student.getName());
        cv.put(USER_SURNAME,student.getSurname());
        cv.put(USER_ID,student.getUser_ID());
        cv.put(USER_GENDER,student.getGender());
        cv.put(COURSE_NAME,student.getCourse());
        cv.put(SUBJECT_MARK1,student.getSubject1());
        cv.put(SUBJECT_MARK2,student.getSubject2());
        cv.put(SUBJECT_MARK3,student.getSubject3());
        return db.insert(USER_DATABASE_TABLE, null,cv);
    }

    public Cursor getAllData()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+USER_DATABASE_TABLE,null);
        return cursor;
    }

    public long deleteAllData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        long bb = db.delete(USER_DATABASE_TABLE, "1", null);
        return bb;
    }
    public ArrayList<String> getStudent(int id_no)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] column =new String[] { USER_ID, STUDENT_NUMBER, USER_NAME,USER_SURNAME,COURSE_NAME,USER_GENDER,SUBJECT_MARK1,SUBJECT_MARK2,SUBJECT_MARK3};
        ArrayList<String> theString = new ArrayList<>();
        Cursor cursor = db.query
                (
                        USER_DATABASE_TABLE,column, STUDENT_NUMBER + "=" + id_no,
                        null, null, null, null, null
                );
        int name = cursor.getColumnIndex(USER_NAME);
        int surname = cursor.getColumnIndex(USER_SURNAME);
        int course = cursor.getColumnIndex(COURSE_NAME);
        int mark1 = cursor.getColumnIndex(SUBJECT_MARK1);
        int mark2 = cursor.getColumnIndex(SUBJECT_MARK2);
        int mark3 = cursor.getColumnIndex(SUBJECT_MARK3);
        int gender = cursor.getColumnIndex(USER_GENDER);
        int userID= cursor.getColumnIndex(USER_ID);
        int studentNo = cursor.getColumnIndex(STUDENT_NUMBER);
        //Cursor cursor = db.rawQuery("SELECT * FROM "+USER_DATABASE_TABLE+" WHERE STD_Number = "+ id_no+";",null);

        cursor.moveToFirst();
        theString.add(cursor.getString(name));
        theString.add(cursor.getString(surname));
        theString.add(cursor.getString(course));
        theString.add(cursor.getString(mark1));
        theString.add(cursor.getString(mark2));
        theString.add(cursor.getString(mark3));
        theString.add(cursor.getString(gender));
        theString.add(cursor.getString(userID));
        theString.add(cursor.getString(studentNo));
        return theString;
    }
    public int deleteStudent(int id_no)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(USER_DATABASE_TABLE, "id = ?", new String[] {String.valueOf(id_no)});
    }
    public int updateStudent(Student student)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(USER_NAME,student.getName());
        cv.put(USER_SURNAME,student.getSurname());
        cv.put(COURSE_NAME,student.getCourse());
        cv.put(SUBJECT_MARK1,student.getSubject1());
        cv.put(SUBJECT_MARK2,student.getSubject2());
        cv.put(SUBJECT_MARK3,student.getSubject3());
        return db.update(USER_DATABASE_TABLE,cv,"STD_Number = ?", new String[] {String.valueOf(student.getStd_Number())});
    }
}
