(ns tasks.core-test
  (:require [tasks.components.auth.view-test]
            [tasks.components.nav.view-test]
            [tasks.components.prompt.view-test]
            [tasks.components.task.view-test]
            [tasks.components.task-edit.view-test]
            [tasks.components.tasks-list.create-test]
            [tasks.components.tasks-list.view-test]
            [tasks.components.tasks-list-edit.view-test]
            [tasks.models.task-test]
            [tasks.models.task-edit-test]
            [tasks.models.tasks-list-test]
            [tasks.models.tasks-list-edit-test]
            [cljs.test :refer-macros [run-tests]]))

(enable-console-print!)

(run-tests 'tasks.models.task-test
           'tasks.models.task-edit-test
           'tasks.models.tasks-list-test
           'tasks.models.tasks-list-edit-test)
