(ns tasks.components.nav.view
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [tasks.components.nav.menu :as menu-view]
            [tasks.routes :as routes]
            ;; [clairvoyant.core :refer-macros [trace-forms]]
            ;; [re-frame-tracer.core :refer [tracer]]

            [tasks.debug :as debug]))

;; (trace-forms {:tracer (tracer :color "orange")}

(defn render [{:keys [current-list lists on-edit on-delete] :as props}]
  (debug/spy "render" current-list)
  [:div.tasks-nav
   [menu-view/render lists props]
   [:div.tasks-nav-header
    current-list]
   [:button.tasks-nav-action
    {:on-click #(on-edit current-list)}
    "Edit"]
   [:button.tasks-nav-action
    {:on-click #(on-delete current-list)}
    "Delete"]])

(defn component []
  (let [current-list (re-frame/subscribe [:current-list])
        lists (re-frame/subscribe [:lists])
        on-create #(re-frame/dispatch [:nav routes/list-create])
        on-delete #(re-frame/dispatch [:list-delete %])
        on-edit #(re-frame/dispatch [:nav routes/list-edit {:id %}])
        on-list #(re-frame/dispatch [:list-current %])]
    (fn component []
      [render {:current-list @current-list
               :lists @lists
               :on-create on-create
               :on-delete on-delete
               :on-edit on-edit
               :on-list on-list}])))

;; )
