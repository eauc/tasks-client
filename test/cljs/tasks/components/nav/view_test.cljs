(ns tasks.components.nav.view-test
  (:require [devcards.core :as dc :refer-macros [defcard-rg]]
            [reagent.core :as reagent]
            [tasks.components.nav.view :as nav-view]
            [tasks.debug :as debug]))

(defcard-rg nav-view
  "### Top nav"

  (fn [state_atom _]
    (let [on-list #(swap! state_atom assoc-in [:tasks-list :name] %)
          state @state_atom]
      [nav-view/render
       (merge state {:on-create #(debug/spy "on-create")
                     :on-delete #(debug/spy "on-delete" %)
                     :on-edit #(debug/spy "on-edit" %)
                     :on-list on-list})]))

  (reagent/atom
   {:tasks-list {:name "current list"}
    :tasks-lists-names ["default"
                        "courses"
                        "check"
                        "boulot"]})

  {:inspect-data true})
