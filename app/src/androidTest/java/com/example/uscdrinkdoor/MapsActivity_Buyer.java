package com.example.uscdrinkdoor;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MapsActivity_Buyer {

    @Rule
    public ActivityTestRule<MapsActivity> mActivityTestRule = new ActivityTestRule<>(MapsActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION");

    @Before
    public void SetUser(){

    }

//    @Test
//    public void Login() {
//        ViewInteraction appCompatEditText = onView(allOf(withId(R.id.email),
//                childAtPosition(childAtPosition(withId(android.R.id.content),
//                        0), 1), isDisplayed()));
//        appCompatEditText.perform(click());
//
//        ViewInteraction appCompatEditText2 = onView(
//                allOf(withId(R.id.email), childAtPosition(
//                        childAtPosition(withId(android.R.id.content),
//                                0), 1), isDisplayed()));
//        appCompatEditText2.perform(replaceText("user@a.com"), closeSoftKeyboard());
//
//        ViewInteraction appCompatEditText3 = onView(
//                allOf(withId(R.id.password), childAtPosition(
//                        childAtPosition(withId(android.R.id.content),
//                                0), 2), isDisplayed()));
//        appCompatEditText3.perform(replaceText("123456"), closeSoftKeyboard());
//
//        ViewInteraction materialButton = onView(
//                allOf(withId(R.id.btnlogin), withText("Login"), childAtPosition(
//                        childAtPosition(withId(android.R.id.content),
//                                0), 4), isDisplayed()));
//        materialButton.perform(click());
//    }


    @Test
    public void Buttons() {
        ViewInteraction button = onView(allOf(withId(R.id.Home), withText("Home")));
        button.check(matches(isDisplayed()));

        ViewInteraction button2 = onView(allOf(withId(R.id.sellerMenu), withText("Menu")));
        button2.check(matches(isDisplayed()));

        ViewInteraction button3 = onView(allOf(withId(R.id.userOrder), withText("Order")));
        button3.check(matches(isDisplayed()));

        ViewInteraction button5 = onView(allOf(withId(R.id.Account_Profile), withText("Account")));
        button5.check(matches(isDisplayed()));
    }

    @Test
    public void Map() {
        ViewInteraction view = onView(withId(R.id.map));
        view.check(matches(isDisplayed()));
    }

    @Test
    public void Location() {

        ViewInteraction imageView = onView(
                allOf(withContentDescription("My Location"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class))),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}