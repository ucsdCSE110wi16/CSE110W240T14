package edu.etduongucsd.dopeshit;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
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
public class UnitTest {

    String upload;
    String myNotes;
    String myCourses;
    String allClasses;

    @Rule
    public ActivityTestRule<MainActivity> homeScreen =
            new ActivityTestRule(HomeScreen.class);


    @Before
    public void init() {
        upload = "UPLOAD";
        myNotes = "My Notes";
        myCourses = "My Courses";
        allClasses = "All Courses";
    }

    /*
     * This unit test will check whether the home screen buttons point to the
     * correct locations
     */
    @Test
    public void unitTest() {

        /*
         * Each line will check whether the button contains the correct text
         */
        // Upload
        onView(allOf(is(instanceOf(AppCompatButton.class)), withText(upload))).check(matches(withText(upload)));

        // My Notes
        onView(allOf(is(instanceOf(AppCompatButton.class)), withText(myNotes))).check(matches(withText(myNotes)));

        // My Courses
        onView(allOf(is(instanceOf(AppCompatButton.class)), withText(myCourses))).check(matches(withText(myCourses)));

        // All Notes
        onView(allOf(is(instanceOf(AppCompatButton.class)), withText(allClasses))).check(matches(withText(allClasses)));

    }


}
