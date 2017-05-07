(ns tasks.models.tasks-list-test
  (:require [cljs.spec.test :as stest]
            [cljs.test :as test :refer-macros [is testing use-fixtures]]
            [devcards.core :as dc :refer-macros [defcard-doc deftest mkdn-pprint-source]]
            [tasks.models.tasks-list :as tasks-list]
            [tasks.utils-test :as utils-test]))

(use-fixtures :once
  {:before (fn [] (stest/instrument 'tasks.models.tasks-list))
   :after (fn [] (stest/unstrument 'tasks.models.tasks-list))})

(def lists-example
  ["default"
   "courses"
   "boulot"
   "check"])

(defcard-doc
  "## Tasks List model"
  (mkdn-pprint-source lists-example))

(deftest list-test
  "Add new tasks list"
  (testing "create"
    (is (= true
           (utils-test/check-result
            (stest/check `tasks-list/create utils-test/check-opts))))
    (is (= ["nouveau"]
           (tasks-list/create [] "nouveau")))
    (is (= ["boulot"
            "check"
            "courses"
            "default"
            "nouveau"]
           (tasks-list/create lists-example "nouveau"))))
  "Remove tasks list"
  (testing "delete"
    (is (= true
           (utils-test/check-result
            (stest/check `tasks-list/delete utils-test/check-opts))))
    (is (= []
           (tasks-list/delete [] "check")))
    (is (= ["boulot"
            "courses"
            "default"]
           (tasks-list/delete lists-example "check")))
    (is (= ["boulot"
            "check"
            "courses"
            "default"]
           (tasks-list/delete lists-example "unknown"))
        "when list does not exists, do nothing"))
  "Rename (or create new) tasks list"
  (testing "rename"
    (is (= true
           (utils-test/check-result
            (stest/check `tasks-list/rename utils-test/check-opts))))
    (is (= ["new"]
           (tasks-list/rename [] "old" "new"))
        "No previous list")
    (is (= ["boulot"
            "check"
            "courses"
            "default"
            "new"]
           (tasks-list/rename lists-example "old" "new"))
        "List did not exists, then create")
    (is (= ["boulot"
            "courses"
            "default"
            "new"]
           (tasks-list/rename lists-example "check" "new")))))
