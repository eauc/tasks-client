(ns tasks.components.page.view
  (:require [re-frame.core :as re-frame]
            [tasks.components.page.handler]
            [tasks.components.page.sub]
            [tasks.components.task.create :as task-create]
            [tasks.components.task.list :as task-list]
            [tasks.components.task.edit :as task-edit]))

(defn render-home []
  [:div
   [task-list/component]
   [task-create/render]])

(defn render-edit []
  [:div
   [task-edit/component]])

(defn render [page]
  (case page
    :home [render-home]
    :edit [render-edit]
    [:div]))

(defn component []
  (let [page (re-frame/subscribe [:page])]
    (fn []
      [render @page])))
