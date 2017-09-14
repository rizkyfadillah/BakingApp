package com.rizkyfadillah.bakingapp.ui.recipedetail;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.rizkyfadillah.bakingapp.R;
import com.rizkyfadillah.bakingapp.RecipeDetailActivity;
import com.rizkyfadillah.bakingapp.testing.SingleFragmentActivity;
import com.rizkyfadillah.bakingapp.util.RecyclerViewMatcher;
import com.rizkyfadillah.bakingapp.util.TaskExecutorWithIdlingResourceRule;
import com.rizkyfadillah.bakingapp.util.TestUtil;
import com.rizkyfadillah.bakingapp.util.ViewModelUtil;
import com.rizkyfadillah.bakingapp.vo.Recipe;
import com.rizkyfadillah.bakingapp.vo.Resource;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author rizkyfadillah on 07/09/2017.
 */

@RunWith(AndroidJUnit4.class)
public class RecipeDetailFragmentTest {

    private final int RECIPE_ID = 1;

    @Rule
    public ActivityTestRule<SingleFragmentActivity> activityRule = new ActivityTestRule<>(
            SingleFragmentActivity.class, true, true
    );

    @Rule
    public TaskExecutorWithIdlingResourceRule executorRule =
            new TaskExecutorWithIdlingResourceRule();

    private MutableLiveData<Resource<Recipe>> recipe = new MutableLiveData<>();
    private RecipeDetailFragment recipeDetailFragment;
    private RecipeDetailViewModel viewModel;

    @Before
    public void init() {
        recipeDetailFragment = new RecipeDetailFragment();
        viewModel = mock(RecipeDetailViewModel.class);

        when(viewModel.getRecipe()).thenReturn(recipe);

        recipeDetailFragment.viewModelFactory = ViewModelUtil.createFor(viewModel);

        activityRule.getActivity().setFragment(recipeDetailFragment);
    }

    @Test
    public void testLoading() {
        recipe.postValue(Resource.loading(null));
        onView(withId(R.id.progress_bar)).check(matches(isDisplayed()));
        onView(withId(R.id.error_and_retry_view)).check(matches(not(isDisplayed())));
    }

    @Test
    public void testValueWhileLoading() {
        Recipe recipe = TestUtil.createRecipe(1, "Bolu Kemojo", 3, "");
        this.recipe.postValue(Resource.loading(recipe));
        onView(withId(R.id.progress_bar)).check(matches(not(isDisplayed())));
        onView(listMatcher().atPosition(0))
                .check(matches(hasDescendant(withText("Aduk adonan"))));
    }

    @Test
    public void testLoaded() throws InterruptedException {
        Recipe recipe = TestUtil.createRecipe(1, "Bolu Kemojo", 3, "");
        this.recipe.postValue(Resource.success(recipe));
        onView(withId(R.id.progress_bar)).check(matches(not(isDisplayed())));
        onView(listMatcher().atPosition(0))
                .check(matches(hasDescendant(withText("Aduk adonan"))));
    }

    @Test
    public void testError() throws InterruptedException {
        recipe.postValue(Resource.error("Error from test", null));
        onView(withId(R.id.progress_bar)).check(matches(not(isDisplayed())));
        onView(withId(R.id.error_and_retry_view)).check(matches(isDisplayed()));

        onView(withId(R.id.button_retry)).perform(click());
        verify(viewModel).retry();
        recipe.postValue(Resource.loading(null));

        onView(withId(R.id.progress_bar)).check(matches(isDisplayed()));
        onView(withId(R.id.error_and_retry_view)).check(matches(not(isDisplayed())));
        Recipe recipe = TestUtil.createRecipe(1, "Bolu Kemojo", 3, "");
        this.recipe.postValue(Resource.success(recipe));

        onView(withId(R.id.progress_bar)).check(matches(not(isDisplayed())));
        onView(withId(R.id.error_and_retry_view)).check(matches(not(isDisplayed())));
        onView(listMatcher().atPosition(0))
                .check(matches(hasDescendant(withText("Aduk adonan"))));
    }

    @NonNull
    private RecyclerViewMatcher listMatcher() {
        return new RecyclerViewMatcher(R.id.recyclerview_step);
    }

}
