package nebula

import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.TaskAction

class MyPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        MyTask myTask = project.tasks.create('hello', MyTask)
        finalizedBy(project, myTask)

    }

    private void finalizedBy(Project project, Task otherTask) {
        project.tasks.configureEach { Task task ->
            if (task != otherTask) {
                task.finalizedBy otherTask
            }
        }
        project.childProjects.values().each { Project subProject ->
            finalizedBy(subProject, otherTask)
        }
    }
}

class MyTask extends DefaultTask {
    @TaskAction
    void print() {
        println 'Hello, World'
    }
}