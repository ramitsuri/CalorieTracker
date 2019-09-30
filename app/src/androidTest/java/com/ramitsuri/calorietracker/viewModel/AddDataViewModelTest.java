package com.ramitsuri.calorietracker.viewModel;

import com.ramitsuri.calorietracker.LiveDataTestUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

@RunWith(JUnit4.class)
public class AddDataViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private AddDataViewModel mViewModel;

    @Before
    public void setUp() {
        mViewModel = new AddDataViewModel();
    }

    @Test
    public void testCurrentIndex() throws Exception {
        Assert.assertEquals((Integer)0, LiveDataTestUtil.getValue(mViewModel.getCurrentIndex()));

        mViewModel.selectNextQuestion();
        Assert.assertEquals((Integer)1, LiveDataTestUtil.getValue(mViewModel.getCurrentIndex()));

        mViewModel.selectPreviousQuestion();
        Assert.assertEquals((Integer)0, LiveDataTestUtil.getValue(mViewModel.getCurrentIndex()));

        mViewModel.selectPreviousQuestion();
        Assert.assertEquals((Integer)(mViewModel.getTrackedItems().size() - 1),
                LiveDataTestUtil.getValue(mViewModel.getCurrentIndex()));

        mViewModel.selectNextQuestion();
        Assert.assertEquals((Integer)0, LiveDataTestUtil.getValue(mViewModel.getCurrentIndex()));
    }

    @After
    public void tearDown() {
        mViewModel = null;
    }
}
