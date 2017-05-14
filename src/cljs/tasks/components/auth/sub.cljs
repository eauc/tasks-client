(ns tasks.components.auth.sub
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
 :auth
 (fn auth-token [db _]
   (:auth db)))
