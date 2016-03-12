package edu.etduongucsd.dopeshit;

import android.app.AlertDialog;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.test.suitebuilder.annotation.LargeTest;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

/**
 * Created by Collin on 3/10/16.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class BasicLoginScenarioEspressoTest {

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
        login_email = "test@ucsd.edu";
        courseCode = 20; // CSE
        courseName = "CSE";
        courseNum = "30"; // CSE 30
        prof = "Rick Ord";
    }

    @Test
    public void fullRunThrough() {

        /* Log in with our login details */
        onView(withId(R.id.email)).perform(click(), (replaceText(login_email)), closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());

        /* Check that we have a toolbar available, meaning that we have logged in. */
        onView(withId(R.id.toolbar_title)).check(matches(withText("Noté")));

        /* Go into the all classes section so we can add a professor and a lecture. */
        onView(withId(R.id.allClassesButton)).perform(click());

        /* Go into the CSE subsection. */
        onData(anything()).inAdapterView(withId(R.id.allClassesExpList)).atPosition(courseCode).perform(click(), closeSoftKeyboard());

        /* Go into the CSE 30 sub section. */
        onView(allOf(is(instanceOf(AppCompatTextView.class)), withText(courseNum))). perform(click());

        /* Make sure we are in the right course, with the correct course number. */
        onView(withId(R.id.profListTitle)).check(matches(withText(courseName + " " + courseNum)));

        /* Then, create a new professor and name him/her. */
        onView(withId(R.id.addNewProfBut)).perform(click());
        onView(allOf(is(instanceOf(EditText.class)), withId(-1))).perform(click(), (replaceText(prof)), closeSoftKeyboard());
        onView(allOf(is(instanceOf(AppCompatButton.class)), withText("Accept"))).perform(click());

        /* Go back and select the same class so fire base has a chance to reset, then check that you can find the professor */
        Espresso.pressBack();
        Espresso.closeSoftKeyboard();
        onView(allOf(is(instanceOf(AppCompatTextView.class)), withText(courseNum))). perform(click());
        onView(allOf(is(instanceOf(AppCompatTextView.class)), withText(prof))).perform(click());

        /* Add a new lecture for today */
        onView(allOf(is(instanceOf(AppCompatButton.class)), withText("Add New Lecture"))).perform(click());
        onView(allOf(is(instanceOf(AppCompatButton.class)), withText("OK"))).perform(click());

        /* Go back to the upload page and make sure the professor and lecture are there */
        onView(allOf(is(instanceOf(AppCompatTextView.class)), withText("Noté"))).perform(click());
        onView(allOf(is(instanceOf(AppCompatButton.class)), withText("UPLOAD"))).perform(click());
        onView(allOf(is(instanceOf(AppCompatButton.class)), withText("OK"))).perform(click());

    }




}
