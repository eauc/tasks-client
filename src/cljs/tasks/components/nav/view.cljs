(ns tasks.components.nav.view
  (:require [re-frame.core :as re-frame]
            [tasks.components.nav.menu :as menu]
            [tasks.routes :as routes]))

(defn render [{:keys [tasks-list tasks-lists-names on-edit on-delete] :as props}]
  [:div.tasks-nav
   [menu/render tasks-lists-names props]
   [:div.tasks-nav-header
    (:name tasks-list)]
   [:button.tasks-nav-action
    {:on-click #(on-edit tasks-list)}
    "Edit"]
   [:button.tasks-nav-action
    {:on-click #(on-delete tasks-list)}
    "Delete"]])

(defn component []
  (let [tasks-list (re-frame/subscribe [:tasks-list])
        tasks-lists-names (re-frame/subscribe [:tasks-lists-names])
        on-create #(re-frame/dispatch [:nav routes/tasks-list-create])
        on-delete #(re-frame/dispatch [:tasks-list-delete-confirm %])
        on-edit #(re-frame/dispatch [:nav routes/tasks-list-edit {:name (:name %)}])
        on-list #(re-frame/dispatch [:tasks-list-set-current %])]
    (fn nav-component []
      [render {:tasks-list @tasks-list
               :tasks-lists-names @tasks-lists-names
               :on-create on-create
               :on-delete on-delete
               :on-edit on-edit
               :on-list on-list}])))
