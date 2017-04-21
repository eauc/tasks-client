(ns tasks.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [tasks.db]
            [tasks.components.nav.view :as nav-view]
            [tasks.components.task.create :as task-create]
            [tasks.components.task.list :as task-list]))

(defn dev-setup []
  (let [debug? ^boolean js/goog.DEBUG]
    (when debug?
      (enable-console-print!)
      (println "dev mode"))))

(defn mount-root []
  (reagent/render [nav-view/render]
                  (.getElementById js/document "tasks-header"))
  (reagent/render [:div
                   [task-list/component]
                   [task-create/render]]
                  (.getElementById js/document "tasks-body")))

(defn ^:export init []
  ;; (routes/app-routes)
  (re-frame/dispatch-sync [:initialize-db])
  (dev-setup)
  (mount-root))
