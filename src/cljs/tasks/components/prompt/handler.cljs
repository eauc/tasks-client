(ns tasks.components.prompt.handler
  (:require [re-frame.core :as re-frame]
            [tasks.db :as db]
            [tasks.debug :as debug :refer [debug?]]))

(def interceptors
  [db/check-spec-interceptor
   (when debug? re-frame/debug)])

(re-frame/reg-event-fx
 :prompt-cancel
 [interceptors
  (re-frame/path :prompt)]
 (fn prompt-cancel [{:keys [db]}]
   (let [fx {:db nil}
         on-cancel (:on-cancel db)]
     (if on-cancel
       (assoc fx :dispatch on-cancel)
       fx))))

(re-frame/reg-event-fx
 :prompt-validate
 [interceptors
  (re-frame/path :prompt)]
 (fn prompt-cancel [{:keys [db]}]
   (let [fx {:db nil}
         on-validate (:on-validate db)
         type (:type db)
         value (:value db)]
     (if on-validate
       (assoc fx :dispatch (conj on-validate value))
       fx))))

(re-frame/reg-event-db
 :prompt-update
 [interceptors
  (re-frame/path :prompt)]
 (fn prompt-update [db [_ value]]
   (assoc db :value value)))

(re-frame/reg-event-fx
 :prompt-test
 interceptors
 (fn prompt-test [_ event]
   (debug/spy "prompt-test" event)
   {}))
