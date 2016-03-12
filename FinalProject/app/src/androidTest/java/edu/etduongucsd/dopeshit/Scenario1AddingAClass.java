package edu.etduongucsd.dopeshit;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.test.suitebuilder.annotation.LargeTest;
import android.widget.EditText;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

/**
 * Created by Collin on 3/10/16.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class Scenario1AddingAClass {

    String login_email;
    int courseCode;
    String courseNum;
    String courseName;
    String prof;

    @Rule
    public ActivityTestRule<MainActivity> startingPoint =
            new ActivityTestRule(StartingPoint.class);


    @Before
    public void init() {
        login_email = "kevin@ucsd.edu";
        courseCode = 20; // CSE
        courseName = "CSE";
        courseNum = "30"; // CSE 30
        prof = "Rick Ord";
    }

    /*
     * SCENARIO 1: ADDING A CLASS
     * Kevin wants to be able to keep track of all the notes he has for his class,
     * CSE 30. Luckily, he has the app Noté! He opens the app on his android phone
     * and enters his UCSD email (kevin@ucsd.edu) into the email. He clicks on the
     * "All Courses" button to see a list of all the courses offered at UCSD. He
     * finds his class, CSE 30, in the list of classes. After choosing CSE 30, he
     * found his professor, Rick Ord. He adds this class into his list of classes
     * by clicking "Add Class".
     */
    @Test
    public void testAddingAClass() {


        /*
         * He opens the app on his android phone and enters his UCSD email
         *(kevin@ucsd.edu) into the email prompt.
         */
        onView(withId(R.id.email)).perform(click(), (replaceText(login_email)), closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());

        // Does a quick check that we have a toolbar available, meaning we are on the right page
        onView(withId(R.id.toolbar_title)).check(matches(withText("Noté")));

        /* He clicks on the "All Courses" button to see a list of all the courses offered at UCSD. */
        onView(withId(R.id.allClassesButton)).perform(click());

        /* He finds his class, CSE 30, in the list of classes. */
        onData(anything()).inAdapterView(withId(R.id.allClassesExpList)).atPosition(courseCode).perform(click(), closeSoftKeyboard());
        onView(allOf(is(instanceOf(AppCompatTextView.class)), withText(courseNum))).perform(click());
        onView(withId(R.id.profListTitle)).check(matches(withText(courseName + " " + courseNum)));

        /*
         * After choosing CSE 30, he found his professor, Rick Ord. He adds this class
         * into his list of classes by clicking "Add Class".
         */
        onView(allOf(is(instanceOf(AppCompatTextView.class)), withText(prof))).perform(click());
        onView(allOf(is(instanceOf(AppCompatButton.class)), withText("Add To MyClasses"))).perform(click());

    }
}
