(ns tasks.components.nav.menu
  (:require [reagent.core :as reagent]))

(defn icon-render []
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

(defn list-render [list-name {:keys [on-list]}]
  (let [on-list-click (fn [event]
                        (-> event .preventDefault)
                        (on-list list-name))]
    (fn _list-render [_ _]
      [:div.tasks-nav-list
       {:on-click on-list-click}
       list-name])))

(defn lists-render [_ {:keys [on-create] :as props}]
  (let [on-create-click (fn [event]
                          (-> event .preventDefault)
                          (on-create))]
    (fn _lists-render [lists-names _]
      [:div.tasks-nav-lists
       [:div.tasks-nav-list.tasks-nav-create
        {:on-click on-create-click}
        "Create"]
       (for [list-name lists-names]
         ^{:key list-name} [list-render list-name props])])))

(defn render [_ {:keys [on-list on-create]}]
  (let [hide (reagent/atom true)
        on-toggle #(swap! hide not)
        on-list-reset (fn [list-name]
                        #(reset! hide true)
                        (on-list list-name))]
    (fn menu-render [lists-names _]
      [:div.tasks-nav-menu {:class (if @hide "hide")
                            :on-click on-toggle}
       [icon-render]
       [lists-render lists-names
        {:on-list on-list-reset
         :on-create on-create}]])))
