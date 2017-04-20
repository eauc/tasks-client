(ns tasks.models.task-test
  (:require [cljs.test :as test :refer-macros [is testing use-fixtures]]
            [devcards.core :as dc :refer-macros [defcard deftest]]
            [tasks.models.task :as task]
            [cljs.spec.test :as stest]))

(use-fixtures
  :once
  {:before (fn []
             (stest/instrument 'tasks.models.task))
   :after (fn []
            (stest/unstrument 'tasks.models.task))})

(deftest
  task-model
  (testing
      "toggle-done"
    (is (= true
           (:done (task/toggle-done {:id "42"
                                     :title "title"
                                     :body "body"
                                     :done false}))))
    (is (= false
           (:done (task/toggle-done {:id "42"
                                     :title "title"
                                     :body "body"
                                     :done true}))))))
