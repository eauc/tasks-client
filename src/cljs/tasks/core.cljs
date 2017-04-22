(ns tasks.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [tasks.db]
            [tasks.components.nav.view :as nav-view]
            [tasks.components.page.view :as page.view]
            [tasks.routes :as routes]))

(defn dev-setup []
  (let [debug? ^boolean js/goog.DEBUG]
    (when debug?
      (enable-console-print!)
      (println "dev mode"))))

(defn mount-root []
  (reagent/render [nav-view/render]
                  (.getElementById js/document "tasks-header"))
  (reagent/render [page.view/component]
                  (.getElementById js/document "tasks-body")))

(defn ^:export init []
  (dev-setup)
  (routes/app-routes)
  (re-frame/dispatch-sync [:initialize-db])
  (mount-root))
