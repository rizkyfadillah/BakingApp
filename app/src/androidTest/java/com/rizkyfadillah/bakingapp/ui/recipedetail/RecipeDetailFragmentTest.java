package com.rizkyfadillah.bakingapp.ui.recipedetail;

import android.arch.lifecycle.MutableLiveData;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.rizkyfadillah.bakingapp.RecipeDetailActivity;
import com.rizkyfadillah.bakingapp.vo.Recipe;
import com.rizkyfadillah.bakingapp.vo.Resource;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.mock;

/**
 * @author rizkyfadillah on 07/09/2017.
 */

@RunWith(AndroidJUnit4.class)
public class RecipeDetailFragmentTest {

    @Rule
    public ActivityTestRule<RecipeDetailActivity> activityActivityTestRule = new ActivityTestRule<RecipeDetailActivity>(
            RecipeDetailActivity.class, true, true
    );

    private MutableLiveData<Resource<Recipe>> repo = new MutableLiveData<>();
    private RecipeDetailFragment recipeDetailFragment;
    private RecipeDetailViewModel viewModel;

    @Before
    public void init() {
        recipeDetailFragment = new RecipeDetailFragment();
        viewModel = mock(RecipeDetailViewModel.class);
    }

}
