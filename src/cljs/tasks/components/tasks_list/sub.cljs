(ns tasks.components.tasks-list.sub
  (:require [re-frame.core :as re-frame]
            [tasks.models.task :as task]
            [tasks.models.tasks-list :as tasks-list]))

(re-frame/reg-sub
 :tasks-show-details
 (fn tasks-show-details [db _]
   (:tasks-show-details db)))

(re-frame/reg-sub
 :tasks-list
 (fn tasks-list [db _] (:tasks-list db)))

(re-frame/reg-sub
 :tasks-filter
 (fn tasks-filter [db _]
   (:tasks-filter db)))

(re-frame/reg-sub
 :tasks
 (fn tasks [db _]
   (get-in db [:tasks-list :tasks])))

(re-frame/reg-sub
 :tasks-filtered
 :<- [:tasks]
 :<- [:tasks-filter]
 (fn filtered [[tasks filter] _]
   (task/filter-with tasks filter)))

(re-frame/reg-sub
 :tasks-sorted
 :<- [:tasks-filtered]
 (fn tasks-sorted [tasks _]
   (task/sort-by-title tasks)))

(re-frame/reg-sub
 :tasks-lists-names
 (fn tasks-lists-names [db _]
   (:tasks-lists-names db)))
