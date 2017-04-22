(ns tasks.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [tasks.db]
            [tasks.debug :as debug]
            [tasks.components.nav.view :as nav-view]
            [tasks.components.page.view :as page.view]
            [tasks.routes :as routes]))

(defn mount-root []
  (reagent/render [nav-view/render]
                  (.getElementById js/document "tasks-header"))
  (reagent/render [page.view/component]
                  (.getElementById js/document "tasks-body")))

(defn ^:export init []
  (debug/setup)
  (routes/app-routes)
  (re-frame/dispatch-sync [:initialize-db])
  (mount-root))
