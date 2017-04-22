(ns tasks.components.task.sub
  (:require [re-frame.core :as re-frame]
            [tasks.models.tasks :as tasks]))

(re-frame/reg-sub
 :edit
 (fn [db _] (:edit db)))

(re-frame/reg-sub
 :filter
 (fn [db _] (:filter db)))

(re-frame/reg-sub
 :show-details
 (fn [db _] (:show-details db)))

(re-frame/reg-sub
 :tasks
 (fn [db _] (:tasks db)))

(re-frame/reg-sub
 :tasks-filtered
 (fn [_ _]
   [(re-frame/subscribe [:tasks])
    (re-frame/subscribe [:filter])])
 (fn [[tasks filter] _] (tasks/filter-with tasks filter)))

(re-frame/reg-sub
 :tasks-sorted
 (fn [_ _] (re-frame/subscribe [:tasks-filtered]))
 (fn [tasks _] (tasks/sort-by-title tasks)))
