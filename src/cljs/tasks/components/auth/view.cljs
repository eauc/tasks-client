(ns tasks.components.auth.view
  (:require [tasks.components.auth.handler]
            [tasks.components.auth.sub]))

(defn render [auth {:keys [on-login on-sync]}]
  (let [auth-token (:token auth)
        auth-status (:status auth)]
    [:button.tasks-nav-action
     {:type "button"
      :on-click (cond
                  (nil? auth-token) on-login
                  (nil? auth-status) on-sync
                  :else nil)}
     (if (nil? auth-token)
       "Login"
       (if (string? auth-status) auth-status "Sync"))]))
