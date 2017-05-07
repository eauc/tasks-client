(ns tasks.components.page.view
  (:require [re-frame.core :as re-frame]
            [tasks.components.page.handler]
            [tasks.components.page.sub]
            [tasks.components.task-edit.view :as task-edit-view]
            [tasks.components.tasks-list.view :as tasks-list-view]
            [tasks.components.tasks-list-edit.view :as tasks-list-edit-view]
            [tasks.storage]))

(defn render-home []
  [tasks-list-view/component])

(defn render-task-edit [props]
  [:div
   [task-edit-view/component props]])

(defn render-tasks-list-edit [props]
  [:div
   [tasks-list-edit-view/component props]])

(defn render [page]
  (case page
    :home [render-home]
    :task-create [render-task-edit {:on-save-event :task-create-save}]
    :task-edit [render-task-edit {:on-save-event :task-edit-save}]
    :tasks-list-create [render-tasks-list-edit {:on-save-event :tasks-list-create-save}]
    :tasks-list-edit [render-tasks-list-edit {:on-save-event :tasks-list-edit-save}]
    [:div "Undefined page"]))

(defn component []
  (let [page (re-frame/subscribe [:page])
        storage (re-frame/subscribe [:storage])]
    (fn page-component []
      @storage
      [render @page])))
