(ns tasks.components.tasks-list.create-test
  (:require [devcards.core :as dc :refer-macros [defcard-rg]]
            [reagent.core :as reagent]
            [tasks.components.tasks-list.create :as create]))

(defcard-rg tasks-list-create
  "### Task create button"

  (fn [state_atom _]
    [create/render]))
