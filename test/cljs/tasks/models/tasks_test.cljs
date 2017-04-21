(ns tasks.models.tasks-test
	(:require [cljs.spec.test :as stest]
						[cljs.test :as test :refer-macros [is testing use-fixtures]]
						[devcards.core :as dc :refer-macros [defcard deftest]]
						[tasks.models.tasks :as tasks]))

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

(deftest tasks-model
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
															 :done false})))))
