(ns tasks.components.page.view
  (:require [re-frame.core :as re-frame]
            [tasks.components.page.handler]
            [tasks.components.page.sub]
            [tasks.storage]
            [tasks.components.task.list :as task-list]
            [tasks.components.task.edit :as task-edit]
            [tasks.components.list.edit :as list-edit]))

(defn render-home []
  [task-list/component])

(defn render-edit [props]
  [:div
   [task-edit/component props]])

(defn render-list-edit [props]
  [:div
   [list-edit/component props]])

(defn render [page]
  (case page
    :home [render-home]
    :create [render-edit {:on-save-event :create-save}]
    :edit [render-edit {:on-save-event :edit-save}]
    :list-create [render-list-edit {:on-save-event :list-create}]
    :list-edit [render-list-edit {:on-save-event :list-save}]
    [:div "Undefined page"]))

(defn component []
  (let [page (re-frame/subscribe [:page])
        storage (re-frame/subscribe [:storage])]
    (fn []
      @storage
      [render @page])))
