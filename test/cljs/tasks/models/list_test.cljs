(ns tasks.models.list-test
  (:require [cljs.spec.test :as stest]
            [cljs.test :as test :refer-macros [is testing use-fixtures]]
            [devcards.core :as dc :refer-macros [defcard-doc deftest mkdn-pprint-source]]
            [tasks.models.list :as list-model]
            [tasks.utils-test :as utils-test]))

(use-fixtures :once
  {:before (fn [] (stest/instrument 'tasks.models.list))
   :after (fn [] (stest/unstrument 'tasks.models.list))})

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
            (stest/check `list-model/create utils-test/check-opts))))
    (is (= ["nouveau"]
           (list-model/create [] "nouveau")))
    (is (= ["boulot"
            "check"
            "courses"
            "default"
            "nouveau"]
           (list-model/create lists-example "nouveau"))))
  "Remove tasks list"
  (testing "delete"
    (is (= true
           (utils-test/check-result
            (stest/check `list-model/delete utils-test/check-opts))))
    (is (= []
           (list-model/delete [] "check")))
    (is (= ["boulot"
            "courses"
            "default"]
           (list-model/delete lists-example "check")))
    (is (= ["boulot"
            "check"
            "courses"
            "default"]
           (list-model/delete lists-example "unknown"))
        "when list does not exists, do nothing"))
  "Rename (or create new) tasks list"
  (testing "rename"
    (is (= true
           (utils-test/check-result
            (stest/check `list-model/rename utils-test/check-opts))))
    (is (= ["new"]
           (list-model/rename [] "old" "new"))
        "No previous list")
    (is (= ["boulot"
            "check"
            "courses"
            "default"
            "new"]
           (list-model/rename lists-example "old" "new"))
        "List did not exists, then create")
    (is (= ["boulot"
            "courses"
            "default"
            "new"]
           (list-model/rename lists-example "check" "new"))))
  "Return a map of validation errors message for a list edit"
  (testing "describe-errors"
    (is (= {}
           (list-model/describe-errors {:new-name "toto"
                                        :current-name ""} []))
        "Create - empty list")
    (is (= {}
           (list-model/describe-errors {:new-name "toto"
                                        :current-name ""} ["tata" "titi"]))
        "Create - no-conflict list")
    (is (= {:new-name "Name should be a non-empty string"}
           (list-model/describe-errors {:new-name ""
                                        :current-name ""} ["tata" "titi"]))
        "Create - invalid new name")
    (is (= {:new-name "A tasks list with this name already exists"}
           (list-model/describe-errors {:new-name "titi"
                                        :current-name ""} ["tata" "titi"]))
        "Create - already exists")
    (is (= {}
           (list-model/describe-errors {:new-name "titi"
                                        :current-name "titi"} ["tata" "titi"]))
        "Edit - unchanged")
    (is (= {}
           (list-model/describe-errors {:new-name "toto"
                                        :current-name "titi"} ["tata" "titi"]))
        "Edit - no-conflict")
    (is (= {:new-name "Name should be a non-empty string"}
           (list-model/describe-errors {:new-name ""
                                        :current-name "titi"} ["tata" "titi"]))
        "Edit - invalid new name")
    (is (= {:new-name "A tasks list with this name already exists"}
           (list-model/describe-errors {:new-name "tata"
                                        :current-name "titi"} ["tata" "titi"]))
        "Edit - already exists")))
