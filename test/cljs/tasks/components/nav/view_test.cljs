(ns tasks.components.nav.view-test
  (:require [devcards.core :as dc :refer-macros [defcard-rg]]
            [reagent.core :as reagent]
            [tasks.components.nav.view :as nav-view]))

(defcard-rg nav-view*
  "### Top nav"

  (fn [state_atom _]
    [nav-view/render (merge @state_atom {:on-list #(println %)})])

  {:current-list "current list"
   :lists ["default"
           "courses"
           "check"
           "boulot"]})
