(ns tasks.components.task.view-test
  (:require [cljs.spec :as spec]
            [cljs.spec.impl.gen :as gen]
            [devcards.core :as dc :refer-macros [defcard-rg]]
            [reagent.core :as reagent]
            [tasks.components.task.view :as task-view]))

(defcard-rg task-view
  "### Basic task view

* clicking on checkbox should toggle :done state
* clicking on title should toggle body/actions"

  (fn [state_atom _]
    [task-view/render (:task @state_atom)
     {:show-details (:show-details @state_atom)
      :on-delete #(println (str "Delete ! " %))
      :on-edit #(println (str "Edit ! " %))
      :on-update #(swap! state_atom assoc-in [:task] %)
      :toggle-details #(swap! state_atom update-in [:show-details] not)}])

  (reagent/atom
    {:task (gen/generate (spec/gen :tasks.specs.task/task))
     :show-details false})

  {:inspect-data true})
