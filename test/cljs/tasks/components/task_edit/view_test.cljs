(ns tasks.components.task-edit.view-test
  (:require [cljs.spec :as spec]
            [cljs.spec.impl.gen :as gen]
            [devcards.core :as dc :refer-macros [defcard-rg]]
            [reagent.core :as reagent]
            [tasks.components.task-edit.view :as view]
            [tasks.debug :as debug]
            [tasks.models.task-edit :as task-edit]))

(defcard-rg task-edit-view
  "### Basic component to edit a task

* save button should be disabled when task is invalid.
* input fields should be red when in error."
  (fn [state_atom _]
    (let [errors (debug/spy "errors" (task-edit/describe-errors (:task @state_atom)))
          on-update (fn [field value]
                      (debug/spy "update" [field value])
                      (swap! state_atom assoc-in (concat [:task] field) value))
          on-save #(println "Save!" @state_atom)
          on-cancel #(println "Cancel!")
          task (:task @state_atom)]
      [view/render task
       {:errors errors
        :on-cancel on-cancel
        :on-update on-update
        :on-save on-save}]))

  (reagent/atom
    {:task (gen/generate (spec/gen :tasks.specs.task/task))})

  {:inspect-data true})
