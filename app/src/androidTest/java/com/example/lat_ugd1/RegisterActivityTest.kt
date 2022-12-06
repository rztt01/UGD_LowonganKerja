package com.example.lat_ugd1


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class RegisterActivityTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(RegisterActivity::class.java)

    @Test
    fun registerActivityTest() {
        val materialButton = onView(
            allOf(
                withId(R.id.btnRegister2), withText("Register"),
                childAtPosition(
                    allOf(
                        withId(R.id.linearLayout4),
                        childAtPosition(
                            withId(R.id.mainLayout2),
                            2
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText = onView(
            allOf(
                childAtPosition(
                    childAtPosition(
                        withId(R.id.inputLayoutUsername2),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText.perform(replaceText("test"), closeSoftKeyboard())

        val materialButton2 = onView(
            allOf(
                withId(R.id.btnRegister2), withText("Register"),
                childAtPosition(
                    allOf(
                        withId(R.id.linearLayout4),
                        childAtPosition(
                            withId(R.id.mainLayout2),
                            2
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialButton2.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText2 = onView(
            allOf(
                childAtPosition(
                    childAtPosition(
                        withId(R.id.inputLayoutPassword2),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText2.perform(replaceText("test"), closeSoftKeyboard())


        val materialButton3 = onView(
            allOf(
                withId(R.id.btnRegister2), withText("Register"),
                childAtPosition(
                    allOf(
                        withId(R.id.linearLayout4),
                        childAtPosition(
                            withId(R.id.mainLayout2),
                            2
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialButton3.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText3 = onView(
            allOf(
                withId(R.id.inputConfirmPassword),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.inputLayoutConfirmPassword),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText3.perform(replaceText("test1"), closeSoftKeyboard())

        val materialButton4 = onView(
            allOf(
                withId(R.id.btnRegister2), withText("Register"),
                childAtPosition(
                    allOf(
                        withId(R.id.linearLayout4),
                        childAtPosition(
                            withId(R.id.mainLayout2),
                            2
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialButton4.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText4 = onView(
            allOf(
                childAtPosition(
                    childAtPosition(
                        withId(R.id.inputLayoutEmail),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText4.perform(replaceText("test"), closeSoftKeyboard())


        val materialButton5 = onView(
            allOf(
                withId(R.id.btnRegister2), withText("Register"),
                childAtPosition(
                    allOf(
                        withId(R.id.linearLayout4),
                        childAtPosition(
                            withId(R.id.mainLayout2),
                            2
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialButton5.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText5 = onView(
            allOf(
                childAtPosition(
                    childAtPosition(
                        withId(R.id.inputLayoutTanggalLahir),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText5.perform(replaceText("test"), closeSoftKeyboard())

        val materialButton6 = onView(
            allOf(
                withId(R.id.btnRegister2), withText("Register"),
                childAtPosition(
                    allOf(
                        withId(R.id.linearLayout4),
                        childAtPosition(
                            withId(R.id.mainLayout2),
                            2
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialButton6.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText6 = onView(
            allOf(
                childAtPosition(
                    childAtPosition(
                        withId(R.id.inputLayoutNoTelp),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText6.perform(replaceText("098"), closeSoftKeyboard())

        val materialButton7 = onView(
            allOf(
                withId(R.id.btnRegister2), withText("Register"),
                childAtPosition(
                    allOf(
                        withId(R.id.linearLayout4),
                        childAtPosition(
                            withId(R.id.mainLayout2),
                            2
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialButton7.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText7 = onView(
            allOf(
                childAtPosition(
                    childAtPosition(
                        withId(R.id.inputLayoutConfirmPassword),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText7.perform(replaceText("test"), closeSoftKeyboard())

        val materialButton8 = onView(
            allOf(
                withId(R.id.btnRegister2), withText("Register"),
                childAtPosition(
                    allOf(
                        withId(R.id.linearLayout4),
                        childAtPosition(
                            withId(R.id.mainLayout2),
                            2
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialButton8.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText8 = onView(
            allOf(
                childAtPosition(
                    childAtPosition(
                        withId(R.id.inputLayoutEmail),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText8.perform(replaceText("test@email.com"), closeSoftKeyboard())

        val materialButton9 = onView(
            allOf(
                withId(R.id.btnRegister2), withText("Register"),
                childAtPosition(
                    allOf(
                        withId(R.id.linearLayout4),
                        childAtPosition(
                            withId(R.id.mainLayout2),
                            2
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialButton9.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText9 = onView(
            allOf(
                childAtPosition(
                    childAtPosition(
                        withId(R.id.inputLayoutEmail),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText9.perform(replaceText("09998127"), closeSoftKeyboard())

        val materialButton10 = onView(
            allOf(
                withId(R.id.btnRegister2), withText("Register"),
                childAtPosition(
                    allOf(
                        withId(R.id.linearLayout4),
                        childAtPosition(
                            withId(R.id.mainLayout2),
                            2
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialButton10.perform(click())
        onView(isRoot()).perform(waitFor(3000))

    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }

    fun waitFor(delay: Long): ViewAction? {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isRoot()
            }

            override fun getDescription(): String {
                return "wait for " + delay + "milliseconds"
            }

            override fun perform(uiController: UiController, view: View) {
                uiController.loopMainThreadForAtLeast(delay)
            }
        }
    }
}
