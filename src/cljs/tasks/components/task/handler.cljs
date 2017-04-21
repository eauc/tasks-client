(ns tasks.components.task.handler
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-event-db
 :filter-update
 (fn [db [_ filter]]
   (assoc-in db [:filter] filter)))

(re-frame/reg-event-db
 :tasks-update
 (fn [db [_ task]]
   (assoc-in db [:tasks] task)))

(re-frame/reg-event-db
 :toggle-details
 (fn [db [_ show-details]]
   (assoc-in db [:show-details] show-details)))
