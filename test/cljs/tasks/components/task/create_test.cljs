(ns tasks.components.task.create-test
  (:require [devcards.core :as dc :refer-macros [defcard-rg]]
            [reagent.core :as reagent]
            [tasks.components.task.create :as task-create]))

(defcard-rg task-create
  "### Task create button"

  (fn [state_atom _]
    [task-create/render]))
