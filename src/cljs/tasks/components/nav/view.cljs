(ns tasks.components.nav.view
  (:require [re-frame.core :as re-frame]
            [tasks.components.nav.menu :as menu-view]
            [tasks.routes :as routes]))

(defn render [{:keys [current-list lists on-edit on-delete] :as props}]
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
        on-delete #(re-frame/dispatch [:list-delete-confirm %])
        on-edit #(re-frame/dispatch [:nav routes/list-edit {:id %}])
        on-list #(re-frame/dispatch [:list-current %])]
    (fn component []
      [render {:current-list @current-list
               :lists @lists
               :on-create on-create
               :on-delete on-delete
               :on-edit on-edit
               :on-list on-list}])))
