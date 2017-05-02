(ns tasks.core-test
  (:require [tasks.components.list.edit-test]
            [tasks.components.nav.view-test]
            [tasks.components.prompt.view-test]
            [tasks.components.task.create-test]
            [tasks.components.task.edit-test]
            [tasks.components.task.list-test]
            [tasks.components.task.view-test]
            [tasks.models.list-test]
            [tasks.models.task-test]
            [tasks.models.tasks-test]
            [cljs.test :refer-macros [run-tests]]))

(enable-console-print!)

(run-tests 'tasks.models.list-test
           'tasks.models.task-test
           'tasks.models.tasks-test)
