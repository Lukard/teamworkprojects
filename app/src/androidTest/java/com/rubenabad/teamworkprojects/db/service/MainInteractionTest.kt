package com.rubenabad.teamworkprojects.db.service

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.rubenabad.teamworkprojects.R
import com.rubenabad.teamworkprojects.view.ProjectListActivity
import com.schibsted.spain.barista.interaction.BaristaListInteractions.clickListItem
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.greaterThan
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainInteractionTest {

    @get:Rule
    val mActivityRule = ActivityTestRule<ProjectListActivity>(ProjectListActivity::class.java)

    @Test
    fun loadProjects() {
        // We check that project are loaded
        assertThat((mActivityRule.activity.findViewById(R.id.recycler) as RecyclerView).adapter.itemCount,
                greaterThan(0))

        clickListItem(R.id.recycler, 0)

        // And we check again that the project list activity has traveled to detail activity and the corresponding
        // tasks are loaded
        assertThat((mActivityRule.activity.findViewById(R.id.recycler) as RecyclerView).adapter.itemCount,
                greaterThan(0))
    }

}