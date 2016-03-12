package edu.etduongucsd.dopeshit;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

/**
 * Created by Collin on 3/10/16.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class Scenario3FlaggingContent {

    String login_email;
    int courseCode;
    String courseNum;
    String courseName;
    String prof;
    String lecture;

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
        lecture = "3/11 Lecture";
    }

    /*
     * SCENARIO 3: FLAGGING
     * Kevin is studying for finals and wants another perspective on a
     * confusing topic. Luckily, he has the app Noté! He opens the app on his
     * android phone and enters his UCSD email (kevin@ucsd.edu) into the email
     * prompt. He has already saved CSE 30 so, so he clicks on the "My Classes"
     * button. From his list of classes, he chooses CSE 30 with Rick Ord, and
     * chooses the lecture he wants, Friday March 11th. Scrolling through the
     * notes, he sees notes that are terrible, and unhelpful. Not wanting other
     * students to go through the struggle of reading them or be misled, he uses
     * the flag tool to flag the notes.
     */
    @Test
    public void testFlaggingContent() {

        /*
         * He opens the app on his android phone and enters his UCSD email
         * (kevin@ucsd.edu) into the email prompt.
         */
        onView(withId(R.id.email)).perform(click(), (replaceText(login_email)),
                closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());

        // Does a quick check that we have a toolbar available, meaning we are on the right page
        onView(withId(R.id.toolbar_title)).check(matches(withText("Noté")));

        /* He has already saved CSE 30 so, so he clicks on the "My Classes" button. */
        onView(withId(R.id.myClassesButton)).perform(click());

        /* From his list of classes, he chooses CSE 30 with Rick Ord... */
        onView(allOf(is(instanceOf(AppCompatTextView.class)), withText(courseName + " " + courseNum))).perform(click());

        /* ... and chooses the lecture he wants, Friday March 11th. */
        onView(allOf(is(instanceOf(AppCompatTextView.class)), withText(lecture))).perform(click());


        /* Scrolling through the
         * notes, he sees notes that are terrible, and unhelpful. Not wanting other
         * students to go through the struggle of reading them or be misled, he uses
         * the flag tool to flag the notes.
         */
        onView(allOf(is(instanceOf(AppCompatImageButton.class)), withId(2131558587))).perform(click());

    }




}
