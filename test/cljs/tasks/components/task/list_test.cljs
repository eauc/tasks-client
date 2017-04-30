(ns tasks.components.task.list-test
  (:require [cljs.spec :as spec]
            [cljs.spec.impl.gen :as gen]
            [clojure.test.check.generators]
            [devcards.core :as dc :refer-macros [defcard-rg]]
            [reagent.core :as reagent]
            [tasks.components.task.list :as task-list]
            [tasks.models.task :as task]
            [tasks.models.tasks :as tasks]))

(defcard-rg task-view
  "### Tasks list view

* clicking on checkbox should toggle :done state.
* clicking on a task's title should show the task body/actions.
        * clicking on the same task again should hide the body/actions.
        * clicking on another task should show the new task & hide the previous task.
* typing a filter search the list (ignoring case).
* list should be sorted by title (ignoring case).
* list should scroll if it's too long for container."

  (fn [state_atom _]
    (let [tasks (-> (:tasks @state_atom)
                    (tasks/filter-with (:filter @state_atom))
                    (tasks/sort-by-title))
          show-details (:show-details @state_atom)
          on-filter #(swap! state_atom assoc-in [:filter] %2)
          on-update #(swap! state_atom assoc-in [:tasks] %)
          toggle-details #(swap! state_atom assoc-in [:show-details] %)]
      [:div {:style {:border "1px solid rgba(0,0,0,0.8)"
                     :display "flex"
                     :flex-flow "column nowrap"
                     :height "30em"}}
       [task-list/render tasks
        {:filter (:filter @state_atom)
         :show-details show-details
         :on-edit #(println "Edit! " %)
         :on-filter on-filter
         :on-update on-update
         :toggle-details toggle-details}]]))

  (reagent/atom
   {:tasks (gen/sample (spec/gen :tasks.models.task/task))
    :filter ""
    :show-details nil})

  {:inspect-data true})
