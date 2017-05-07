(ns tasks.components.prompt.handler
  (:require [re-frame.core :as re-frame]
            [tasks.db :as db]
            [tasks.debug :as debug]))

(re-frame/reg-event-fx
 :prompt-cancel
 [db/default-interceptors
  (re-frame/path :prompt)]
 (fn prompt-cancel [{:keys [db]}]
   (let [fx {:db nil}
         on-cancel (:on-cancel db)]
     (if on-cancel
       (assoc fx :dispatch on-cancel)
       fx))))

(re-frame/reg-event-fx
 :prompt-validate
 [db/default-interceptors
  (re-frame/path :prompt)]
 (fn prompt-cancel [{:keys [db]}]
   (let [fx {:db nil}
         on-validate (:on-validate db)
         value (:value db)]
     (if on-validate
       (assoc fx :dispatch (conj on-validate value))
       fx))))

(re-frame/reg-event-db
 :prompt-update
 [db/default-interceptors
  (re-frame/path :prompt)]
 (fn prompt-update [db [_ value]]
   (assoc db :value value)))

(re-frame/reg-event-fx
 :prompt-test
 db/default-interceptors
 (fn prompt-test [_ event]
   (debug/spy "prompt-test" event)
   {}))
