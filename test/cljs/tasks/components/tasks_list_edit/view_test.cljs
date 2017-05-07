(ns tasks.components.tasks-list-edit.view-test
  (:require [devcards.core :as dc :refer-macros [defcard-rg]]
            [tasks.components.tasks-list-edit.view :as view]
            [reagent.core :as reagent]
            [tasks.debug :as debug]
            [tasks.models.tasks-list-edit :as tasks-list-edit]))

(defcard-rg tasks-list-edit-view
  "### Basic component to edit task list name"

  (fn [state-atom _]
    (let [errors (tasks-list-edit/describe-errors (:tasks-list-edit @state-atom)
                                                  (:tasks-lists-names @state-atom))
          on-cancel #(debug/spy "Cancel!")
          on-save #(debug/spy "Save!" @state-atom)
          on-update #(swap! state-atom assoc-in (concat [:tasks-list-edit] %1) %2)]
      [view/render (:tasks-list-edit @state-atom)
       {:errors errors
        :on-cancel on-cancel
        :on-save on-save
        :on-update on-update}]))

  (reagent/atom {:tasks-list-edit {:new-name ""
                                   :current-name "toto"}
                 :tasks-lists-names ["titi" "toto"]})

  {:inspect-data true})
