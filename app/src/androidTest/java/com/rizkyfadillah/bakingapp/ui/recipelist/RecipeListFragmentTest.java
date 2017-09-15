package com.rizkyfadillah.bakingapp.ui.recipelist;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.rizkyfadillah.bakingapp.R;
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

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author rizkyfadillah on 9/14/2017.
 */

@RunWith(AndroidJUnit4.class)
public class RecipeListFragmentTest {

    @Rule
    public ActivityTestRule<SingleFragmentActivity> activityRule = new ActivityTestRule<>(
            SingleFragmentActivity.class, true, true
    );

    @Rule
    public TaskExecutorWithIdlingResourceRule executorRule =
            new TaskExecutorWithIdlingResourceRule();

    private MutableLiveData<Resource<List<Recipe>>> recipes = new MutableLiveData<>();
    private RecipeListFragment recipeListFragment;
    private RecipeListViewModel viewModel;

    @Before
    public void init() {
        recipeListFragment = new RecipeListFragment();
        viewModel = mock(RecipeListViewModel.class);

        when(viewModel.getRecipes()).thenReturn(recipes);

        recipeListFragment.viewModelFactory = ViewModelUtil.createFor(viewModel);

        activityRule.getActivity().setFragment(recipeListFragment);
    }

    @Test
    public void testLoading() {
        recipes.postValue(Resource.loading(null));
        onView(withId(R.id.progress_bar)).check(matches(isDisplayed()));
        onView(withId(R.id.error_and_retry_view)).check(matches(not(isDisplayed())));
    }

    @Test
    public void testRecipes() {
        List<Recipe> recipeList = new ArrayList<>();
        recipeList.add(TestUtil.createRecipe(1, "Bolu Kemojo", 3, ""));
        recipeList.add(TestUtil.createRecipe(2, "Bolu Dam", 3, ""));
        recipeList.add(TestUtil.createRecipe(3, "Bolu Blackforest", 3, ""));
        this.recipes.postValue(Resource.success(recipeList));
        onView(listMatcher().atPosition(0))
                .check(matches(hasDescendant(withText("Bolu Kemojo"))));
    }

    @NonNull
    private RecyclerViewMatcher listMatcher() {
        return new RecyclerViewMatcher(R.id.recyclerview_recipe);
    }

}
