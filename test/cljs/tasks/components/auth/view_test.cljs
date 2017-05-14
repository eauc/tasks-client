(ns tasks.components.auth.view-test
  (:require [devcards.core :as dc :refer-macros [defcard-rg]]
            [reagent.core :as reagent]
            [tasks.components.auth.view :as auth-view]))

(defcard-rg auth-view
  "### Authentication button"

  (fn [state-atom]
    (let [on-login #(swap! state-atom assoc :token "token")
          on-sync #(.log js/console "Sync!")]
      [auth-view/render (:token @state-atom)
       {:on-login on-login
        :on-sync on-sync}]))

  (reagent/atom
   {:token nil})

  {:inspect-data true})
