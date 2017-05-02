(ns tasks.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [tasks.db]
            [tasks.debug :as debug]
            [tasks.components.nav.view :as nav-view]
            [tasks.components.page.view :as page-view]
            [tasks.components.prompt.view :as prompt-view]
            [tasks.routes :as routes]))

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [nav-view/component]
                  (.getElementById js/document "tasks-header"))
  (reagent/render [page-view/component]
                  (.getElementById js/document "tasks-body"))
  (reagent/render [prompt-view/component]
                  (.getElementById js/document "tasks-prompt")))

(defn ^:export init []
  (debug/setup)
  (when (not debug/debug?) (.registerServiceWorker js/window))
  (routes/app-routes)
  (re-frame/dispatch-sync [:initialize-db])
  (mount-root))
