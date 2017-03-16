package com.beardflex;

import com.beardflex.bean.Bug;
import com.beardflex.bean.Feature;
import com.beardflex.bean.Project;
import com.beardflex.bean.Version;
import com.beardflex.db.dao.ProjectDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Created by David on 16/03/2017.
 */
public class TestAddProject {

    private static final Logger log = LogManager.getLogger();

    public static void main(String[] args) {
        getAllProjects();
    }

    public static void loadTestData() {
        Version version = new Version(3,1,0);
        Project pcd31 = new Project("PANCloudDirector", "Meteor", version);

        Bug bug41686 = new Bug();
        bug41686.setName("[POV 1719754] PCDTools expects that all servers have a primary and secondary DNS server configured and fails in the case of only the Primary DNS being set");
        bug41686.setIssueNumber("41686");
        bug41686.setParent(pcd31);

        pcd31.getChildren().add(bug41686);

        Feature feature = new Feature();
        feature.setName("Partner Uplift for Customer Servers");
        feature.setIssueNumber("41273");
        feature.setParent(pcd31);

        pcd31.getChildren().add(feature);

        ProjectDAO projectDao = new ProjectDAO();
        projectDao.addProject(pcd31);
    }

    public static void getAllProjects() {
        ProjectDAO projectDao = new ProjectDAO();
        List<Project> projects = projectDao.listProject();
        for(Project p : projects) {
            log.info("Project: '{}'.", p.getName());
        }
    }
}
