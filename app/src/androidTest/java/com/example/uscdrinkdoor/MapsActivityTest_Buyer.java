package com.example.uscdrinkdoor;

import static androidx.test.InstrumentationRegistry.getInstrumentation;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.intent.Intents;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MapsActivityTest_Buyer {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION");

    @Before
    public void Login() {
        ViewInteraction appCompatEditText = onView(allOf(withId(R.id.email),
                childAtPosition(childAtPosition(withId(android.R.id.content),
                        0), 1), isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.email), childAtPosition(
                        childAtPosition(withId(android.R.id.content),
                                0), 1), isDisplayed()));
        appCompatEditText2.perform(replaceText("user@a.com"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.password), childAtPosition(
                        childAtPosition(withId(android.R.id.content),
                                0), 2), isDisplayed()));
        appCompatEditText3.perform(replaceText("123456"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.btnlogin), withText("Login"), childAtPosition(
                        childAtPosition(withId(android.R.id.content),
                                0), 4), isDisplayed()));
        materialButton.perform(click());
    }

    @Before
    public void registerIdlingResource(){
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource);
    }

    @After
    public void unregisterIdlingResource(){
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource);

    }

    @Test
    public void MapDisplayed() {
        ViewInteraction view = onView(withId(R.id.map));
        view.check(matches(isDisplayed()));
    }

    @Test
    public void ButtonsDisplayed() {
        ViewInteraction button = onView(allOf(withId(R.id.Home)));
        button.check(matches(withText("Home")));

        ViewInteraction button2 = onView(allOf(withId(R.id.sellerMenu)));
        button2.check(matches(withText("Cart")));

        ViewInteraction button3 = onView(allOf(withId(R.id.userOrder)));
        button3.check(matches(withText("Order")));

        ViewInteraction button5 = onView(allOf(withId(R.id.Account_Profile)));
        button5.check(matches(withText("Account")));
    }

    @Test
    public void ClickCart() {
        ViewInteraction button2 = onView(allOf(withId(R.id.sellerMenu)));
        Intents.init();
        button2.perform(click());
        intended(hasComponent(ShoppingCartActivity.class.getName()));
        Intents.release();
    }

    @Test
    public void ClickOrder() {
        ViewInteraction button2 = onView(allOf(withId(R.id.userOrder)));
        Intents.init();
        button2.perform(click());
        intended(hasComponent(OrderCompleteActivity.class.getName()));
        Intents.release();
    }

    @Test
    public void ClickAccount() {
        ViewInteraction button2 = onView(allOf(withId(R.id.Account_Profile)));
        Intents.init();
        button2.perform(click());
        intended(hasComponent(User_Profile.class.getName()));
        Intents.release();
    }

    @Test
    public void StoresDisplayedClicked(){
        UiDevice uiDevice = UiDevice.getInstance(getInstrumentation());
        UiObject mMarker1 = uiDevice.findObject(new UiSelector().descriptionContains("Alex Hatzo"));
        UiObject mMarker2 = uiDevice.findObject(new UiSelector().descriptionContains("USC Tea Shop"));
        UiObject mMarker3 = uiDevice.findObject(new UiSelector().descriptionContains("USCoffee"));

        // Click 1 time, display route, estimated delivery time, travel options
        try {
            // Click on first store
            mMarker1.click();
            ViewInteraction button1 = onView(allOf(withId(R.id.walking)));
            ViewInteraction button2 = onView(allOf(withId(R.id.driving)));
            ViewInteraction button3 = onView(allOf(withId(R.id.esttime)));
            button1.check(matches(isDisplayed()));
            button2.check(matches(isDisplayed()));
            button3.check(matches(isDisplayed()));

            // Check different travel options
            button2.perform(click());
            button1.perform(click());

            // Click on second store
            mMarker2.click();
            button1.check(matches(isDisplayed()));
            button2.check(matches(isDisplayed()));
            button3.check(matches(isDisplayed()));
            button2.perform(click());
            button1.perform(click());

            // Click on third store
            mMarker3.click();
            button1.check(matches(isDisplayed()));
            button2.check(matches(isDisplayed()));
            button3.check(matches(isDisplayed()));
            button2.perform(click());
            button1.perform(click());

        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }

//        ViewInteraction view = onView(
//                allOf(withContentDescription("Alex Hatzo. Click twice to see menu."),
//                        withParent(allOf(withContentDescription("Google Map"),
//                                withParent(IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class)))),
//                        isDisplayed()));
//        view.check(matches(isDisplayed()));
//
//        ViewInteraction view2 = onView(
//                allOf(withContentDescription("USC Tea Shop. Click twice to see menu."),
//                        withParent(allOf(withContentDescription("Google Map"),
//                                withParent(IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class)))),
//                        isDisplayed()));
//        view2.check(matches(isDisplayed()));
//
//        ViewInteraction view3 = onView(
//                allOf(withContentDescription("USCoffee. Click twice to see menu."),
//                        withParent(allOf(withContentDescription("Google Map"),
//                                withParent(IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class)))),
//                        isDisplayed()));
//        view3.check(matches(isDisplayed()));
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