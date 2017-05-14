(ns tasks.components.nav.view
  (:require [re-frame.core :as re-frame]
            [tasks.components.auth.view :as auth-view]
            [tasks.components.nav.menu :as menu]
            [tasks.routes :as routes]))

(defn render [{:keys [auth tasks-list tasks-lists-names on-edit on-delete] :as props}]
  [:div.tasks-nav
   [menu/render tasks-lists-names props]
   [:div.tasks-nav-header
    (:name tasks-list)]
   [auth-view/render auth props]
   [:button.tasks-nav-action
    {:on-click #(on-edit tasks-list)}
    "Edit"]
   [:button.tasks-nav-action
    {:on-click #(on-delete tasks-list)}
    "Delete"]])

(defn component []
  (let [auth (re-frame/subscribe [:auth])
        tasks-list (re-frame/subscribe [:tasks-list])
        tasks-lists-names (re-frame/subscribe [:tasks-lists-names])
        on-create #(re-frame/dispatch [:nav routes/tasks-list-create])
        on-delete #(re-frame/dispatch [:tasks-list-delete-confirm %])
        on-edit #(re-frame/dispatch [:nav routes/tasks-list-edit {:name (:name %)}])
        on-list #(re-frame/dispatch [:tasks-list-set-current %])
        on-login #(re-frame/dispatch [:auth-login])
        on-sync #(re-frame/dispatch [:tasks-list-sync])]
    (fn nav-component []
      [render {:auth @auth
               :tasks-list @tasks-list
               :tasks-lists-names @tasks-lists-names
               :on-create on-create
               :on-delete on-delete
               :on-edit on-edit
               :on-list on-list
               :on-login on-login
               :on-sync on-sync}])))
