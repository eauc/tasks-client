(ns tasks.components.nav.menu
  (:require [reagent.core :as reagent]))

(defn render-icon []
  [:svg {:height "1em"
         :view-box "0 0 24 24"
         :width "1em"}
   [:rect {:x 0 :y 0
           :width 24 :height 4
           :rx 2 :ry 2
           :style {:fill "white"}}]
   [:rect {:x 0 :y 10
           :width 24 :height 4
           :rx 2 :ry 2
           :style {:fill "white"}}]
   [:rect {:x 0 :y 20
           :width 24 :height 4
           :rx 2 :ry 2
           :style {:fill "white"}}]])

(defn render-lists [lists {:keys [on-list on-create]}]
  [:div.tasks-nav-lists
   [:div.tasks-nav-list.tasks-nav-create
    {:on-click (fn [event]
                 (-> event .preventDefault)
                 (on-create))}
    "Create"]
    (for [list lists]
      ^{:key list} [:div.tasks-nav-list
                    {:on-click (fn [event]
                                 (-> event .preventDefault)
                                 (on-list list))}
                    list])])

(defn render [lists {:keys [on-list on-create]}]
  (let [hide (reagent/atom true)
        on-toggle #(swap! hide not)
        on-list-reset (fn [list]
                        #(reset! hide true)
                        (on-list list))]
    (fn _render-menu [lists]
      [:div.tasks-nav-menu {:class (if @hide "hide")
                            :on-click on-toggle}
       [render-icon]
       [render-lists lists
        {:on-list on-list-reset
         :on-create on-create}]])))
