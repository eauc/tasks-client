(ns tasks.components.auth.handler
  (:require [cljsjs.auth0-lock]
            [re-frame.core :as re-frame]
            [tasks.debug :as debug]
            [tasks.db :as db]))

(def lock-config
  (clj->js {:ui {:autoClose true}
            :auth {:loginAfterSignup true
                   :redirect false
                   :params {:scope "openid email permissions"}}}))

(def lock
  (js/Auth0Lock. "DQtcSq5ScL8R1ySbihplVM6UPJu6nNHO"
                 "eauc.eu.auth0.com"
                 lock-config))

(.on lock "authenticated"
     #(re-frame/dispatch [:auth-set-token (aget (debug/spy "auth result" %) "idToken")]))

(re-frame/reg-fx
 :auth-login
 (fn auth-login-fx []
   (.show lock)))

(re-frame/reg-event-fx
 :auth-login
 (fn auth-login []
   {:auth-login {}}))

(re-frame/reg-event-db
 :auth-set-token
 [db/default-interceptors
  (re-frame/path :auth :token)]
 (fn auth-set-token [_ [_ token]]
   token))
