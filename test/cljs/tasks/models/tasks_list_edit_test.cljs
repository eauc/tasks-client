(ns tasks.models.tasks-list-edit-test
  (:require [cljs.spec.test :as stest]
            [cljs.test :as test :refer-macros [is testing use-fixtures]]
            [devcards.core :as dc :refer-macros [deftest]]
            [tasks.models.tasks-list-edit :as tasks-list-edit]))

(use-fixtures :once
  {:before (fn [] (stest/instrument 'tasks.models.tasks-list-edit))
   :after (fn [] (stest/unstrument 'tasks.models.tasks-list-edit))})

(deftest tasks-list-edit-test
  "Return a map of validation errors message for a list edit"
  (testing "describe-errors"
    (is (= {}
           (tasks-list-edit/describe-errors {:new-name "toto"
                                             :current-name nil} []))
        "Create - empty list")
    (is (= {}
           (tasks-list-edit/describe-errors {:new-name "toto"
                                             :current-name nil} ["tata" "titi"]))
        "Create - no-conflict list")
    (is (= {:new-name "Name should be a non-empty string"}
           (tasks-list-edit/describe-errors {:new-name ""
                                             :current-name nil} ["tata" "titi"]))
        "Create - invalid new name")
    (is (= {:new-name "A tasks list with this name already exists"}
           (tasks-list-edit/describe-errors {:new-name "titi"
                                             :current-name nil} ["tata" "titi"]))
        "Create - already exists")
    (is (= {}
           (tasks-list-edit/describe-errors {:new-name "titi"
                                             :current-name "titi"} ["tata" "titi"]))
        "Edit - unchanged")
    (is (= {}
           (tasks-list-edit/describe-errors {:new-name "toto"
                                             :current-name "titi"} ["tata" "titi"]))
        "Edit - no-conflict")
    (is (= {:new-name "Name should be a non-empty string"}
           (tasks-list-edit/describe-errors {:new-name ""
                                             :current-name "titi"} ["tata" "titi"]))
        "Edit - invalid new name")
    (is (= {:new-name "A tasks list with this name already exists"}
           (tasks-list-edit/describe-errors {:new-name "tata"
                                             :current-name "titi"} ["tata" "titi"]))
        "Edit - already exists")))
