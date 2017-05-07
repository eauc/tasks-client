(ns tasks.components.tasks-list.view-test
  (:require [cljs.spec :as spec]
            [cljs.spec.impl.gen :as gen]
            [devcards.core :as dc :refer-macros [defcard-rg]]
            [reagent.core :as reagent]
            [tasks.components.tasks-list.view :as view]
            [tasks.models.task :as task]))

(defcard-rg tasks-list-view
  "### Tasks list view

* clicking on checkbox should toggle :done state.
* clicking on a task's title should show the task body/actions.
        * clicking on the same task again should hide the body/actions.
        * clicking on another task should show the new task & hide the previous task.
* typing a filter search the list (ignoring case).
* list should be sorted by title (ignoring case).
* list should scroll if it's too long for container."

  (fn [state_atom _]
    (let [tasks (-> (get-in @state_atom [:tasks-list :tasks])
                    (task/filter-with (:tasks-filter @state_atom))
                    (task/sort-by-title))
          show-details (:tasks-show-details @state_atom)
          on-filter #(swap! state_atom assoc-in [:tasks-filter] %2)
          on-update #(swap! state_atom assoc-in [:tasks-list :tasks] %)
          toggle-details #(swap! state_atom assoc-in [:tasks-show-details] %)]
      [:div {:style {:border "1px solid rgba(0,0,0,0.8)"
                     :display "flex"
                     :flex-flow "column nowrap"
                     :height "30em"}}
       [view/render tasks
        {:filter (:tasks-filter @state_atom)
         :show-details show-details
         :on-edit #(println "Edit! " %)
         :on-filter on-filter
         :on-update on-update
         :toggle-details toggle-details}]]))

  (reagent/atom
   {:tasks-list {:name "name"
                 :id nil
                 :tasks (gen/sample (spec/gen :tasks.specs.task/task))}
    :tasks-filter ""
    :tasks-show-details nil})

  {:inspect-data true})
