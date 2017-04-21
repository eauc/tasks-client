(ns tasks.components.nav.view)

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

(defn render-menu []
  [:div.tasks-nav-menu
    [render-menu-icon]])

(defn render []
  [:div.tasks-nav
   [render-menu]
   [:div.tasks-nav-header
    "Tasks"]])
