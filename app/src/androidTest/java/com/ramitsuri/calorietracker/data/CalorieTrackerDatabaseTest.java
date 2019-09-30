package com.ramitsuri.calorietracker.data;

import android.content.Context;

import com.ramitsuri.calorietracker.LiveDataTestUtil;
import com.ramitsuri.calorietracker.data.dao.TrackedItemDao;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4ClassRunner.class)
public class CalorieTrackerDatabaseTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private static final String TAG = CalorieTrackerDatabase.class.getName();

    private TrackedItemDao mTrackedItemDao;
    private CalorieTrackerDatabase mDb;

    @Before
    public void createDb() {
        Context appContext = getContext();
        mDb = Room.inMemoryDatabaseBuilder(appContext, CalorieTrackerDatabase.class).build();
        mTrackedItemDao = mDb.trackedItemDao();

        mTrackedItemDao.insertAll(DummyData.getTrackedItems());
    }

    @After
    public void closeDb() throws IOException {
        mDb.close();
    }

    private Context getContext() {
        return InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    @Test
    public void questionTest() throws Exception {
        Assert.assertEquals(DummyData.getTrackedItems().size(), mTrackedItemDao.getAll().size());

        Assert.assertEquals(DummyData.getUnsynced().size(), mTrackedItemDao.getAllUnsynced().size());

        Assert.assertEquals(DummyData.getForDate(DummyData.getDates()[0]).size(),
                mTrackedItemDao.getForDay(DummyData.getDates()[0]).size());

        Assert.assertEquals(DummyData.getDates().length,
                LiveDataTestUtil.getValue(mTrackedItemDao.getDates()).size());

        mTrackedItemDao.updateUnsynced();
        Assert.assertEquals(0, mTrackedItemDao.getAllUnsynced().size());
        mTrackedItemDao.deleteAll();
        mTrackedItemDao.insertAll(DummyData.getTrackedItems());

        mTrackedItemDao.deleteSynced();
        Assert.assertEquals(0, mTrackedItemDao.getAll().size() - mTrackedItemDao.getAllUnsynced().size());
        mTrackedItemDao.deleteAll();
        mTrackedItemDao.insertAll(DummyData.getTrackedItems());
    }
}
