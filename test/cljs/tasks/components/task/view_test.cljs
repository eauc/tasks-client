(ns tasks.components.task.view-test
  (:require [cljs.spec :as spec]
            [cljs.spec.impl.gen :as gen]
						[clojure.test.check.generators]
						[devcards.core :as dc :refer-macros [defcard-rg dom-node]]
						[reagent.core :as reagent]
            [tasks.components.task.view :as task-view]
            [tasks.models.task :as task]))

(defcard-rg task-view
	(fn [state_atom _]
    [task-view/render
     (:task @state_atom)
     {:show-details (:show-details @state_atom)
      :on-delete #(println (str "Delete ! " %))
      :on-edit #(println (str "Edit ! " %))
      :toggle-details #(swap! state_atom update-in [:show-details] not)
      :toggle-done #(swap! state_atom update-in [:task] task/toggle-done)}])
  (reagent/atom {:task (gen/generate (spec/gen :tasks.models.task/task))
                 :show-details false})
  {:inspect-data true})
