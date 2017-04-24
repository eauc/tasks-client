(ns tasks.components.nav.view
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]))

(defn render-menu-icon []
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

(defn render-menu-lists [lists {:keys [on-list]}]
  [:div.tasks-nav-lists
   (for [list lists]
     ^{:key list} [:div.tasks-nav-list
                   {:on-click (fn [event]
                                (-> event .preventDefault)
                                (on-list list))}
                   list])])

(defn render-menu [lists {:keys [on-list]}]
  (let [hide (reagent/atom true)
        on-toggle #(swap! hide not)
        on-list-reset (fn [list]
                        #(reset! hide true)
                        (on-list list))]
    (fn []
      [:div.tasks-nav-menu {:class (if @hide "hide")
                            :on-click on-toggle}
       [render-menu-icon]
       [render-menu-lists lists
        {:on-list on-list-reset}]])))

(defn render [{:keys [current-list lists] :as props}]
  [:div.tasks-nav
   [render-menu lists props]
   [:div.tasks-nav-header
    current-list]])

(defn component []
  (let [current-list (re-frame/subscribe [:current-list])
        lists (re-frame/subscribe [:lists])
        on-list #(re-frame/dispatch [:current-list %])]
    (fn []
      [render {:current-list @current-list
               :lists @lists
               :on-list on-list}])))
