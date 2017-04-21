(ns tasks.models.tasks-test
	(:require [cljs.spec.test :as stest]
						[cljs.test :as test :refer-macros [is testing use-fixtures]]
						[devcards.core :as dc :refer-macros [defcard-doc deftest mkdn-pprint-source]]
						[tasks.models.tasks :as tasks]
            [cljs.pprint :refer [pprint]]))

(use-fixtures :once
	{:before (fn [] (stest/instrument 'tasks.models.tasks))
	 :after (fn [] (stest/unstrument 'tasks.models.tasks))})

(def tasks-example
	'({:id "40"
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
		 :done false}))

(pprint tasks-example)

(defcard-doc
  "## Tasks List model"
  (mkdn-pprint-source tasks-example))

(deftest tasks-model
  "Delete task in list by :id"
	(testing "delete-task"
		(is (= '({:id "40"
							:title "title1"
							:body "body1"
							:done false}
						 {:id "44"
							:title "title3"
							:body "body3"
							:done false})
					 (tasks/delete-task tasks-example
															(nth tasks-example 1))))
		(is (= tasks-example
					 (tasks/delete-task tasks-example
															{:id "48"
															 :title "title"
															 :body "body"
															 :done false}))))
  "Update task in list by :id"
	(testing "update-task"
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
					 (tasks/update-task tasks-example
															{:id "42"
															 :title "newTitle"
															 :body "newBody"
															 :done false})))
		(is (= tasks-example
					 (tasks/update-task tasks-example
															{:id "48"
															 :title "title"
															 :body "body"
															 :done false}))))
  "Filter list with :title or :body matching regexp"
  (testing "filter-with"
		(is (= '()
					 (tasks/filter-with tasks-example "toto")))
    (is (= '({:id "42"
              :title "title2"
              :body "body2"
              :done true})
					 (tasks/filter-with tasks-example "title2")))
    (is (= '({:id "44"
              :title "title3"
              :body "body3"
              :done false})
					 (tasks/filter-with tasks-example "body3")))
    (is (= '({:id "44"
              :title "title3"
              :body "body3"
              :done false})
					 (tasks/filter-with tasks-example "tle3"))
        ":with is in fact a regexp")
    (is (= '({:id "42"
              :title "title2"
              :body "body2"
              :done true})
					 (tasks/filter-with tasks-example "dy2"))
        ":with is in fact a regexp")
    (is (= '({:id "42"
              :title "title2"
              :body "body2"
              :done true}
             {:id "44"
              :title "title3"
              :body "body3"
              :done false})
					 (tasks/filter-with tasks-example "dy2   tle3"))
        "any number of spaces become '|'")
    (is (= '({:id "42"
              :title "title2"
              :body "body2"
              :done true}
             {:id "44"
              :title "title3"
              :body "body3"
              :done false})
					 (tasks/filter-with tasks-example "dy2   tle3"))
        "spaces at the statr/end don't become '|'")
    (is (= '({:id "42"
              :title "title2"
              :body "body2"
              :done true})
					 (tasks/filter-with tasks-example " dy2 "))
        "any number of '.' become '.*'")))
