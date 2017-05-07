(ns tasks.models.task-test
  (:require [cljs.pprint :refer [pprint]]
            [cljs.spec.test :as stest]
            [cljs.test :as test :refer-macros [is testing use-fixtures]]
            [devcards.core :as dc :refer-macros [defcard-doc deftest mkdn-pprint-source]]
            [tasks.models.task :as task]
            [tasks.utils-test :as utils-test]))

(use-fixtures :once
  {:before (fn [] (stest/instrument 'tasks.models.task))
   :after (fn [] (stest/unstrument 'tasks.models.task))})

(def tasks-example
  [{:id "40"
    :title "title1"
    :body "body1"
    :done false}
   {:id "42"
    :title "title2"
    :body "body2"
    :done true}
   {:id "44"
    :title "title3"
    :body "body3"
    :done false}])

(pprint tasks-example)

(defcard-doc
  "## Tasks List model"
  (mkdn-pprint-source tasks-example))

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
  "Delete task in list by :id"
  (testing "delete-task"
    (is (= true
           (utils-test/check-result
            (stest/check `task/delete-task utils-test/check-opts))))
    (is (= '({:id "40"
              :title "title1"
              :body "body1"
              :done false}
             {:id "44"
              :title "title3"
              :body "body3"
              :done false})
           (task/delete-task tasks-example
                              (nth tasks-example 1))))
    (is (= tasks-example
           (task/delete-task tasks-example
                              {:id "48"
                               :title "title"
                               :body "body"
                               :done false}))))
  "Update task in list by :id"
  (testing "update-task"
    (is (= true
           (utils-test/check-result
            (stest/check `task/update-task utils-test/check-opts))))
    (is (= '({:id "40"
              :title "title1"
              :body "body1"
              :done false}
             {:id "42"
              :title "newTitle"
              :body "newBody"
              :done false}
             {:id "44"
              :title "title3"
              :body "body3"
              :done false})
           (task/update-task tasks-example
                              {:id "42"
                               :title "newTitle"
                               :body "newBody"
                               :done false})))
    (is (= tasks-example
           (task/update-task tasks-example
                              {:id "48"
                               :title "title"
                               :body "body"
                               :done false}))))

  "Check/Uncheck all tasks :done"
  (testing "toggle-all-done"
    (is (= true
           (utils-test/check-result
            (stest/check `task/toggle-all-done utils-test/check-opts))))
    (is (= []
           (task/toggle-all-done [])))
    (is (= '({:id "40"
              :title "title1"
              :body "body1"
              :done true}
             {:id "42"
              :title "title2"
              :body "body2"
              :done true}
             {:id "44"
              :title "title3"
              :body "body3"
              :done true})
           (task/toggle-all-done tasks-example)))
    (is (= '({:id "40"
              :title "title1"
              :body "body1"
              :done false}
             {:id "42"
              :title "title2"
              :body "body2"
              :done false}
             {:id "44"
              :title "title3"
              :body "body3"
              :done false})
           (task/toggle-all-done (task/toggle-all-done tasks-example)))
        "Based on :done state for the first task"))
  "Filter list with :title or :body matching regexp"
  (testing "filter-with"
    (is (= true
           (utils-test/check-result
            (stest/check `task/filter-with utils-test/check-opts))))
    (is (= '()
           (task/filter-with tasks-example "toto")))
    (is (= '({:id "42"
              :title "title2"
              :body "body2"
              :done true})
           (task/filter-with tasks-example "title2")))
    (is (= '({:id "44"
              :title "title3"
              :body "body3"
              :done false})
           (task/filter-with tasks-example "body3")))
    (is (= '({:id "44"
              :title "title3"
              :body "body3"
              :done false})
           (task/filter-with tasks-example "tle3"))
        ":with is in fact a regexp")
    (is (= '({:id "42"
              :title "title2"
              :body "body2"
              :done true})
           (task/filter-with tasks-example "dy2"))
        ":with is in fact a regexp")
    (is (= '({:id "42"
              :title "title2"
              :body "body2"
              :done true})
           (task/filter-with tasks-example "Dy2"))
        "match ignores case")
    (is (= '({:id "42"
              :title "title2"
              :body "boDy2"
              :done true})
           (task/filter-with
            (assoc-in tasks-example [1 :body] "boDy2")
            "dy2"))
        "match ignores case")
    (is (= '({:id "42"
              :title "title2"
              :body "body2"
              :done true}
             {:id "44"
              :title "title3"
              :body "body3"
              :done false})
           (task/filter-with tasks-example "dy2         tle3"))
        "any number of spaces become '|'")
    (is (= '({:id "42"
              :title "title2"
              :body "body2"
              :done true}
             {:id "44"
              :title "title3"
              :body "body3"
              :done false})
           (task/filter-with tasks-example "dy2         tle3"))
        "spaces at the statr/end don't become '|'")
    (is (= '({:id "42"
              :title "title2"
              :body "body2"
              :done true})
           (task/filter-with tasks-example " dy2 "))
        "any number of '.' become '.*'"))
  "Sort list by title"
  (testing "sort-by-title"
    (is (= true
           (utils-test/check-result
            (stest/check `task/sort-by-title utils-test/check-opts))))
    (is (= '()
           (task/sort-by-title
            '())))
    (is (= '({:id "40"
              :title "title1"
              :body "body1"
              :done false}
             {:id "42"
              :title "title2"
              :body "body2"
              :done true}
             {:id "44"
              :title "title3"
              :body "body3"
              :done false})
           (task/sort-by-title
            tasks-example)))
    (is (= '({:id "40"
              :title "title1"
              :body "body1"
              :done false}
             {:id "42"
              :title "title2"
              :body "body2"
              :done true}
             {:id "44"
              :title "title3"
              :body "body3"
              :done false})
           (task/sort-by-title
            '({:id "44"
               :title "title3"
               :body "body3"
               :done false}
              {:id "42"
               :title "title2"
               :body "body2"
               :done true}
              {:id "40"
               :title "title1"
               :body "body1"
               :done false}))))
    (is (= '({:id "40"
              :title "title1"
              :body "body1"
              :done false}
             {:id "42"
              :title "tiTle2"
              :body "body2"
              :done true}
             {:id "44"
              :title "Title3"
              :body "body3"
              :done false})
           (task/sort-by-title
            '({:id "44"
               :title "Title3"
               :body "body3"
               :done false}
              {:id "42"
               :title "tiTle2"
               :body "body2"
               :done true}
              {:id "40"
               :title "title1"
               :body "body1"
               :done false})))
        "should ignore case")))
