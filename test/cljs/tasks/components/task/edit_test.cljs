(ns tasks.components.task.edit-test
  (:require [cljs.spec :as spec]
            [cljs.spec.impl.gen :as gen]
            [clojure.test.check.generators]
            [devcards.core :as dc :refer-macros [defcard-rg]]
            [reagent.core :as reagent]
            [tasks.components.task.edit :as task-edit]
            [tasks.models.task :as task]
            [tasks.debug :as debug]))

(defcard-rg task-edit
  "### Basic component to edit a task

* save button should be disabled when task is invalid.
* input fields should be red when in error."
  (fn [state_atom _]
    (let [on-update (fn [field value]
                      (debug/spy "update" [field value])
                      (swap! state_atom assoc-in (concat [:task] field) value))
          on-save #(println "Save!" @state_atom)
          on-cancel #(println "Cancel!")
          task (:task @state_atom)]
      [task-edit/render task
       {:on-cancel on-cancel
        :on-update on-update
        :on-save on-save}]))

  (reagent/atom
    {:task (gen/generate (spec/gen :tasks.models.task/task))})

  {:inspect-data true})
