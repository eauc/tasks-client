(ns tasks.models.task-test
  (:require [cljs.spec.test :as stest]
            [cljs.test :as test :refer-macros [is testing use-fixtures]]
            [devcards.core :as dc :refer-macros [deftest]]
            [tasks.models.task :as task]
            [tasks.debug :as debug]
            [tasks.utils-test :as utils-test]))

(use-fixtures :once
  {:before (fn [] (stest/instrument 'tasks.models.task))
   :after (fn [] (stest/unstrument 'tasks.models.task))})

(deftest task-model
  (testing "toggle-done"
    (is (= true
           (utils-test/check-result
            (stest/check `task/toggle-done utils-test/check-opts))))
    (is (= true
           (:done (task/toggle-done
                   {:id "42"
                    :title "title"
                    :body "body"
                    :done false}))))
    (is (= false
           (:done (task/toggle-done
                   {:id "42"
                    :title "title"
                    :body "body"
                    :done true})))))
  "Return a map of validation errors message for a task edit"
  (testing "describe-errors"
    (is (= {}
           (task/describe-errors
            {:id "42"
             :title "title"
             :body "body"
             :done false})))
    (is (= {:title "Title should be a non-empty string"}
           (task/describe-errors
            {:id "42"
             :title ""
             :body "body"
             :done false})))
    (is (= {:body "Body should be a string"}
           (task/describe-errors
            {:id "42"
             :title "title"
             :body 42
             :done false})))
    (is (= {:title "Title should be a non-empty string"
            :body "Body should be a string"}
           (task/describe-errors
            {:id "42"
             :title 71
             :body 42
             :done false})))))
