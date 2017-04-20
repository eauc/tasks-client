(ns tasks.core-test
  (:require [tasks.components.task.edit-test]
            [tasks.components.task.list-test]
            [tasks.components.task.view-test]
            [tasks.models.task-test]
            [tasks.models.tasks-test]
            [cljs.test :refer-macros [run-tests]]))

(enable-console-print!)

(run-tests 'tasks.models.task-test
           'tasks.models.tasks-test)
