(ns tasks.core-test
  (:require [tasks.components.task.view-test]
            [tasks.models.task-test]
            [cljs.test :refer-macros [run-tests]]))

(enable-console-print!)

(run-tests 'tasks.models.task-test)
