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
        MyOtherTask otherTask = project.tasks.create('otherTask', MyOtherTask)
        MyOtherTask2 otherTask2 = project.tasks.create('otherTask2', MyOtherTask2)
        finalizedBy(project, otherTask)
        finalizedBy(project, otherTask2)

    }

    private void finalizedBy(Project project, Task otherTask) {
        project.tasks.configureEach { Task task ->
            if (task != otherTask && task.name != 'otherTask2') {
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

class MyOtherTask extends DefaultTask {
    @TaskAction
    void print() {
        println 'this is the end'
    }
}

class MyOtherTask2 extends DefaultTask {
    @TaskAction
    void print() {
        println 'this is the end, maybe?'
    }
}