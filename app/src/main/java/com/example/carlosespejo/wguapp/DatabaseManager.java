package com.example.carlosespejo.wguapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlosespejo on 12/16/17.
 */

public class DatabaseManager extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "wgu_app.db";

    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");

    /**
     * Table names
     */

    public static final String TABLE_TERMS = "terms";
    public static final String TABLE_COURSES = "courses";
    public static final String TABLE_ASSESSMENTS = "assessments";
    public static final String TABLE_MENTORS = "mentors";

    /**
     * Table Columns
     */

    /**
     * TERMS Table
     */
    public static final String TERM_ID_FIELD = "_id";
    public static final String TERM_TITLE_FIELD = "title";
    public static final String TERM_START_DATE_FIELD = "start_date";
    public static final String TERM_END_DATE_FIELD = "end_date";


    /**
     * COURSES Table
     */
    public static final String COURSE_ID_FIELD = "_id";
    public static final String COURSE_TERM_ID_FIELD = "term_id";
    public static final String COURSE_TITLE_FIELD = "title";
    public static final String COURSE_START_DATE_FIELD = "start_date";
    public static final String COURSE_END_DATE_FIELD = "end_date";
    public static final String COURSE_STATUS_FIELD = "status";
    public static final String COURSE_NOTES_FIELD = "notes";

    /**
     * MENTORS Table
     */
    public static final String MENTOR_ID_FIELD = "_id";
    public static final String MENTOR_COURSE_ID_FIELD = "course_id";
    public static final String MENTOR_NAME_FIELD = "name";
    public static final String MENTOR_PHONE_FIELD = "phone";
    public static final String MENTOR_EMAIL_FIELD = "email";

    /**
     * ASSESSMENTS TABLE
     */
    public static final String ASSESSMENT_ID_FIELD = "_id";
    public static final String ASSESSMENT_COURSE_ID_FIELD = "course_id";
    public static final String ASSESSMENT_TITLE_FIELD = "title";
    public static final String ASSESSMENT_DUE_DATE_FIELD = "due_date";
    public static final String ASSESSMENT_IS_OBJECTIVE_FIELD = "is_objective";
    public static final String ASSESSMENT_GOAL_DATE_FIELD = "goal_date";

    /**
     * CONSTRUCTOR
     * @param context
     * @param name
     * @param factory
     * @param version
     */
    public DatabaseManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d("db", "onCreate");

        //CREATE TERMS TABLE
        String create_terms = "CREATE TABLE " + TABLE_TERMS
                + " (" + TERM_ID_FIELD + " INTEGER, "
                + TERM_TITLE_FIELD + " TEXT,"
                + TERM_START_DATE_FIELD + " TEXT,"
                + TERM_END_DATE_FIELD + " TEXT,"
                + " PRIMARY KEY (" + TERM_ID_FIELD + "));";

        //CREATE COURSES TABLE
        String create_courses = "CREATE TABLE " + TABLE_COURSES
                + " (" + COURSE_ID_FIELD + " INTEGER, "
                + COURSE_TERM_ID_FIELD + " INTEGER,"
                + COURSE_TITLE_FIELD + " TEXT,"
                + COURSE_START_DATE_FIELD + " TEXT,"
                + COURSE_END_DATE_FIELD + " TEXT,"
                + COURSE_STATUS_FIELD + " TEXT,"
                + COURSE_NOTES_FIELD + " TEXT,"
                + " PRIMARY KEY (" + COURSE_ID_FIELD + ")"
                + " FOREIGN KEY (" + COURSE_TERM_ID_FIELD + ") REFERENCES terms(_id));";

        //CREATE MENTORS TABLE
        String create_mentors = "CREATE TABLE " + TABLE_MENTORS
                + " (" + MENTOR_ID_FIELD + " INTEGER, "
                + MENTOR_COURSE_ID_FIELD + " INTEGER,"
                + MENTOR_NAME_FIELD + " TEXT,"
                + MENTOR_PHONE_FIELD + " TEXT,"
                + MENTOR_EMAIL_FIELD + " TEXT,"
                + " PRIMARY KEY (" + MENTOR_ID_FIELD + ")"
                + " FOREIGN KEY (" + MENTOR_COURSE_ID_FIELD + ") REFERENCES courses(_id));";

        //CREATE ASSESSMENTS TABLE
        String create_assessments = "CREATE TABLE " + TABLE_ASSESSMENTS
                + " (" + ASSESSMENT_ID_FIELD + " INTEGER, "
                + ASSESSMENT_COURSE_ID_FIELD + " INTEGER,"
                + ASSESSMENT_TITLE_FIELD + " TEXT,"
                + ASSESSMENT_DUE_DATE_FIELD + " TEXT,"
                + ASSESSMENT_GOAL_DATE_FIELD + " TEXT,"
                + ASSESSMENT_IS_OBJECTIVE_FIELD + " INTEGER,"
                + " PRIMARY KEY (" + ASSESSMENT_ID_FIELD + ")"
                + " FOREIGN KEY (" + ASSESSMENT_COURSE_ID_FIELD + ") REFERENCES courses(_id));";

        //EXECUTE THE QUERIES
        db.execSQL(create_terms);
        db.execSQL(create_courses);
        db.execSQL(create_mentors);
        db.execSQL(create_assessments);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("db", "onUpdate");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TERMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MENTORS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ASSESSMENTS);

        // re-create the table
        onCreate(db);
    }



    public void addTerm(Term term){

        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(TERM_TITLE_FIELD, term.getTitle());
        values.put(TERM_START_DATE_FIELD, term.getStartDateFormatted());
        values.put(TERM_END_DATE_FIELD, term.getEndDateFormatted());

        db.insert("terms", null, values);

    }

    public void addCourse(Course course){

        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();


        values.put(COURSE_TERM_ID_FIELD, course.getTermId());
        values.put(COURSE_TITLE_FIELD, course.getTitle());
        values.put(COURSE_START_DATE_FIELD, course.getStartDateFormatted());
        values.put(COURSE_END_DATE_FIELD, course.getEndDateFormatted());
        values.put(COURSE_STATUS_FIELD, course.getStatus());
        values.put(COURSE_NOTES_FIELD, course.getNotes());

        db.insert("courses", null, values);

    }

    public void addMentor(CourseMentor mentor){

        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();


        values.put(MENTOR_COURSE_ID_FIELD, mentor.getCourseId());
        values.put(MENTOR_NAME_FIELD, mentor.getName());
        values.put(MENTOR_PHONE_FIELD, mentor.getPhoneNumber());
        values.put(MENTOR_EMAIL_FIELD, mentor.getEmail());

        db.insert("mentors", null, values);

    }

    public void addAssessment(Assessment assessment){

        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();

        values.put(ASSESSMENT_COURSE_ID_FIELD, assessment.getCourseId());
        values.put(ASSESSMENT_TITLE_FIELD, assessment.getTitle());
        values.put(ASSESSMENT_DUE_DATE_FIELD, assessment.getDueDateFormatted());
        values.put(ASSESSMENT_IS_OBJECTIVE_FIELD, assessment.isObjectiveBit());
        values.put(ASSESSMENT_GOAL_DATE_FIELD, assessment.getGoalDateFormatted());

        db.insert("assessments", null, values);

    }

    public void deleteTerm(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TERMS, TERM_ID_FIELD + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }

    public void deleteCourse(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_COURSES, COURSE_ID_FIELD + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }

    public void deleteMentor(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MENTORS, MENTOR_ID_FIELD + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }

    public void deleteAssessment(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ASSESSMENTS, ASSESSMENT_ID_FIELD + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }

    // Getting single term
    Term getTerm(long id) throws ParseException {
        Log.d("dannyProblem", "entering database get method");
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TERMS, new String[] {
                        TERM_ID_FIELD, TERM_TITLE_FIELD, TERM_START_DATE_FIELD,
                        TERM_END_DATE_FIELD}, TERM_ID_FIELD + "=?",
                new String[] { String.valueOf(id) }, null,
                null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();

            Term term = new Term(
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3));
            term.setId(cursor.getLong(0));

            return term;
        }
        return null;
    }

    // Getting single Course
    Course getCourse(long id) throws ParseException {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_COURSES, new String[] {
                        COURSE_ID_FIELD,COURSE_TERM_ID_FIELD, COURSE_TITLE_FIELD, COURSE_START_DATE_FIELD,
                        COURSE_END_DATE_FIELD, COURSE_STATUS_FIELD, COURSE_NOTES_FIELD}, COURSE_ID_FIELD + "=?",
                new String[] { String.valueOf(id) }, null,
                null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            Course course = new Course(
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6));
            course.setId(cursor.getLong(0));
            course.setTermId(cursor.getLong(1));
            return course;
        }
        return null;
    }

    // Getting single Mentor
    CourseMentor getMentor(long id) throws ParseException {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_MENTORS, new String[] {
                        MENTOR_ID_FIELD, MENTOR_COURSE_ID_FIELD, MENTOR_NAME_FIELD,
                        MENTOR_PHONE_FIELD, MENTOR_EMAIL_FIELD}, MENTOR_ID_FIELD + "=?",
                new String[] { String.valueOf(id) }, null,
                null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            CourseMentor mentor = new CourseMentor(
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4));
            mentor.setId(cursor.getLong(0));
            mentor.setCourseId(cursor.getLong(1));
            return mentor;
        }
        return null;
    }

    // Getting single term
    Assessment getAssessment(long id) throws ParseException {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ASSESSMENTS, new String[] {
                        ASSESSMENT_ID_FIELD, ASSESSMENT_COURSE_ID_FIELD, ASSESSMENT_TITLE_FIELD,
                        ASSESSMENT_DUE_DATE_FIELD, ASSESSMENT_GOAL_DATE_FIELD, ASSESSMENT_IS_OBJECTIVE_FIELD}, ASSESSMENT_ID_FIELD + "=?",
                new String[] { String.valueOf(id) }, null,
                null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            Assessment assessment = new Assessment(
                    cursor.getString(2),
                   cursor.getString(3),
                    cursor.getString(4),
                    cursor.getInt(5));
            assessment.setId(cursor.getLong(0));
            assessment.setCourseId(cursor.getLong(1));

            return assessment;
        }
        return null;
    }

    // Getting All Terms
    public List<Term> getAllTerms() throws ParseException {
        List<Term> terms = new ArrayList<Term>();
        String selectQuery = "SELECT * FROM " + TABLE_TERMS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {
            Term term = new Term();
            term.setId(Integer.parseInt(cursor.getString(0)));
            term.setTitle(cursor.getString(1));
            term.setStartDateWithParser(cursor.getString(2));
            term.setEndDateWithParser(cursor.getString(3));
            terms.add(term);
        }
        return terms;
    }

    // Getting All Courses
    public List<Course> getAllCourses() throws ParseException {
        List<Course> courses = new ArrayList<Course>();
        String selectQuery = "SELECT * FROM " + TABLE_COURSES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {
            Course course = new Course();
            course.setId(Integer.parseInt(cursor.getString(0)));
            course.setTermId(Integer.parseInt(cursor.getString(1)));
            course.setTitle(cursor.getString(2));
            course.setStartDateWithParser(cursor.getString(3));
            course.setEndDateWithParser(cursor.getString(4));
            course.setStatus(cursor.getString(5));
            course.setNotes(cursor.getString(6));
            courses.add(course);
        }
        return courses;
    }

    // Getting All Courses
    public int  getCoursesCountWithStatus(String status) throws ParseException {
        List<Course> courses = new ArrayList<Course>();
        String selectQuery = "SELECT * FROM " + TABLE_COURSES   + " WHERE " + COURSE_STATUS_FIELD + " = " + status;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {
            Course course = new Course();
            course.setId(Integer.parseInt(cursor.getString(0)));
            course.setTermId(Integer.parseInt(cursor.getString(1)));
            course.setTitle(cursor.getString(2));
            course.setStartDateWithParser(cursor.getString(3));
            course.setEndDateWithParser(cursor.getString(4));
            course.setStatus(cursor.getString(5));
            course.setNotes(cursor.getString(6));
            courses.add(course);
        }
        return courses.size();
    }



    public void deleteMentorsForCourse(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MENTORS, MENTOR_COURSE_ID_FIELD + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }


    public void  deleteAsmForCourse(Long  id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ASSESSMENTS, ASSESSMENT_COURSE_ID_FIELD + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }

    // Getting count for courses for given term id
    public int getCourseCountForTerm(long termId) throws ParseException {
        List<Course> courses = new ArrayList<Course>();
        String selectQuery = "SELECT * FROM " + TABLE_COURSES  + " WHERE " + COURSE_TERM_ID_FIELD + " = " + termId;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        int cnt = cursor.getCount();
        cursor.close();
        return cnt;

    }

    // Getting All Course Mentors
    public List<CourseMentor> getAllMentors() {
        List<CourseMentor> mentors = new ArrayList<CourseMentor>();
        String selectQuery = "SELECT * FROM " + TABLE_MENTORS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {
            CourseMentor mentor = new CourseMentor();
            mentor.setId(Integer.parseInt(cursor.getString(0)));
            mentor.setCourseId(Integer.parseInt(cursor.getString(1)));
            mentor.setName(cursor.getString(2));
            mentor.setPhoneNumber(cursor.getString(3));
            mentor.setEmail(cursor.getString(4));
            mentors.add(mentor);
        }
        return mentors;
    }

    // Getting All Terms
    public List<Assessment> getAllAssessments() throws ParseException {
        List<Assessment> assessments = new ArrayList<Assessment>();
        String selectQuery = "SELECT * FROM " + TABLE_ASSESSMENTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {
            Assessment assessment = new Assessment();
            assessment.setId(Integer.parseInt(cursor.getString(0)));
            assessment.setCourseId(Integer.parseInt(cursor.getString(1)));
            assessment.setTitle(cursor.getString(2));
            assessment.setDueDateWithParser(cursor.getString(3));
            assessment.setGoalDateFormatted(cursor.getString(4));
            assessment.setObjectiveBit(cursor.getInt(5));
            assessments.add(assessment);
        }
        return assessments;
    }

    // Getting All Terms
    public ArrayList<Assessment> getAllAssessmentsForCourse(long courseId) throws ParseException {
        ArrayList<Assessment> assessments = new ArrayList<Assessment>();
        String selectQuery = "SELECT * FROM " + TABLE_ASSESSMENTS +" WHERE " + ASSESSMENT_COURSE_ID_FIELD + " = " + courseId;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {
            Assessment assessment = new Assessment();
            assessment.setId(Integer.parseInt(cursor.getString(0)));
            assessment.setCourseId(Integer.parseInt(cursor.getString(1)));
            assessment.setTitle(cursor.getString(2));
            assessment.setDueDateWithParser(cursor.getString(3));
            assessment.setGoalDateFormatted(cursor.getString(4));
            assessment.setObjectiveBit(cursor.getInt(5));
            assessments.add(assessment);
        }
        return assessments;
    }



    public int updateTerm(Term term) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(TERM_TITLE_FIELD, term.getTitle());
        values.put(TERM_START_DATE_FIELD, term.getStartDateFormatted());
        values.put(TERM_END_DATE_FIELD, term.getEndDateFormatted());

        return db.update(TABLE_TERMS, values, TERM_ID_FIELD + " = ?",
                new String[] { String.valueOf(term.getId()) });
    }

    public int updateCourse(Course course) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COURSE_TITLE_FIELD, course.getTitle());
        values.put(COURSE_START_DATE_FIELD, course.getStartDateFormatted());
        values.put(COURSE_END_DATE_FIELD, course.getEndDateFormatted());
        values.put(COURSE_STATUS_FIELD, course.getStatus());
        values.put(COURSE_NOTES_FIELD, course.getNotes());

        return db.update(TABLE_COURSES, values, COURSE_ID_FIELD + " = ?",
                new String[] { String.valueOf(course.getId()) });
    }

    public int updateMentor(CourseMentor mentor) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();



        values.put(MENTOR_NAME_FIELD, mentor.getName());
        values.put(MENTOR_PHONE_FIELD, mentor.getPhoneNumber());
        values.put(MENTOR_EMAIL_FIELD, mentor.getEmail());

        return db.update(TABLE_MENTORS, values, MENTOR_ID_FIELD + " = ?",
                new String[] { String.valueOf(mentor.getId()) });
    }

    public int updateAssessment(Assessment assessment) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();


        values.put(ASSESSMENT_TITLE_FIELD, assessment.getTitle());
        values.put(ASSESSMENT_DUE_DATE_FIELD, assessment.getDueDateFormatted());
        values.put(ASSESSMENT_GOAL_DATE_FIELD, assessment.getGoalDateFormatted());
        values.put(ASSESSMENT_IS_OBJECTIVE_FIELD, assessment.isObjectiveBit());


        return db.update(TABLE_ASSESSMENTS, values, ASSESSMENT_ID_FIELD + " = ?",
                new String[] { String.valueOf(assessment.getId()) });
    }

    public Cursor getTermsCursor() {
        String selectQuery = "SELECT * FROM " + TABLE_TERMS;
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery(selectQuery, null);
    }

    public Cursor getCoursesCursor() {
        String selectQuery = "SELECT * FROM " + TABLE_COURSES;
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery(selectQuery, null);
    }

    public Cursor getCoursesCursorForTerm(long id) {
        Log.d("dannyproblem", "get cursor");
        String selectQuery = "SELECT * FROM " + TABLE_COURSES + " WHERE " + COURSE_TERM_ID_FIELD + " = " + id;
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery(selectQuery, null);
    }

    public Cursor getMentorsCursor() {
        String selectQuery = "SELECT * FROM " + TABLE_MENTORS;
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery(selectQuery, null);
    }

    public Cursor getMentorCursorForCourse(long id) {
        Log.d("dannyproblem", "get cursor");
        String selectQuery = "SELECT * FROM " + TABLE_MENTORS + " WHERE " + MENTOR_COURSE_ID_FIELD + " = " + id;
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery(selectQuery, null);
    }

    public Cursor getAssessmentsCursor() {
        String selectQuery = "SELECT * FROM " + TABLE_ASSESSMENTS;
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery(selectQuery, null);
    }

    public Cursor getAssessmentCursorForCourse(long id) {

        String selectQuery = "SELECT * FROM " + TABLE_ASSESSMENTS + " WHERE " + ASSESSMENT_COURSE_ID_FIELD + " = " + id;
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery(selectQuery, null);
    }
}
