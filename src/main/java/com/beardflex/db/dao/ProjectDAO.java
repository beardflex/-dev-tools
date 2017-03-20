package com.beardflex.db.dao;

import com.beardflex.bean.Project;
import com.beardflex.db.HibernateUtil;
import com.beardflex.ui.Background;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.*;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by David on 16/03/2017.
 */
public class ProjectDAO {

    private final Logger log = LogManager.getLogger();

    public List<Project> listProject() {
        // Create a callable so we can perform the load on a background thread.
        Callable<List<Project>> getProjectsCall = () -> {
            log.info("Received request to retrieve all projects from DB.");
            List<Project> projects = new ArrayList<Project>();
            Session session = null;
            try {
                session = HibernateUtil.get().getSession();
                Query query = session.createQuery("from Project p");

                List<Project> dbProjects = query.list();
                if(dbProjects != null) {
                    projects = new ArrayList<Project>(dbProjects);
                }
            } catch (HibernateException e) {
                log.error(e);
            } finally {
                // Always remember to close the session.
                if(session != null && session.getTransaction().isActive()) {
                    session.getTransaction().commit();
                    session.close();
                }
                return projects;
            }
        };

        List<Project> projects = null;

        Future<List<Project>> result = Background.get().monitor(getProjectsCall);
        try {
            projects = result.get();
        } catch(InterruptedException e) {
            log.error(e);
        } catch(ExecutionException e) {
            log.error(e);
        } catch(CancellationException e) {
            log.error(e);
        } finally {
            return projects;
        }
    }

    public Project getProjectById(long id) {
        Callable<Project> getProjectByIdCall = () -> {
            log.info("Received request to retrieve project with id {} from DB.", id);
            Session session = null;
            Project project = null;
            try {
                session = HibernateUtil.get().getSession();
                Query query = session.createQuery("from Project p where p.id=:id");
                query.setLong("id", id);

                List<Project> dbProjects = query.list();
                if(dbProjects != null && dbProjects.size() > 0) {
                    project = dbProjects.get(0);
                }
            } catch(HibernateException e) {
                log.error(e);
            } finally {
                // Always remember to close the session.
                if(session != null && session.getTransaction().isActive()) {
                    session.getTransaction().commit();
                    session.close();
                }
                return project;
            }
        };
        Project project = null;
        Future<Project> result = Background.get().monitor(getProjectByIdCall);
        try {
            project = result.get();
        } catch(InterruptedException e) {
            log.error(e);
        } catch(ExecutionException e) {
            log.error(e);
        } catch(CancellationException e) {
            log.error(e);
        } finally {
            return project;
        }
    }

    public Project addProject(Project project) {
        Callable<Project> addProjectCall = () -> {
            log.info("Received request to add new project to DB.");
            Session session = null;
            Project newProject = null;
            try {
                session = HibernateUtil.get().getSession();
                Transaction transaction = session.beginTransaction();
                session.save(project);
                transaction.commit();
                return project;
            } catch(HibernateException e) {
                StringWriter errors = new StringWriter();
                e.printStackTrace(new PrintWriter(errors));
                log.error(errors.toString());
                log.error(e);
            } finally {
                // Always remember to close the session.
                if(session != null && session.getTransaction().isActive()) {
                    session.getTransaction().commit();
                    session.close();
                }
                return newProject;
            }
        };

        Project newProject = null;
        Future<Project> result = Background.get().monitor(addProjectCall);
        try {
            newProject = result.get();
        } catch(InterruptedException e) {
            log.error(e);
        } catch(ExecutionException e) {
            log.error(e);
        } catch(CancellationException e) {
            log.error(e);
        } finally {
            return newProject;
        }
    }

    public void updateProject(Project project) {
        Callable<Void> updateProjectCall = () -> {
            log.info("Received request to update project with id {} in the DB.", project.getId());
            Session session = null;
            try {
                session = HibernateUtil.get().getSession();
                session.saveOrUpdate(project);
                session.flush();
            } catch(HibernateException e) {
                log.error(e);
            } finally {
                // Always remember to close the session.
                if(session != null && session.getTransaction().isActive()) {
                    session.getTransaction().commit();
                    session.close();
                }
                return null;
            }
        };

        Background.get().fireAndForget(updateProjectCall);
    }
}
