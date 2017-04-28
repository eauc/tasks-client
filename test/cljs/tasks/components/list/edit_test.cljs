(ns tasks.components.list.edit-test
  (:require [devcards.core :as dc :refer-macros [defcard-rg]]
            [tasks.components.list.edit :as list-edit]
            [reagent.core :as reagent]
            [tasks.debug :as debug]))

(defcard-rg list-edit
  "### Basic component to edit task list name"

  (fn [state-atom _]
    (let [on-cancel #(debug/spy "Cancel!")
          on-save #(debug/spy "Save!" @state-atom)
          on-update #(swap! state-atom assoc-in (concat [:edit-list] %1) %2)]
      [list-edit/render (:edit-list @state-atom)
       {:on-cancel on-cancel
        :on-save on-save
        :on-update on-update}]))

  (reagent/atom {:edit-list {:new-name ""
                             :current-name "toto"}})

  {:inspect-data true})
